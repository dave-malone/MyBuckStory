package com.mybuckstory.web.editor;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import com.mybuckstory.core.service.ContestService;
import com.mybuckstory.model.Contest;

public class ContestSetEditor extends CustomCollectionEditor{

	private static final Logger			LOG	= LoggerFactory.getLogger(ContestSetEditor.class);
	private final ContestService	contestService;

	public ContestSetEditor(ContestService contestService){
		super(Set.class);
		this.contestService = contestService;
	}

	@Override
	protected Object convertElement(Object element){
		if(element instanceof Contest){
			LOG.debug("Element is a Competition; returning: " + element);
			return element;
		}
		if(element instanceof String && StringUtils.isNotBlank((String)element)){
			LOG.debug("Looking for Competition with id " + element);
			Long id = Long.valueOf((String)element);
			return contestService.getById(id);
		}
		if(element instanceof Long){
			LOG.debug("Getting Competition with id " + element);
			return contestService.getById((Long) element);
		}

		LOG.warn("Don't know what to do with: " + element + "; type: " + element.getClass().getName());
		return null;
	}

}