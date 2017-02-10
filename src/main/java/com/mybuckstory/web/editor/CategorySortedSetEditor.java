package com.mybuckstory.web.editor;

import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.model.Category;

public class CategorySortedSetEditor extends CustomCollectionEditor{

	private static final Logger			LOG	= LoggerFactory.getLogger(CategorySortedSetEditor.class);
	private final CategoryService	categoryService;

	public CategorySortedSetEditor(CategoryService categoryService){
		super(SortedSet.class);
		this.categoryService = categoryService;
	}

	@Override
	protected Object convertElement(Object element){
		if(element instanceof Category){
			LOG.debug("Element is a Category; returning: " + element);
			return element;
		}
		if(element instanceof String && StringUtils.isNotBlank((String)element)){
			LOG.debug("Looking for Category with id " + element);
			Long id = Long.valueOf((String)element);
			return categoryService.getById(id);
		}
		if(element instanceof Long){
			LOG.debug("Getting Category with id " + element);
			return categoryService.getById((Long) element);
		}

		LOG.info("Don't know what to do with: " + element + "; type: " + element.getClass().getName());
		return element;
	}

}