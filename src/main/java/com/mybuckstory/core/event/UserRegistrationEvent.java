package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

import com.mybuckstory.model.User;

public class UserRegistrationEvent extends EmailEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2424420938361830211L;
	private final User to;
	
	public UserRegistrationEvent(Object source, User to) {
		super(source, to, "com/mybuckstory/core/service/signupConfirmation.vm");
		this.to = to;
		super.sendEmail = true;
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", to);		
		return model;
	}

	@Override
	public String getMessageSubject() {
		StringBuffer subject = getSubjectTemplate();
		subject.append("Registration Confirmation");
		return subject.toString();
	}

}
