package com.mybuckstory.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.common.Constants;

public class Event extends AbstractAuditable implements Resource{
	
	private String title;
	private Date date = new Date();
	private String eventDay;
	private String eventMonth;
	private String eventYear;
	private String description;
	private boolean publishInUsersFeeds = false;
	private String uri;
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());
	private Set<MemberFeedItem> memberFeedItems = Collections.synchronizedSet(new HashSet<MemberFeedItem>());
	
	private String metaKeywords;
	private String metaDescription;
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() throws Exception{
		if(StringUtils.isNotBlank(eventDay) && StringUtils.isNotBlank(eventMonth) && StringUtils.isNotBlank(eventYear)){
			return Constants.BASIC_DATE_FORMATTER.parse(eventMonth + "/"  + eventDay + "/"  + eventYear);
		}
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublishInUsersFeeds() {
		return publishInUsersFeeds;
	}

	public void setPublishInUsersFeeds(boolean publishInUsersFeeds) {
		this.publishInUsersFeeds = publishInUsersFeeds;
	}

	public SortedSet<Comment> getComments() {
		return comments;
	}

	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}	
	
	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setEventDay(String eventDay) {
		this.eventDay = eventDay;
	}

	public void setEventMonth(String eventMonth) {
		this.eventMonth = eventMonth;
	}

	public void setEventYear(String eventYear) {
		this.eventYear = eventYear;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public Set<MemberFeedItem> getMemberFeedItems() {
		return memberFeedItems;
	}

	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems) {
		this.memberFeedItems = memberFeedItems;
	}

}
