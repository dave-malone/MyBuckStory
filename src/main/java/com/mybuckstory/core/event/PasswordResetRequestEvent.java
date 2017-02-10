package com.mybuckstory.core.event;

import java.util.HashMap;
import java.util.Map;

import com.mybuckstory.model.User;

public class PasswordResetRequestEvent extends EmailEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4988046956418914710L;
	private final User to;
	
	public PasswordResetRequestEvent(Object source, User to) {
		super(source, to, "com/mybuckstory/core/service/passwordResetMessage.vm");
		this.to = to;
		super.sendEmail = true;
	}

	@Override
	public Map<String, Object> getMessageModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", to);	
		return model;
	}

	@Override
	public String getMessageSubject() {
		StringBuffer subject = getSubjectTemplate();
		subject.append("Password Reset Request");
		return subject.toString();
	}

}
