package com.mybuckstory.web.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import com.mybuckstory.model.User;

public class RecentLoginsTag extends AbstractIteratorTag<User>{

	@Override
	protected Iterator<User> init() throws JspException {
		return getUserManager().findAllRecentlyLoggedIn().iterator();
	}

	

}