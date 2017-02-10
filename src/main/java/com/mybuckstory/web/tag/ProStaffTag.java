package com.mybuckstory.web.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import com.mybuckstory.model.User;

public class ProStaffTag extends AbstractIteratorTag<User>{
	
	@Override
	protected Iterator<User> init() throws JspException {
		return getUserManager().findAllProStaff().iterator();		
	}
	

}