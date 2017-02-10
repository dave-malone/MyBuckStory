package com.mybuckstory.model;

import java.util.HashSet;
import java.util.Set;


public class Prize extends AbstractSingleImageContainer{

	private String title;
	private String description;
	
	private Set<ContestPrize> contestPrizes = new HashSet<ContestPrize>();
	
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
	public Set<ContestPrize> getContestPrizes() {
		return contestPrizes;
	}
	public void setContestPrizes(Set<ContestPrize> contestPrizes) {
		this.contestPrizes = contestPrizes;
	}
	
	
}
