package com.mybuckstory.web.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.web.command.EditEventCommand;

@Component
public class EditEventCommandValidator implements Validator {

	private static final Logger logger = Logger.getLogger(EditEventCommandValidator.class);
		
	public boolean supports(Class<?> clazz) {		
		return EditEventCommand.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		EditEventCommand event = (EditEventCommand)obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required", "Title is required");
		ValidationUtils.rejectIfEmpty(errors, "description", "required", "The event description can not be empty");

		try {
			if(event.getDate() == null){
				errors.rejectValue("date", "", "You must select a date for your event");
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to validate event date", e);
			errors.rejectValue("date", "", "You must select a date for your event");
		}
	}

}
