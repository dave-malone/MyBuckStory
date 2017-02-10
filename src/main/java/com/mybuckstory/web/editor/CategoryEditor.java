package com.mybuckstory.web.editor;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.model.Category;

public class CategoryEditor extends PropertyEditorSupport{

	private static final Logger			LOG	= LoggerFactory.getLogger(CategoryEditor.class);
	private final CategoryService	categoryService;

	public CategoryEditor(CategoryService categoryService){
		super(Category.class);
		this.categoryService = categoryService;
	}

	@Override
	public String getAsText() {
		Category category = (Category)getValue();
		if(category != null){
			return category.getId().toString();
		}		
		return null;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		LOG.debug("Looking for Category with id " + text);
		try{
			Long id = Long.valueOf(text);
	        super.setValue(categoryService.getById(id));
		}catch(NumberFormatException e){
			super.setValue(null);
		}
	}

}