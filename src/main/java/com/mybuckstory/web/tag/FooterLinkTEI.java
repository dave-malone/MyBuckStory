package com.mybuckstory.web.tag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import com.mybuckstory.model.FooterLink;

public class FooterLinkTEI extends TagExtraInfo{

	public VariableInfo[] getVariableInfo(TagData data) {
		
		return new VariableInfo[] {
		         new VariableInfo(data.getAttributeString("id"),
		            FooterLink.class.getName(),
		            true,
		            VariableInfo.AT_BEGIN)
		      };
	}

}