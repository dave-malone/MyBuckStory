package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

import com.mybuckstory.model.Message;

public class MessageSentEvent extends EmailEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6188581266400277991L;
	private final Message message;
	
	public MessageSentEvent(Object source, Message message) {
		super(source, message.getTo(), "com/mybuckstory/core/service/messageNotification.vm");
		this.message = message;
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		model.put("senderFirstName", message.getCreatedBy().getProfile().getFirstName());	
		return model;
	}

	@Override
	public String getMessageSubject() {
		StringBuffer subject = getSubjectTemplate(message.getCreatedBy());		
		subject.append(" sent you a message");
		return subject.toString();
	}

}
