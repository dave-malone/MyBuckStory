package com.mybuckstory.web.editor;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import com.mybuckstory.core.service.PrizeService;
import com.mybuckstory.model.Prize;

public class PrizeSetEditor extends CustomCollectionEditor{

	private static final Logger			LOG	= LoggerFactory.getLogger(PrizeSetEditor.class);
	private final PrizeService	prizeService;

	public PrizeSetEditor(PrizeService prizeService){
		super(Set.class);
		this.prizeService = prizeService;
	}

	@Override
	protected Object convertElement(Object element){
		if(element instanceof Prize){
			LOG.debug("Element is a Prize; returning: " + element);
			return element;
		}
		if(element instanceof String && StringUtils.isNotBlank((String)element)){
			LOG.debug("Looking for Prize with id " + element);
			Long id = Long.valueOf((String)element);
			return prizeService.getById(id);
		}
		if(element instanceof Long){
			LOG.debug("Getting Prize with id " + element);
			return prizeService.getById((Long) element);
		}

		LOG.warn("Don't know what to do with: " + element + "; type: " + element.getClass().getName());
		return null;
	}

}