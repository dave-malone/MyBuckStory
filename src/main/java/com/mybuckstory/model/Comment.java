package com.mybuckstory.model;


public class Comment extends AbstractAuditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4458622157130790028L;
	private String text;
	
	public Comment(){}
	
	public Comment(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
