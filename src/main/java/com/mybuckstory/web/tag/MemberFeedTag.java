package com.mybuckstory.web.tag;

import javax.servlet.jsp.JspException;

public class MemberFeedTag extends MbsTagSupport {

	private static final int DEFAULT_COUNT = 4;
	
	private int count;	
	
	public int doStartTag() throws JspException {
		if(count <= 0){
			count = DEFAULT_COUNT;
		}		
		
		setAttribute(getId(), getMemberFeedManager().findAllPaginated(0, count));
		return SKIP_BODY;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}	

}
