package com.mybuckstory.web.tag;

import javax.servlet.jsp.JspException;

import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;

public class CurrentUserTag extends MbsTagSupport {

	public int doStartTag() throws JspException {
		User currentUser = null;
		try{
			currentUser = UserUtil.getCurrentUser();
			if(currentUser != null){
				getUserManager().refresh(currentUser);
			}
		}catch(Exception e){
			logger.info("Exception occurred while getting the current user", e);
		}
		setAttribute(getId(), currentUser);
		return SKIP_BODY;
	}
	
}
