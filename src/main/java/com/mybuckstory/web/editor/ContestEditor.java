package com.mybuckstory.web.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybuckstory.core.service.ContestService;
import com.mybuckstory.model.Contest;

public class ContestEditor extends PropertyEditorSupport{

	private static final Logger			LOG	= LoggerFactory.getLogger(ContestEditor.class);
	private final ContestService	contestService;

	public ContestEditor(ContestService contestService){
		super(Contest.class);
		this.contestService = contestService;
	}

	@Override
	public String getAsText() {
		Contest contest = (Contest)getValue();
		if(contest != null){
			return contest.getId().toString();
		}		
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LOG.debug("Looking for Competition with id " + text);
		
		try{
			Long id = Long.valueOf(text);
	        super.setValue(contestService.getById(id));
		}catch(NumberFormatException e){
			super.setValue(null);
		}
	}

}