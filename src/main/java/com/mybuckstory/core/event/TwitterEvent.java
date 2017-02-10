package com.mybuckstory.core.event;

import org.springframework.context.ApplicationEvent;

import com.mybuckstory.model.Story;

public class TwitterEvent extends ApplicationEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8118612783788950018L;
	protected final Story story;
	
	public TwitterEvent(Object source, Story story) {
		super(source);
		this.story = story;
	}

	public Story getStory() {
		return story;
	}	

}
