package com.mybuckstory.web.validator;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.core.service.UserService;
import com.mybuckstory.model.User;
import com.mybuckstory.util.DateUtil;
import com.mybuckstory.util.EmailAddressValidator;

@Component
public class RegistrationValidator implements Validator {

	@Autowired
	private UserService userService;
		
	public boolean supports(Class<?> clazz) {		
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		User user = (User)obj;
		
		if(!user.isAcceptedTOS()){
			errors.rejectValue("acceptedTOS", "", "You must agree to the terms of service");
		}
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profile.dob", "required", "Birthday is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profile.firstName", "required", "First name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profile.lastName", "required", "Last name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", "Email is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reEnteredUsername", "required", "Re-Entered Email is required");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required", "Password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reEnteredPassword", "required", "Re-Entered Password is required");		
		
		if(StringUtils.isNotBlank(user.getUsername())
		&& !EmailAddressValidator.isValid(user.getUsername())){			
			errors.rejectValue("username", "", "Invalid Email Address");
		}
		
		try{
			if(userService.findByName(user.getUsername()) != null){
				errors.rejectValue("username", "", "Email address already in use");
			}
		}catch(UsernameNotFoundException e){}
		
		if((StringUtils.isNotBlank(user.getUsername()) && StringUtils.isNotBlank(user.getReEnteredUsername()))
			&& !user.getUsername().equals(user.getReEnteredUsername())){
				errors.rejectValue("username", "", "Email addresses entered did not match");
			}
		
		
		if((StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotBlank(user.getReEnteredPassword()))
		&& !user.getPassword().equals(user.getReEnteredPassword())){
			errors.rejectValue("password", "", "Passwords did not match");
		}
		
		Date dob = null;
		
		try {
			dob = user.getProfile().getDob();
		} catch (Exception e) {
			
		}
		if(dob != null && DateUtil.isBefore1900(dob)){
			errors.rejectValue("profile.dob", "", "Invalid birth date.  Check to see if the day of the month entered is correct");
		}		
	}

}
