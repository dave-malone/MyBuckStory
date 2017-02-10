package com.mybuckstory.core.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.PasswordResetRequestEvent;
import com.mybuckstory.exception.InvalidPwResetTokenException;
import com.mybuckstory.exception.UserDisabledException;
import com.mybuckstory.model.User;
import com.mybuckstory.util.PasswordGen;

@Service
@Transactional
public class PasswordResetService {

	private final UserService userService;
	private final ApplicationEventService eventService;
	
	public PasswordResetService(){
		this(null, null);
	}
	
	@Autowired
	public PasswordResetService(UserService userService, ApplicationEventService eventPublisher){
		this.userService = userService;
		this.eventService = eventPublisher;
	}
	
	public void sendResetEmail(String email) throws UserDisabledException{
		User user = userService.findByName(email);
		if(user == null){
			throw new IllegalArgumentException("User with email " + email + " does not exist in our system");
		}
		if(!user.isEnabled()){
			throw new UserDisabledException(user);
		}
		user.setPasswordResetToken(PasswordGen.getRandomPassword());
		userService.update(user);
		eventService.publishAsynch(new PasswordResetRequestEvent(this, user));
	}
	
	public String resetPassword(String email, String pwResetToken) throws UserDisabledException, InvalidPwResetTokenException{
		String newPass = PasswordGen.getRandomPassword();
		User user = userService.findByName(email);
		if(user == null){
			throw new IllegalArgumentException("User with email " + email + " does not exist in our system");
		}
		if(!user.isEnabled()){
			throw new UserDisabledException(user);
		}
		if(StringUtils.isBlank(user.getPasswordResetToken()) || StringUtils.isBlank(pwResetToken) ||
				!StringUtils.equals(user.getPasswordResetToken(), pwResetToken)){
			throw new InvalidPwResetTokenException("Invalid Password reset attempt.  You may have already reset your password, and new to request to reset it again");
		}
		
		if(user.getPasswordResetToken().equals(pwResetToken)){
			user.setPasswordResetToken(null);
			user.setPassword(newPass);		
			userService.resetPassword(user);
		}
		return newPass;
	}

	
}
