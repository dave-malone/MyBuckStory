package com.mybuckstory.core.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.mybuckstory.model.Profile;
import com.mybuckstory.model.User;
import com.mybuckstory.web.command.MemberSearch;

public class UserManagerTest extends AbstractSecureContextTest {

	protected UserService userService;
	protected GenericMbsService<Profile> profileManager;

	public void testFindAllAdmins(){
		List<User> users = userService.findAll();
		assertNotNull(users);
		assertFalse(users.isEmpty());
		logger.debug("Found " + users.size() + " admin users");		
	}
	
	public void testLoadById() {
		User admin = userService.findByName(admin_username);
		User user = userService.loadById(1L);
		assertEquals(admin, user);
	}

	public void testGetById() {
		User admin = userService.findByName(admin_username);
		User user = userService.getById(1L);
		assertEquals(admin, user);
	}

	public void testResetPassword() {
		User admin = userService.findByName(admin_username);
		String pwd = admin.getPassword();
		admin.setPassword("newpass");
		userService.resetPassword(admin);
		userService.refresh(admin);
		
		assertNotSame(pwd, admin.getPassword());
	}

	public void testCreate() {
		User user = new User();
		user.getProfile().setDob(new Date());
		user.setUsername("test");
		user.setPassword("test");
		userService.saveOrUpdate(user);
		userService.refresh(user);
		assertNotNull(user.getId());
		//tests that the password gets encrypted
		assertNotSame("test", user.getPassword());
		assertNotNull(user.getProfile());
		assertNotNull(user.getProfile().getId());
		
	}

	public void testDelete() {
		User user = new User();
		user.getProfile().setDob(new Date());
		user.setUsername("test");
		user.setPassword("test");
		userService.saveOrUpdate(user);
		userService.refresh(user);
		assertNotNull(user.getId());
		//tests that the password gets encrypted
		assertNotSame("test", user.getPassword());
		assertNotNull(user.getProfile());
		assertNotNull(user.getProfile().getId());
		
		userService.delete(user);
		userService.refresh(user);
	}

	public void testUpdateWithoutPasswordChange() {
		User admin = userService.findByName(admin_username);
		String pwd = admin.getPassword();
		userService.updateWithoutPasswordChange(admin);
		userService.refresh(admin);
		assertEquals(pwd, admin.getPassword());
	}

	public void testUpdateWithNewPassword() {
		User admin = userService.findByName(admin_username);
		String pwd = admin.getPassword();
		admin.setPassword("newpass");
		userService.update(admin);
		userService.refresh(admin);
		assertNotSame(pwd, admin.getPassword());
	}

