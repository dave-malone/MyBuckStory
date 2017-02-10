package com.mybuckstory.web.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybuckstory.core.service.BadgeService;
import com.mybuckstory.model.Badge;

public class BadgeEditor extends PropertyEditorSupport{

	private static final Logger			LOG	= LoggerFactory.getLogger(BadgeEditor.class);
	private final BadgeService	badgeService;

	public BadgeEditor(BadgeService badgeService){
		super(Badge.class);
		this.badgeService = badgeService;
	}

	@Override
	public String getAsText() {
		Badge badge = (Badge)getValue();
		if(badge != null){
			return badge.getId().toString();
		}		
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LOG.debug("Looking for Badge with id " + text);
		try{
			Long id = Long.valueOf(text);
	        super.setValue(badgeService.getById(id));
		}catch(NumberFormatException e){
			super.setValue(null);
		}
	}

}