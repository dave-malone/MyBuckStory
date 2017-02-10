package com.mybuckstory.web.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class TellAFriend{
	
	private final Set<String> allFriendsEmailAddys = Collections.synchronizedSet(new HashSet<String>());
	
	private String from;
	private String fromEmail;
	
	private String email1;
	private String email2;
	private String email3;
	private String email4;
	
	private String message;

	public String getFrom(){
		return from;
	}

	public void setFrom(String from){
		this.from = from;
	}

	public String getFromEmail(){
		return fromEmail;
	}

	public void setFromEmail(String fromEmail){
		this.fromEmail = fromEmail;
	}

	public String getEmail1(){
		return email1;
	}

	public void setEmail1(String email1){
		this.email1 = email1;
		add(email1);
	}

	public String getEmail2(){
		return email2;
		
	}

	public void setEmail2(String email2){
		this.email2 = email2;
		add(email2);
	}

	public String getEmail3(){
		return email3;
	}

	public void setEmail3(String email3){
		this.email3 = email3;
		add(email3);
	}

	public String getEmail4(){
		return email4;
	}

	public void setEmail4(String email4){
		this.email4 = email4;
		add(email4);
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message = message;
	}
	
	public Set<String> getDestinationEmailAddys(){
		return allFriendsEmailAddys;		
	}
	
	void add(String friendsEmailAddy){
		if(StringUtils.isNotBlank(friendsEmailAddy)){
			allFriendsEmailAddys.add(friendsEmailAddy);
		}
	}

}
