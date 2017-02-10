package com.mybuckstory.model;

import java.util.HashSet;
import java.util.Set;

public class Album extends AbstractAuditable{

	private String name;
	private String description;
	
	private Set<Image> images = new HashSet<Image>();
	private Set<MemberFeedItem> memberFeedItems = new HashSet<MemberFeedItem>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<MemberFeedItem> getMemberFeedItems() {
		return memberFeedItems;
	}

	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems) {
		this.memberFeedItems = memberFeedItems;
	}
	
}
