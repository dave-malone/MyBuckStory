package com.mybuckstory.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mybuckstory.core.service.UserService;
import com.mybuckstory.model.User;
import com.mybuckstory.util.EmailAddressValidator;
import com.mybuckstory.util.UserUtil;
import com.mybuckstory.web.command.LoginInfoCommand;

@Component
public class LoginInfoCommandValidator implements Validator {

	@Autowired
	private UserService userService;	
	
	public boolean supports(Class<?> clazz) {		
		return LoginInfoCommand.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		LoginInfoCommand command = (LoginInfoCommand)obj;
		User currentUser = UserUtil.getCurrentUser();
		
		if((StringUtils.isNotBlank(command.getNewPass()) || StringUtils.isNotBlank(command.getNewPassConf()))
				&& StringUtils.isBlank(command.getCurrPass())){
			errors.rejectValue("currPass", "required", "You must enter your current password in order to reset your password");
		}else if((StringUtils.isNotBlank(command.getNewPass()) || StringUtils.isNotBlank(command.getNewPassConf()))
				&& StringUtils.isNotBlank(command.getCurrPass()) && !StringUtils.equals(command.getNewPass(), command.getNewPassConf())){
			errors.rejectValue("newPassConf", "required", "New passwords entered did not match.  These values are case sensitive");
		}
		
		
		if(StringUtils.isNotBlank(command.getUsername())
		&& !EmailAddressValidator.isValid(command.getUsername())){			
			errors.rejectValue("username", "required", "Invalid Email Address");
		}
		
		try{
			if(!currentUser.getUsername().equals(command.getUsername()) && userService.findByName(command.getUsername()) != null){
				errors.rejectValue("username", "required", "Email address already in use");
			}
		}catch(UsernameNotFoundException e){}
		
		User tempUser = new User();
		tempUser.setPassword(command.getCurrPass());
		userService.getPasswordEncryptionService().encodePassword(tempUser);
		
		if(!StringUtils.equals(tempUser.getPassword(), currentUser.getPassword())){
			errors.rejectValue("currPass", "required", "Current password entered was not valid");
		}
		
	}

}
