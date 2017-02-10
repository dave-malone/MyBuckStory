package com.mybuckstory.core.event;

import org.springframework.context.ApplicationEvent;

import com.mybuckstory.model.MemberFeedItem;

public class MemberFeedEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6020085674159401093L;
	protected final MemberFeedItem memberFeedItem;
	
	public MemberFeedEvent(Object source, MemberFeedItem memberFeedItem) {
		super(source);
		this.memberFeedItem = memberFeedItem;
	}

	public MemberFeedItem getMemberFeedItem() {
		return memberFeedItem;
	}	

}
