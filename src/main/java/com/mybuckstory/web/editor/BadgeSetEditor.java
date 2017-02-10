package com.mybuckstory.web.editor;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import com.mybuckstory.core.service.BadgeService;
import com.mybuckstory.model.Badge;

public class BadgeSetEditor extends CustomCollectionEditor{

	private static final Logger			LOG	= LoggerFactory.getLogger(BadgeSetEditor.class);
	private final BadgeService	badgeService;

	public BadgeSetEditor(BadgeService badgeService){
		super(Set.class);
		this.badgeService = badgeService;
	}

	@Override
	protected Object convertElement(Object element){
		if(element instanceof Badge){
			LOG.debug("Element is a Badge; returning: " + element);
			return element;
		}
		if(element instanceof String && StringUtils.isNotBlank((String)element)){
			LOG.debug("Looking for Badge with id " + element);
			Long id = Long.valueOf((String)element);
			return badgeService.getById(id);
		}
		if(element instanceof Long){
			LOG.debug("Getting Badge with id " + element);
			return badgeService.getById((Long) element);
		}

		LOG.warn("Don't know what to do with: " + element + "; type: " + element.getClass().getName());
		return null;
	}

}