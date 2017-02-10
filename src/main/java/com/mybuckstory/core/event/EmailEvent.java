package com.mybuckstory.core.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationEvent;

import com.mybuckstory.model.User;

public abstract class EmailEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7017852299566755872L;
	protected final Set<String> addresses = new HashSet<String>();
	protected final String address;
	protected final String velocityTemplate;
	protected boolean sendEmail;
	
	public EmailEvent(Object source, String to, String velocityTemplate) {
		super(source);		
		this.address = to;
		this.velocityTemplate = velocityTemplate;
		sendEmail = true;
	}
	
	public EmailEvent(Object source, User to, String velocityTemplate){
		this(source, to.getUsername(), velocityTemplate);
		
		if(!to.isEnabled() || to.getProfile().isDisableAllEmailNotifications()){
			sendEmail = false;
		}else{
			sendEmail = true;
		}
	}

	public EmailEvent(Object source, Collection<String> addresses, String velocityTemplate){
		this(source, "", velocityTemplate);
		this.addresses.addAll(addresses);		
	}
	
	public String getTo() {
		return address;
	}
	
	public Set<String> getAddresses() {
		return addresses;
	}
	
	public boolean sendEmail() {
		return sendEmail;
	}
	
	public boolean containsMultipleAddressees(){
		return !addresses.isEmpty();
	}
	
	public String getVelocityTemplate(){
		return velocityTemplate;
	}
	
	public abstract String getMessageSubject();
	
	public abstract Map<String, Object> getMessageModel();
	
	protected StringBuffer getSubjectTemplate(){
		return getSubjectTemplate("");
	}
	
	protected StringBuffer getSubjectTemplate(User from){
		return getSubjectTemplate(from.getProfile().getFullName());
	}
	
	protected StringBuffer getSubjectTemplate(String from){
		StringBuffer subject = new StringBuffer();
		if(StringUtils.isNotBlank(from)){
			subject.append(from);
		}
		return subject;
	}

	@Override
	public String toString() {
		return "EmailEvent [address=" + address + ", sendEmail=" + sendEmail
				+ ", addresses=" + addresses + ", velocityTemplate="
				+ velocityTemplate + ", getTimestamp()=" + getTimestamp()
				+ ", getSource()=" + getSource() + "]";
	}
	

	
}
