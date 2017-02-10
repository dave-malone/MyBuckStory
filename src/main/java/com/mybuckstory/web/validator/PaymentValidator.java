package com.mybuckstory.web.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.model.Payment;

@Component
public class PaymentValidator implements Validator {

	private static final Logger logger = Logger.getLogger(PaymentValidator.class);
		
	private static final String[] STATUSES = new String[]{Payment.PENDING, Payment.INVALID, Payment.FAILED, Payment.COMPLETE, Payment.CANCELLED};
	
	public boolean supports(Class<?> clazz) {		
		return Payment.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		logger.debug("validating payment");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "buyerId", "required", "Buyer ID is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "required", "Status required");
		 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tax", "required", "Tax is required");		
	}

}
