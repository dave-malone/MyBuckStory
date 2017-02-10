package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Story;

public class StoryCommentEvent extends EmailEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2422671079792462440L;
	private final Story story;
	private final Comment comment;
	
	public StoryCommentEvent(Object source, final Story story, final Comment comment) {
		super(source, story.getCreatedBy(), "com/mybuckstory/core/service/storyCommentNotification.vm");
		this.story = story;
		this.comment = comment;
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("senderFirstName", comment.getCreatedBy().getProfile().getFirstName());
		model.put("storyUri", story.getUri());	
		return model;
	}

	@Override
	public String getMessageSubject() {
		StringBuffer subject = getSubjectTemplate(comment.getCreatedBy());
		subject.append(" commented on your Story");
		return subject.toString();
	}

}
