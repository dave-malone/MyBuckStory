package com.mybuckstory.model;


public class Message extends AbstractAuditable {

	private User to;
	private Message inResponseTo;
	private String subject;
	private String content;
	private boolean mostRecent = true;
	private boolean unread = true;
	private boolean deleted = false;
	private boolean deletedFromSentMessages = false;
	
	public String getContent() {
		return content;
	}

	public void setContent(String text) {
		this.content = text;
	}

	public Message getInResponseTo(){
		return inResponseTo;
	}

	public void setInResponseTo(Message inResponseTo){
		this.inResponseTo = inResponseTo;
	}

	public String getSubject(){
		return subject;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public User getTo(){
		return to;
	}

	public void setTo(User to){
		this.to = to;
	}

	public boolean isMostRecent(){
		return mostRecent;
	}

	public void setMostRecent(boolean mostRecent){
		this.mostRecent = mostRecent;
	}

	public boolean isUnread(){
		return unread;
	}

	public void setUnread(boolean unread){
		this.unread = unread;
	}

	public boolean isDeleted(){
		return deleted;
	}

	public void setDeleted(boolean deleted){
		this.deleted = deleted;
	}

	public boolean isDeletedFromSentMessages() {
		return deletedFromSentMessages;
	}

	public void setDeletedFromSentMessages(boolean deletedFromSentMessages) {
		this.deletedFromSentMessages = deletedFromSentMessages;
	}

	

}
