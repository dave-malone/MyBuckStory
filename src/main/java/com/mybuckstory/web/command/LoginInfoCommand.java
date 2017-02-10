package com.mybuckstory.web.command;

import com.mybuckstory.model.User;

public class LoginInfoCommand {

	private String username;
	private String currPass;
	private String newPass;
	private String newPassConf;
	
	
	public LoginInfoCommand(){}
	
	public LoginInfoCommand(User user) {
		this.username = user.getUsername();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurrPass() {
		return currPass;
	}
	public void setCurrPass(String currPass) {
		this.currPass = currPass;
	}
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getNewPassConf() {
		return newPassConf;
	}
	public void setNewPassConf(String newPassConf) {
		this.newPassConf = newPassConf;
	}
	
}
