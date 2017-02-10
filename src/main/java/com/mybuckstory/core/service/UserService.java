package com.mybuckstory.core.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.security.PasswordEncryptionService;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.User;
import com.mybuckstory.util.DateUtil;
import com.mybuckstory.web.command.MemberSearch;

@Service
@Transactional
public class UserService extends GenericMbsService<User> implements UserDetailsService {

	private final PasswordEncryptionService passwordEncryptionService;
	
	public UserService(){
		this(null, null);
	}	
	
	@Autowired
	public UserService(@Qualifier("userDao") GenericHibernateDao<User, Long> dao, PasswordEncryptionService passwordEncryptionService){
		super(User.class, dao);		
		this.passwordEncryptionService = passwordEncryptionService;
	}

	
	/**
	 * Returns the encrypted password from the database.
	 * @param id
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	private String getPassword(final Long id) throws UsernameNotFoundException, DataAccessException {
		return (String)getCurrentSession().createQuery("select user.password from User user where user.id = :id")
			.setParameter("id", id)								
			.uniqueResult();
	}
	
	public void resetPassword(User updatedUser){
		passwordEncryptionService.encodePassword(updatedUser);
		hibernateDao.update(updatedUser);
	}

	@Override
	public Long create(User user){
		try{
			passwordEncryptionService.encodePassword(user);
			//TODO - semantic profile URIs will be a future enhancement
//			addProfileURI(user);
			checkUsersAge(user);
			super.create(user);
			
			return user.getId();
		}catch(Exception e){
			logger.fatal(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	private void checkUsersAge(User user){
		try {
			if(DateUtil.isUnder18(user.getProfile().getDob())){
				user.getProfile().setMakePublic(false);
				user.getProfile().setUnder18(true);
			}else{
				user.getProfile().setMakePublic(true);
				user.getProfile().setUnder18(false);
			}
		} catch (Exception e) {
			logger.warn("unable to obtain user profile dob", e);
		}
	}
	
	public void updateWithoutPasswordChange(User updatedUser){		
		if(updatedUser == null){
			throw new IllegalArgumentException("The [updatedUser] argument may not be null");
		}		
		
		try{		
			hibernateDao.update(updatedUser);
		}catch(Exception e){
			logger.info("hibernateDao.update failed - attempting merge instead");
			
			try{
				hibernateDao.merge(updatedUser);
			}catch(Exception e2){
				logger.fatal(e2.getMessage());
				throw new RuntimeException(e2);
			}
		}
	}
	
	
	
	/**
	 * Provides the necessary checks for user password change scenarios.  If 
	 * we didn't do that, the new password wouldn't get encrypted and authentication
	 * would fail for the user the next time they attempted to authenticate against 
	 * the system.
	 */
	public void update(User user){
		if(user == null){
			throw new IllegalArgumentException("The [user] argument may not be null");
		}		
		String originalPassword = getPassword(user.getId());
		if(StringUtils.isBlank(originalPassword)){
			throw new IllegalStateException("The user exists in the database, but does not have a password in the database.  Data integrity has been violated");
		}
		
		checkUsersAge(user);
		
		if(StringUtils.isNotBlank(user.getPassword()) 
		&& !user.getPassword().equalsIgnoreCase(originalPassword)){
			resetPassword(user);				
		}else{
			updateWithoutPasswordChange(user);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException, DataAccessException {
		User person = (User)getCurrentSession().createQuery("from User person where person.username = :username")
							.setParameter("username", userName)
							.uniqueResult();
		
		if(person == null){
			throw new UsernameNotFoundException("Username could not be found", userName);
		}		
		logger.debug("Username: " + person.getUsername());
		logger.debug("User password: " + person.getPassword());
		
		return person;
	}
	
	@Transactional(readOnly=true)
	public User findByName(String username){
    	return (User)loadUserByUsername(username);
    }

	@Override
	public void saveOrUpdate(User user){
		checkUsersAge(user);
		hibernateDao.saveOrUpdate(user);
	}
	
	
	
	@Transactional(readOnly=true)	
	@SuppressWarnings("unchecked")
	public List<User> findMostActiveUsers(final int max){
		logger.debug("Finding most active users");		
		List<User> results = new ArrayList<User>();		
		List<Object[]> tuples = (List<Object[]>)getCurrentSession().createSQLQuery("SELECT count( story.id ) AS storycount, member.id " +
													"FROM USER member " +
													"INNER JOIN STORY story ON story.author_id = member.id " +
													"INNER JOIN Profile profile ON profile.user_id = member.id " +
													"INNER JOIN Comment cmt ON cmt.author_id = member.id " +
													"WHERE cmt.date_created > ? " +
													"AND story.date_created > ? " +
													"GROUP BY member.id " +
													"ORDER BY storycount DESC")
									.setDate(0, DateUtil.getLastMonthsDate())
									.setDate(1, DateUtil.getLastMonthsDate())
									.setMaxResults(max)
									.list();
		
		for(Object[] tuple : tuples){			
			User user = getById(new Long(tuple[1].toString()));
			results.add(user);
		}
		
		return results;
	}
	
	@Transactional(readOnly=true)
	public List<User> search(final MemberSearch search){
		Session session = getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> list = createSearchCriteria(search, session)
			.addOrder(Order.desc("id"))
			.setFirstResult(search.getFirstResult() != null ? search.getFirstResult() : 0)
			.setMaxResults(search.getMaxResults() != null ? search.getMaxResults() : 15)
			.setCacheable(true)
			.list();
		return list;
	}
	
	@Transactional(readOnly=true)
	public Long getSearchCount(final MemberSearch search){
		Session session = getCurrentSession();
		
		return (Long)createSearchCriteria(search, session)
			.setProjection(Projections.count("id"))
			.setCacheable(true)
			.uniqueResult();
	}
	
	private Criteria createSearchCriteria(final MemberSearch search, Session session) {
		Criteria criteria = session.createCriteria(User.class);
		
		Criteria profileCriteria = null;
		
		if(StringUtils.isNotBlank(search.getMemberName())){
			String name = StringUtils.trimToEmpty(search.getMemberName());
			String[] fullName = null;
			String splitChar = null;
			//TODO check lengths after split to avoid NPEs
			if(StringUtils.containsIgnoreCase(name, ",")){
				String[] str = name.split(",");
				fullName = new String[str.length];
				fullName[0] = str[1];
				fullName[1] = str[0];
			}else if(StringUtils.containsIgnoreCase(name, " ")){
				String[] str = name.split(" ");
				fullName = new String[str.length];
				fullName[0] = str[0];
				fullName[1] = str[1];
			}					
			
			if(fullName != null){
				profileCriteria = criteria.createCriteria("profile")
						.add(Restrictions.ilike("firstName", "%" + StringUtils.trim(fullName[0]) + "%"))
						.add(Restrictions.ilike("lastName", "%" + StringUtils.trim(fullName[1]) + "%"));
			}else{						
				profileCriteria = criteria.createCriteria("profile")
					.add(Restrictions.disjunction()
						.add(Restrictions.ilike("firstName", "%" + name + "%"))
						.add(Restrictions.ilike("lastName", "%" + name + "%")));
			}
		}				
		if(StringUtils.isNotBlank(search.getGender())){	
			if(profileCriteria == null) profileCriteria = criteria.createCriteria("profile");
			
			profileCriteria.add(Restrictions.eq("gender", search.getGender()));
		}
		if(StringUtils.isNotBlank(search.getLocation())){
			if(profileCriteria == null) profileCriteria = criteria.createCriteria("profile");
			
			String location = StringUtils.trimToEmpty(search.getLocation());
			String[] loc = null;
			if(StringUtils.contains(location, ",")){
				loc = location.split(",");
				profileCriteria
					.add(Restrictions.ilike("location", "%" + StringUtils.trim(loc[0]) + "%" + StringUtils.trim(loc[1]) + "%"));
			}else{					
				profileCriteria
					.add(Restrictions.ilike("location", "%" + location + "%"));
			}
		}
			
		criteria.setCacheable(true);
		
		return criteria;
	}

	@Transactional(readOnly=true)
	public List<User> findAllAdmins() {
		Session session = getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<User> admins = session
			.createCriteria(User.class)
			.createCriteria("roles")
			.add(Restrictions.eq("name", "ROLE_ADMIN"))
			.setCacheable(true)
			.list();
	
		return admins;	
	}

	@Transactional(readOnly=true)
	public List<User> findAllProStaff() {
		Session session = getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<User> admins = session
			.createCriteria(User.class)
			.add(Restrictions.eq("proStaff", true))
			.setCacheable(true)
			.list();
	
		return admins;	
	}
	
	@Transactional(readOnly=true)
	public List<User> findAllRecentlyLoggedIn() {
		Session session = getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<User> admins = session
			.createCriteria(User.class)
			.addOrder(Order.desc("lastLoginDate"))
			.setMaxResults(12)
			.setFirstResult(0)
			.setCacheable(true)
			.list();
	
		return admins;	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<User> findAllFriendsPaginated(final Long userId, final Integer start, final Integer max) {
		Session session = getCurrentSession();
		List<BigInteger> bigIntFriendIds = session.createSQLQuery("select FRIEND_ID from FRIENDS where PROFILE_ID = :userId")
				.setParameter("userId", userId).list();

		List<User> friends = new ArrayList<User>();

		if (bigIntFriendIds.isEmpty()) {
			return friends;
		}

		List<Long> friendIds = new ArrayList<Long>();
		for (BigInteger friendId : bigIntFriendIds) {
			friendIds.add(friendId.longValue());
		}

		friends = session.createCriteria(User.class)
				.add(Restrictions.in("id", friendIds))
				.setMaxResults(max != null ? max : 10)
				.setFirstResult(start != null ? start : 0)
				.createCriteria("profile")
				.addOrder(Order.asc("lastName"))
				.addOrder(Order.asc("firstName")).list();

		return friends;	
	}

	@Transactional(readOnly=true)
	public BigInteger getFriendsCount(final Long userId) {
		Session session = getCurrentSession();
		return (BigInteger)session.createSQLQuery("select count(FRIEND_ID) from FRIENDS where PROFILE_ID = :userId")
			.setParameter("userId", userId)
			.uniqueResult();
	}

	public PasswordEncryptionService getPasswordEncryptionService() {
		return passwordEncryptionService;
	}
	
}
