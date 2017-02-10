package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

import com.mybuckstory.model.FriendRequest;

public class FriendRequestEvent extends EmailEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6002993716505753485L;
	private final FriendRequest friendRequest;
	
	public FriendRequestEvent(Object source, FriendRequest friendRequest) {
		super(source, friendRequest.getTo(), "com/mybuckstory/core/service/friendRequest.vm");
		this.friendRequest = friendRequest;
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("senderFirstName", friendRequest.getCreatedBy().getProfile().getFirstName());		
		return model;
	}

	@Override
	public String getMessageSubject() {
		StringBuffer subject = getSubjectTemplate(friendRequest.getCreatedBy());
		subject.append(" added you as a friend on MyBuckStory");
		return subject.toString();
	}

}
