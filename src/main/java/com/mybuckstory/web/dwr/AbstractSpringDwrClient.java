package com.mybuckstory.web.dwr;

import com.mybuckstory.web.spring.SpringHelper;

public abstract class AbstractSpringDwrClient extends AbstractDwrClient {
	
	private SpringHelper ctx;	
	
	protected SpringHelper getWebAppCtx(){
		if(this.ctx == null){
			this.ctx = SpringHelper.getInstance(super.getWebContext().getServletContext());
		}
		
		return this.ctx;
	}
	
}
