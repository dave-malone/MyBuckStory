package com.mybuckstory.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Badge extends AbstractSingleImageContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6187419962068283608L;
	private String name;
	private String description;
	private Category storyCategory;
	private Integer numberOfStoriesInCategory;
	private Set<MemberFeedItem> memberFeedItems = Collections.synchronizedSet(new HashSet<MemberFeedItem>());
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<MemberFeedItem> getMemberFeedItems() {
		return memberFeedItems;
	}
	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems) {
		this.memberFeedItems = memberFeedItems;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getStoryCategory() {
		return storyCategory;
	}
	public void setStoryCategory(Category storyCategory) {
		this.storyCategory = storyCategory;
	}
	public Integer getNumberOfStoriesInCategory() {
		return numberOfStoriesInCategory;
	}
	public void setNumberOfStoriesInCategory(Integer numberOfStoriesInCategory) {
		this.numberOfStoriesInCategory = numberOfStoriesInCategory;
	}	
	
}
