package com.mybuckstory.web.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.model.MimeType;
import com.mybuckstory.web.command.EditStoryCommand;

@Component
public class EditStoryCommandValidator implements Validator {

	private static final Logger logger = Logger.getLogger(EditStoryCommandValidator.class);
		
	public boolean supports(Class<?> clazz) {		
		return EditStoryCommand.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		EditStoryCommand story = (EditStoryCommand)obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required", "Title is required");
		ValidationUtils.rejectIfEmpty(errors, "text", "required", "The story content can not be empty");
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categories", "required", "You must select a category");		
		
//		if(story.getFile() != null && !story.getFile().isEmpty() && !MimeType.getByContentType(story.getFile().getContentType()).isImage()){
//			errors.rejectValue("file", "", "You may only attach an Image to Stories");
//		}
	}

}
