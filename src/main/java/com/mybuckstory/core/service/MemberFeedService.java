package com.mybuckstory.core.service;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.Profile;
import com.mybuckstory.model.User;

@Service
@Transactional
public class MemberFeedService extends GenericMbsService<MemberFeedItem> implements ApplicationListener<MemberFeedEvent> {
	
	private final UserService userService;
	
	public MemberFeedService(){
		this(null, null);
	}
	
	@Autowired
	public MemberFeedService(UserService userService, @Qualifier("memberFeedItemDao") GenericHibernateDao<MemberFeedItem, Long> dao){
		super(MemberFeedItem.class, dao);
		this.userService = userService;
	}
	
	@Override
	public void onApplicationEvent(MemberFeedEvent event) {
		logger.debug("Received member feed event " + event);
		logger.debug("persisting new member feed item");
		try{
			create(event.getMemberFeedItem());
		}catch(Exception e){
			logger.error("excpetion caught while creating member feed item", e);
		}catch(Throwable t){
			logger.error("throwable caught while creating member feed item", t);
		}
	}
	
	@Transactional(readOnly=true)
	public List<MemberFeedItem> getMemberFeed(final Long userId, final Integer firstResult, final Integer maxResults){
		Session session = getCurrentSession();	

		User user = userService.getById(userId);
		Profile profile = user.getProfile();
		Set<User> friends = profile.getFriends();
		
		//ensure members only get feed items that are relevant to their signup date (i.e. not published before they signed up)
		Criteria criteria = session.createCriteria(MemberFeedItem.class).add(Restrictions.gt("dateCreated", user.getSignupDate()));
		
		//get recent feed items created by your friends		
		Criterion friendsFeedItems = friends.isEmpty() ? null : Restrictions.in("createdBy", friends);
		
		//get member feed items created by admins
		Junction itemsCreatedByAdmins = Restrictions.conjunction()
												.add(Restrictions.in("type", new String[]{"AFFILIATE", "EVENT", "CONTEST", "BADGE_AWARD", "CONTEST_PRIZE"}))
												.add(Restrictions.in("createdBy", userService.findAllAdmins()));
		
		//create an or statement between the two sets of criteria to make it inclusive
		Junction full = Restrictions.disjunction();
		
		if(friendsFeedItems != null){
			full.add(friendsFeedItems);
		}
		if(itemsCreatedByAdmins != null){
			full.add(itemsCreatedByAdmins);
		}
		
		criteria.add(full);
		
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setFirstResult(firstResult != null ? firstResult : 0);
		criteria.setMaxResults(maxResults != null ? maxResults : 15);
		
		criteria.setCacheable(true);
		
		@SuppressWarnings("unchecked")
		List<MemberFeedItem> feed = criteria.list();		
		return feed;
	}
	
	@Transactional(readOnly=true)
	public Long getMemberFeedCount(final Long userId){
		Session session = getCurrentSession();	

		User user = userService.getById(userId);
		
		//ensure members only get feed items that are relevant to their signup date (i.e. not published before they signed up)
		Criteria criteria = session.createCriteria(MemberFeedItem.class).add(Restrictions.gt("dateCreated", user.getSignupDate()));
		
		//get recent feed items created by your friends
		Criterion friendsFeedItems = user.getProfile().getFriends().isEmpty() ? null : Restrictions.in("createdBy", user.getProfile().getFriends());
		
		//get recent events and affiliates created by admins
		Junction recentEventsAndAffiliates = Restrictions.conjunction()
												.add(Restrictions.in("type", new String[]{MemberFeedItem.Type.AFFILIATE.toString(), MemberFeedItem.Type.EVENT.toString()}))
												.add(Restrictions.in("createdBy", userService.findAllAdmins()));
		
		//create an or statement between the two sets of criteria to make it inclusive
		Junction full = Restrictions.disjunction();
		
		if(friendsFeedItems != null){
			full.add(friendsFeedItems);
		}
		if(recentEventsAndAffiliates != null){
			full.add(recentEventsAndAffiliates);
		}
		
		criteria.add(full).setCacheable(true).setProjection(Projections.rowCount());
		
		return (Long)criteria.uniqueResult();
	}
	
}
