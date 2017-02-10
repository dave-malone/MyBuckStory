package com.mybuckstory.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.mybuckstory.model.User;

public class UserUtil {

	private UserUtil(){}
	
	public static boolean isAuthenticated(){
		boolean isAuthenticated = false;
		try{
			isAuthenticated = getCurrentUser() != null;
		}catch(Exception e){}
		
		return isAuthenticated;
	}
	
	public static User getCurrentUser(){
		if(SecurityContextHolder.getContext() == null){
			throw new IllegalStateException("Unable to obtain a security context");
		}
		
		if(SecurityContextHolder.getContext().getAuthentication() == null){
			throw new IllegalStateException("Unable to obtain authentication object; try logging in first");
		}
		
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (obj instanceof User){
			return (User)obj;
		}
		
		return null;
	}
	
}
