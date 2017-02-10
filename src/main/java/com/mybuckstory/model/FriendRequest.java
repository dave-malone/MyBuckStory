package com.mybuckstory.model;


public class FriendRequest extends AbstractAuditable {

	private User to;
	private String message;
	private boolean accepted = false;
	private boolean responded = false;
	
	
	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public boolean isResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}

}
