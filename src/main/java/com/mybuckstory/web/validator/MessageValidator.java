package com.mybuckstory.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.model.Message;

@Component
public class MessageValidator implements Validator {
		
	public boolean supports(Class<?> clazz) {		
		return Message.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "required", "Subject is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "required", "You can not send an empty message");		
	}

}
