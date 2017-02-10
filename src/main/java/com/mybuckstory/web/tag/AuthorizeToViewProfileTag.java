package com.mybuckstory.web.tag;

import javax.servlet.jsp.JspException;

import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;

//TODO - remove boolean option, make this "display if authorized" instead
public class AuthorizeToViewProfileTag extends MbsTagSupport {

	
	private User user;
	private boolean displayIfAuthorized;
	
	public int doStartTag() throws JspException {
		if(user == null){
			return !displayIfAuthorized ? EVAL_BODY_INCLUDE : SKIP_BODY;
		}
		try{			
			User currentUser = UserUtil.getCurrentUser();
			if(user.equals(currentUser)){
				return displayIfAuthorized ? EVAL_BODY_INCLUDE : SKIP_BODY;
			}
		}catch(Exception e){
			logger.warn(e);
		}
		
		
		return !displayIfAuthorized ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}


	public void setDisplayIfAuthorized(boolean displayIfAuthorized) {
		this.displayIfAuthorized = displayIfAuthorized;
	}

	public void setUser(User user){
		this.user = user;
	}

	
	
}
