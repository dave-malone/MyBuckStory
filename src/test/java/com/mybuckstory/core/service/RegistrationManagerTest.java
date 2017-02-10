package com.mybuckstory.core.service;

import java.util.Date;

import com.mybuckstory.common.Constants;
import com.mybuckstory.model.Role;
import com.mybuckstory.model.User;

public class RegistrationManagerTest extends AbstractSecureContextTest {

	protected RegistrationService registrationService;
	protected UserService userService;
	
	public void testRegister() {
		User user = new User();
		user.setUsername("avidmalone@gmail.com");
		user.setPassword("password1");
		assertNotNull(user.getProfile());
		user.getProfile().setDob(new Date());
		registrationService.register(user);
		
				
		assertNotNull(user.getProfile());
		
		assertTrue(user.getProfile().isUnder18());
		assertFalse(user.isEnabled());
		assertNotNull(user.getRoles());
		assertFalse(user.getRoles().isEmpty());
		assertTrue(user.getRoles().contains(new Role(Constants.DEFAULT_USER_ROLE)));
	}

	public void testConfirm(){	
		User userFromConfirm = registrationService.confirm(1L);
		assertNotNull(userFromConfirm);		
		assertTrue(userFromConfirm.isEnabled());
	}
	//TODO - change this to use easymock instead
	private void prepareService(){
//		registrationService.setEmailService(new EmailService(){
//			@Override
//			public void sendConfirmationEmail(User user){
//				assertNotNull(user);				
//				//don't do anything, just ensure that this method gets called, without relying
//				//on sending out a real email
//			}
//			
//		});
//		registrationService.setUserManager(new UserService(){
//			
//			@Override
//			public void saveOrUpdate(User user){
//				create(user);
//			}
//			
//			@Override
//			public Long create(User user){
//				assertNotNull(user);
//				user.setId(new Long(1L));
//				user.getProfile().setId(new Long(1L));
//				user.getProfile().setUnder18(true);
//				user.getProfile().setDob(new Date());
//				return null;
//			}
//			
//			@Override
//			public User getById(Long id){
//				User u = new User();
//				u.setId(id);
//				u.getProfile().setDob(new Date());
//				return u;
//			}
//		});
//		registrationService.setRoleManager(new RoleService(){
//			@Override
//			public Role findByName(String roleName){
//				return new Role(roleName);
//			}
//		});
	}
	
	@Override
	protected void onSetUp() throws Exception{
		prepareService();
	}

}
