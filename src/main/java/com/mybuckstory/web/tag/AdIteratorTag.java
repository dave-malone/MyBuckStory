package com.mybuckstory.web.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import com.mybuckstory.model.Ad;

public class AdIteratorTag extends AbstractIteratorTag<Ad>{

	private boolean enabledOnly;
	
	@Override
	protected Iterator init() throws JspException {
		if(enabledOnly){
			return getAdManager().findAllEnabled().iterator();
		}else{
			return getAdManager().findAll().iterator();
		}
	}

	public void setEnabledOnly(boolean enabledOnly){
		this.enabledOnly = enabledOnly;
	}

	

}