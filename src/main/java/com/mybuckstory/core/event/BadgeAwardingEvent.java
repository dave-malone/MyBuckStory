package com.mybuckstory.core.event;

import org.springframework.context.ApplicationEvent;

import com.mybuckstory.model.Story;

public class BadgeAwardingEvent extends ApplicationEvent{

	private Story story;
	
	public BadgeAwardingEvent(Object source, Story story) {
		super(source);
		this.story = story;
	}

	public Story getStory() {
		return story;
	}

}
