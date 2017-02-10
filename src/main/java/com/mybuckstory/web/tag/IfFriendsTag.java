package com.mybuckstory.web.tag;

import javax.servlet.jsp.JspException;

import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;

public class IfFriendsTag extends MbsTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -71624494892914241L;
	private User member;
	
	public int doStartTag() throws JspException {
		if(member == null){
			//what member...
			return SKIP_BODY;
		}
		
		try{			
			User currentUser = UserUtil.getCurrentUser();
			
			if(currentUser == null){
				//we aren't logged in
				return SKIP_BODY;
			}
			
			if(member.equals(currentUser)){
				//this is Me!  As much as I want to be friends with myself... I can't
				return SKIP_BODY;
			}			
			
			if(member.getProfile().getFriends().contains(currentUser)){
				//we are friends! So show this content
				return EVAL_BODY_INCLUDE;
			}else{
				//we are not friends, so don't show this content
				return SKIP_BODY;
			}
		}catch(Exception e){
			//we aren't logged in
			return SKIP_BODY;
		}
	}

	public void setMember(User user){
		this.member = user;
	}	
	
}