	public void testLoadUserByUsername() {
		String username = admin_username;
		UserDetails user = userService.loadUserByUsername(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
	}

	public void testFindByName() {
		String username = admin_username;
		User user = userService.findByName(username);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
	}
	

	public void testFindAll() {
		List<User> results = userService.findAll();		
		assertFalse(results.isEmpty());
	}

	public void testSaveOrUpdate() {
		User user = new User();
		user.getProfile().setDob(new Date());
		user.setUsername("test");
		user.setPassword("test");
		userService.saveOrUpdate(user);
		assertNotNull(user.getId());
	}

	public void testFindAllPaginated() {
		int max = 10;
		
		List<User> results = userService.findAllPaginated(0, max);		
		
		assertFalse(results.isEmpty());
	}

	public void testFindMostActiveUsers() {
		int max = 30;
		List<User> results = userService.findMostActiveUsers(max);
		assertNotNull(results);
	}

	public void testSearchByGender() {
		int max = 10;
		String gender = "Male";
		
		MemberSearch search = new MemberSearch();		
		search.setGender(gender);
		search.setStart(0);
		search.setMax(max);
		
		List<User> results = userService.search(search);
		assertNotNull(results);
		
		for(User user : results){
			assertEquals(gender, user.getProfile().getGender());
		}
	}
	
	public void testSearchByCity() {
		int max = 10;
		String location = "Minneapolis ";
		
		MemberSearch search = new MemberSearch();		
		search.setLocation(location);
		search.setStart(0);
		search.setMax(max);
		
		List<User> results = userService.search(search);
		
		
		assertFalse(results.isEmpty());
		assertTrue(results.size() < max + 1);
		
		for(User user : results){
			assertTrue(user.getProfile().getLocation().contains(StringUtils.trim(location)));			
		}
	}
	
	public void testSearchByState() {
		int max = 10;
		String location = " MN ";
		
		MemberSearch search = new MemberSearch();		
		search.setLocation(location);
		search.setStart(0);
		search.setMax(max);
		
		
		List<User> results = userService.search(search);
		
		
		assertFalse(results.isEmpty());
		assertTrue(results.size() < max + 1);
		
		for(User user : results){
			assertTrue(user.getProfile().getLocation().contains(StringUtils.trim(location)));			
		}
	}
	
	public void testSearchByCityAndState() {
		int max = 10;
		String location = " Minneapolis, MN ";
		
		MemberSearch search = new MemberSearch();		
		search.setLocation(location);
		search.setStart(0);
		search.setMax(max);
		
		List<User> results = userService.search(search);
		
		
		assertFalse(results.isEmpty());
		assertTrue(results.size() < max + 1);
		
		for(User user : results){
			assertTrue(user.getProfile().getLocation().contains(StringUtils.trim(location)));			
		}
	}
	
	public void testSearchByLastName() {
		int max = 10;
		String lastName = "Malone";
		
		MemberSearch search = new MemberSearch();		
		search.setMemberName(lastName);
		search.setStart(0);
		search.setMax(max);
				
		List<User> results = userService.search(search);
		
		
		assertFalse(results.isEmpty());
		assertTrue(results.size() < max + 1);
		
		for(User user : results){
			assertTrue(user.getProfile().getLastName().contains(lastName));			
		}
	}
	
	public void testSearchByFirstName() {
		int max = 10;
		String firstName = "Dave";
		
		MemberSearch search = new MemberSearch();		
		search.setMemberName(firstName);
		search.setStart(0);
		search.setMax(max);
		
		List<User> results = userService.search(search);
		
		
		assertFalse(results.isEmpty());
		assertTrue(results.size() < max + 1);
		
		for(User user : results){
			assertTrue(user.getProfile().getFirstName().contains(firstName));			
		}
	}
	
	public void testSearchByLastCommaFirst() {
		int max = 10;
		String lastName = "Malone";
		String firstName = "Dave";
		String fullName = lastName + ", " + firstName;
		
		MemberSearch search = new MemberSearch();		
		search.setMemberName(fullName);
		search.setStart(0);
		search.setMax(max);
		
		List<User> results = userService.search(search);
		
		
		assertFalse(results.isEmpty());
		assertTrue(results.size() < max + 1);
		
		for(User user : results){
			assertTrue(user.getProfile().getFirstName().contains(firstName));
			assertTrue(user.getProfile().getLastName().contains(lastName));
		}
	}
	
	public void testSearchByFirstSpaceLast() {
		int max = 10;
		String lastName = "Malone";
		String firstName = "Dave";
		String fullName = firstName + " " + lastName;
		
		MemberSearch search = new MemberSearch();		
		search.setMemberName(fullName);
		search.setStart(0);
		search.setMax(max);
		
		List<User> results = userService.search(search);
		
		
		assertFalse(results.isEmpty());
		assertTrue(results.size() < max + 1);
		
		for(User user : results){
			assertTrue(user.getProfile().getFirstName().contains(firstName));
			assertTrue(user.getProfile().getLastName().contains(lastName));
		}
	}

	public void testGetCount() {
		Long count = userService.getCount();
		assertNotNull(count);
	}
	
	public void testGetFriendsCount(){
		Long testUserId = 1L;
		BigInteger friendCount = userService.getFriendsCount(testUserId);
		assertNotNull(friendCount);
		logger.debug("User id " + testUserId + " has " + friendCount + " friends");
	}
	
	public void testFindAllFriendsPaginated(){
		Long testUserId = 1L;
		int max = 2;
		int start = 0;
		List<User> friends = userService.findAllFriendsPaginated(testUserId, start, max);
		assertNotNull(friends);
		assertTrue(friends.size() == max);
		logger.debug("User id " + testUserId + " # friends returned (paginated): " + friends.size());
		for(User friend : friends){
			logger.debug(friend.getProfile().getFullName());
		}
	}

}
