package com.mybuckstory.web.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybuckstory.core.service.PrizeService;
import com.mybuckstory.model.Prize;

public class PrizeEditor extends PropertyEditorSupport{

	private static final Logger			LOG	= LoggerFactory.getLogger(PrizeEditor.class);
	private final PrizeService	prizeService;

	public PrizeEditor(PrizeService prizeService){
		super(Prize.class);
		this.prizeService = prizeService;
	}

	@Override
	public String getAsText() {
		Prize prize = (Prize)getValue();
		if(prize != null){
			return prize.getId().toString();
		}
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LOG.debug("Looking for Prize with id " + text);
		
		try{
			Long id = Long.valueOf(text);
	        super.setValue(prizeService.getById(id));
		}catch(NumberFormatException e){
			super.setValue(null);
		}
	}

}