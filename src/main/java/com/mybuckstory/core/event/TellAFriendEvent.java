package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

import com.mybuckstory.web.command.TellAFriend;

public class TellAFriendEvent extends EmailEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4936431818563966879L;
	private final TellAFriend tellAFriend;
	
	public TellAFriendEvent(Object source, final TellAFriend tellAFriend) {
		super(source, tellAFriend.getDestinationEmailAddys(), "com/mybuckstory/core/service/tellAFriendNotification.vm");
		this.tellAFriend = tellAFriend;
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("sendersName", tellAFriend.getFrom());
		model.put("message", tellAFriend.getMessage());	
		return model;
	}

	@Override
	public String getMessageSubject() {
		StringBuffer subject = getSubjectTemplate(tellAFriend.getFrom());
		subject.append(" has sent you a message!");
		return subject.toString();
	}

}
