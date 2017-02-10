package com.mybuckstory.core.service;

import com.mybuckstory.exception.InvalidPwResetTokenException;
import com.mybuckstory.exception.UserDisabledException;
import com.mybuckstory.model.User;

public class PasswordResetServiceTest extends AbstractSecureContextTest{

	protected PasswordResetService passwordResetService;
	
	private User user;
	
	public void testSendResetEmail() throws Exception{
		user = new User();
		user.setEnabled(true);
		passwordResetService.sendResetEmail("testemail");
	}
	
	public void testSendResetEmailAgainstInactiveAccount(){
		try{
			user = new User();
			passwordResetService.sendResetEmail("testemail");
		}catch(Exception e){
			assertEquals(UserDisabledException.class, e.getClass());			
		}
	}
	
	public void testSendResetEmailUserNotFound(){
		try{
			user = null;
			passwordResetService.sendResetEmail("testemail");
		}catch(Exception e){
			assertEquals(IllegalArgumentException.class, e.getClass());				
		}
	}
	
	public void testResetPasswordUserNotFound() throws Exception{
		try{
			user = null;
			passwordResetService.resetPassword("testemail", "testpwtoken");
		}catch(Exception e){
			assertEquals(IllegalArgumentException.class, e.getClass());			
		}
	}

	public void testResetPasswordInactiveAccount() throws Exception{
		try{
			user = new User();
			passwordResetService.resetPassword("testemail", "testpwtoken");
		}catch(Exception e){
			assertEquals(UserDisabledException.class, e.getClass());			
		}
	}
	
	public void testResetPassword() throws Exception{
		user = new User();
		user.setEnabled(true);
		String passwordResetToken = "testpwtoken";
		user.setPasswordResetToken(passwordResetToken);
		String newPass = passwordResetService.resetPassword("testemail", passwordResetToken);
		assertNotNull(newPass);
		assertNull(user.getPasswordResetToken());
		assertEquals(newPass, user.getPassword());
	}
	
	public void testResetPasswordNullUserPwResetToken() throws Exception{
		try{
			user = new User();
			user.setEnabled(true);
			user.setPasswordResetToken(null);
			passwordResetService.resetPassword("testemail", "testpwtoken");		
		}catch(Exception e){
			assertEquals(InvalidPwResetTokenException.class, e.getClass());
		}
	}
	
	public void testResetPasswordNullPwResetToken() throws Exception{
		try{
			user = new User();
			user.setEnabled(true);
			user.setPasswordResetToken("testpwtoken");
			passwordResetService.resetPassword("testemail", null);		
		}catch(Exception e){
			assertEquals(InvalidPwResetTokenException.class, e.getClass());
		}
	}
	
	public void testResetPasswordUnmatchingPwResetToken() throws Exception{
		try{
			user = new User();
			user.setEnabled(true);
			user.setPasswordResetToken("testpwtoken");
			passwordResetService.resetPassword("testemail", "idon'tmatch!");		
		}catch(Exception e){
			assertEquals(InvalidPwResetTokenException.class, e.getClass());
		}
	}
	
	//TODO - change this to use easymock instead
	private void prepareService(){
//		passwordResetService.setUserManager(new UserService(){
//			@Override
//			public User findByName(String username){
//				assertNotNull(username);
//				return user;
//			}
//			@Override
//			public void resetPassword(User updatedUser){
//				assertNotNull(updatedUser);
//			}
//			@Override
//			public void update(User user){
//				assertNotNull(user);				
//			}			
//		});
//		passwordResetService.setEmailService(new EmailService(){
//			@Override
//			public void sendPwResetInstructionsEmail(User user){
//				assertNotNull(user);				
//				//don't do anything, just ensure that this method gets called, without relying
//				//on sending out a real email
//			}
//			
//		});
	}

	@Override
	protected void onSetUp() throws Exception{
		prepareService();
	}

}
