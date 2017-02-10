package com.mybuckstory.web.tag;

import javax.servlet.jsp.JspException;

public class GetRecentStoriesTag extends MbsTagSupport {

	private static final int DEFAULT_STORY_COUNT = 4;
	private Long authorId;
	private int storyCount;
	private String category;
	
	public int doStartTag() throws JspException {
		if(storyCount <= 0){
			storyCount = DEFAULT_STORY_COUNT;
		}
		setAttribute(getId(), getStoryManager().getRecentStories(storyCount, authorId, category));
		return SKIP_BODY;
	}

	public int getStoryCount() {
		return storyCount;
	}

	public void setStoryCount(int storyCount) {
		this.storyCount = storyCount;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
