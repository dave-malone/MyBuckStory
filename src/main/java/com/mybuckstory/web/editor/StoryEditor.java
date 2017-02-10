package com.mybuckstory.web.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.model.Story;

public class StoryEditor extends PropertyEditorSupport{

	private static final Logger			LOG	= LoggerFactory.getLogger(StoryEditor.class);
	private final StoryService	storyService;

	public StoryEditor(StoryService storyService){
		super(Story.class);
		this.storyService = storyService;
	}

	@Override
	public String getAsText() {
		Story story = (Story)getValue();
		if(story != null){
			return story.getId().toString();
		}
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LOG.debug("Looking for Story with id " + text);
		
		try{
			Long id = Long.valueOf(text);
	        super.setValue(storyService.getById(id));
		}catch(NumberFormatException e){
			super.setValue(null);
		}
	}

}