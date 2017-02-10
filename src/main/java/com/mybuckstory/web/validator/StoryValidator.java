package com.mybuckstory.web.validator;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mybuckstory.model.MimeType;
import com.mybuckstory.model.Story;

@Component
public class StoryValidator implements Validator {

	private static final Logger logger = Logger.getLogger(StoryValidator.class);
		
	public boolean supports(Class<?> clazz) {		
		return Story.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		logger.debug("validating story");
		Story story = (Story)obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required", "Please enter a Story title");
		ValidationUtils.rejectIfEmpty(errors, "text", "required", "Please tell us Your Story!");
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categories", "story.category.required", "Please select a story Category");
		if(story.getCategories().isEmpty() || StringUtils.isBlank(story.getCategories().first().getName())){
			errors.rejectValue("categories", "story.category.required", "You must select a category");
		}
		
		try {
			MimeType mimeType = MimeType.getByContentType(story.getFile().getContentType());
			logger.debug("file mime type: " + mimeType);
			if(story.containsNewlyUploadedImage() && (mimeType == null || !mimeType.isImage())){
				logger.debug("file mime type: " + mimeType);
				//"You may only upload an Image file to stories"
				errors.rejectValue("file", "invalid.image.file.format", "Only image file types are allowed");
			}
		} catch (IOException e) {
			logger.error("An error occurred while attempting to validate story image type", e);			
		}catch(Exception e){
			logger.error("An unexpected exception occurred while attempting to check the story image type", e);
		}
	}

}
