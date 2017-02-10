package com.mybuckstory.exception;

import com.mybuckstory.model.User;

public class UserDisabledException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2037499907259348770L;
	private User user;
	
	public UserDisabledException(User user){
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}

	@Override
	public String getMessage(){
		return "User account has not yet been enabled.  Please contact the administator for assistance";
	}
	
	
}
