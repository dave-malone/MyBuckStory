package com.mybuckstory.web.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.model.Contest;

@Component
public class ContestValidator implements Validator {

	private static final Logger logger = Logger.getLogger(ContestValidator.class);
		
	public boolean supports(Class<?> clazz) {		
		return Contest.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		Contest contest = (Contest)obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required", "Title is required");
		ValidationUtils.rejectIfEmpty(errors, "description", "required", "The description can not be empty");

		try {
			if(contest.getEndDate() == null){
				errors.rejectValue("endDate", "", "Please select an end date");
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to validate competition end date", e);
			errors.rejectValue("endDate", "", "Please select an end date");
		}
		
		try {
			if(contest.getStartDate() == null){
				errors.rejectValue("startDate", "", "Please select a start date");
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to validate competition end date", e);
			errors.rejectValue("startDate", "", "Please select a start date");
		}
	}

}
