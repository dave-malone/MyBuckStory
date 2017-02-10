package com.mybuckstory.web.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.util.DateUtil;
import com.mybuckstory.web.command.ProfileInfo;

@Component
public class ProfileInfoValidator implements Validator {
	
	private static final Logger logger = Logger.getLogger(ProfileInfoValidator.class);
	
	public boolean supports(Class<?> clazz) {		
		return ProfileInfo.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors){
		ProfileInfo info = (ProfileInfo)obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "profile.firstName.required", "First Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "profile.lastName.required", "Last Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "profile.location.required", "Location is required");	
		
		try {
			if(DateUtil.isBefore1900(info.getDob())){
				errors.rejectValue("dob", "profile.dob.invalid", "Invalid birth date.  Check to see if the day of the month entered is correct");
			}
		} catch (Exception e) {
			logger.warn("an error occurred while validating DOB", e);
		}
		
	}

}
