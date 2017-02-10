package com.mybuckstory.web.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.util.EmailAddressValidator;
import com.mybuckstory.web.command.TellAFriend;

@Component
public class TellAFriendValidator implements Validator {
	
	public boolean supports(Class<?> clazz) {		
		return TellAFriend.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		TellAFriend command = (TellAFriend)obj;	
		if(command.getDestinationEmailAddys().isEmpty()){
			errors.rejectValue("email1", "required", "You must enter at least one email address");
		}else{		
			validateEmail(errors, command.getEmail1(), "email1");
			validateEmail(errors, command.getEmail2(), "email2");
			validateEmail(errors, command.getEmail3(), "email3");
			validateEmail(errors, command.getEmail4(), "email4");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "required", "The message must contain some text");
		}
	}

	private void validateEmail(Errors errors, String emailAddy, String fieldName){
		if(StringUtils.isNotBlank(emailAddy)
		&& !EmailAddressValidator.isValid(emailAddy)){			
			errors.rejectValue(fieldName, "", "Invalid Email Address");
		}
	}

}
