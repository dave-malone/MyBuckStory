package com.mybuckstory.web.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybuckstory.core.service.AffiliateService;
import com.mybuckstory.model.Affiliate;

public class AffiliateEditor extends PropertyEditorSupport{

	private static final Logger			LOG	= LoggerFactory.getLogger(AffiliateEditor.class);
	private final AffiliateService	affiliateService;

	public AffiliateEditor(AffiliateService affiliateService){
		super(Affiliate.class);
		this.affiliateService = affiliateService;
	}

	@Override
	public String getAsText() {
		Affiliate affiliate = (Affiliate)getValue();
		if(affiliate != null){
			return affiliate.getId().toString();
		}		
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LOG.debug("Looking for Affiliate with id " + text);
		try{
			Long id = Long.valueOf(text);
	        super.setValue(affiliateService.getById(id));
		}catch(NumberFormatException e){
			super.setValue(null);
		}
	}

}