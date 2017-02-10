package com.mybuckstory.web.command;

public class StorySearch {

	private Integer max;
	private Integer start;
	private String category;
	private String filter;
	private String generalSearch;
	
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getGeneralSearch() {
		return generalSearch;
	}
	public void setGeneralSearch(String generalSearch) {
		this.generalSearch = generalSearch;
	}
	
}
