package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Image;


public class ImageCommentEvent extends EmailEvent {
	
	private final Image image;
	private final Comment comment;
	
	public ImageCommentEvent(Object source, final Image image, final Comment comment) {
		super(source, image.getCreatedBy(), "com/mybuckimage/core/service/imageCommentNotification.vm");
		this.image = image;
		this.comment = comment;
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("senderFirstName", comment.getCreatedBy().getProfile().getFirstName());
		model.put("imageId", image.getId());	
		return model;
	}

	@Override
	public String getMessageSubject() {
		StringBuffer subject = getSubjectTemplate(comment.getCreatedBy());
		subject.append(" commented on your Image");
		return subject.toString();
	}

}
