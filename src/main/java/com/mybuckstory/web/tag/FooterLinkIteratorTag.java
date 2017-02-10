package com.mybuckstory.web.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.model.FooterLink;

public class FooterLinkIteratorTag extends AbstractIteratorTag<FooterLink> {

	private String category;
	
	@Override
	protected Iterator<FooterLink> init() throws JspException {	
		if(StringUtils.isNotBlank(category)){
			return getFooterLinkManager().findByCategory(category).iterator();
		}
		
		return getFooterLinkManager().findAll().iterator();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
