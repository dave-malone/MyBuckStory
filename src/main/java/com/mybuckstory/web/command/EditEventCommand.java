package com.mybuckstory.web.command;

import java.util.Date;

import com.mybuckstory.model.Event;

public class EditEventCommand {

	private Long id;
	private Integer version;
	private String title;
	private String description;
	private Date date;
	private boolean publishInUsersFeeds;
	
	private String metaDescription;
	private String metaKeywords;
	
	
	public EditEventCommand(){}
	
	public EditEventCommand(Event event){
		this.id = event.getId();
		this.version = event.getVersion();
		this.title = event.getTitle();
		this.description = event.getDescription();
		this.publishInUsersFeeds = event.isPublishInUsersFeeds();
		try {
			this.date = event.getDate();
		} catch (Exception e) {}
		this.metaDescription = event.getMetaDescription();
		this.metaKeywords = event.getMetaKeywords();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isPublishInUsersFeeds() {
		return publishInUsersFeeds;
	}

	public void setPublishInUsersFeeds(boolean publishInUsersFeeds) {
		this.publishInUsersFeeds = publishInUsersFeeds;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	
	
	
}
