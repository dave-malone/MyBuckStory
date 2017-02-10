package com.mybuckstory.web.tag;

import javax.servlet.jsp.tagext.BodyTagSupport;

import com.mybuckstory.web.spring.SpringHelper;

public abstract class SpringTagSupport extends BodyTagSupport {
	
	private SpringHelper ctx;	
	
	protected SpringHelper getWebAppCtx(){
		if(this.ctx == null){
			this.ctx = SpringHelper.getInstance(pageContext.getServletContext());
		}
		
		return this.ctx;
	}
}
