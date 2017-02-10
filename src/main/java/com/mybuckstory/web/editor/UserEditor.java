package com.mybuckstory.web.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybuckstory.core.service.UserService;
import com.mybuckstory.model.User;

public class UserEditor extends PropertyEditorSupport{

	private static final Logger			LOG	= LoggerFactory.getLogger(UserEditor.class);
	private final UserService	userService;

	public UserEditor(UserService userService){
		super(User.class);
		this.userService = userService;
	}

	@Override
	public String getAsText() {
		User user = (User)getValue();
		if(user != null){
			return user.getId().toString();
		}
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LOG.debug("Looking for User with id " + text);
		
		try{
			Long id = Long.valueOf(text);
	        super.setValue(userService.getById(id));
		}catch(NumberFormatException e){
			super.setValue(null);
		}
	}

}