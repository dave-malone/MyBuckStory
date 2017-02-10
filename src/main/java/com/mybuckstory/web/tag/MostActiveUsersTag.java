package com.mybuckstory.web.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import com.mybuckstory.model.User;

public class MostActiveUsersTag extends AbstractIteratorTag<User>{

	private static final int DEFAULT_MAX = 20;
	private int max;
	
	@Override
	protected Iterator<User> init() throws JspException {
		if(max < 1){
			max = DEFAULT_MAX;
		}
		return getUserManager().findMostActiveUsers(max).iterator();		
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	

}