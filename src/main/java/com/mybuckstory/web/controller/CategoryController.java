package com.mybuckstory.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.model.Category;
import com.mybuckstory.web.editor.CategoryEditor;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractBaseController<Category, Long>{

	private final CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		super(Category.class);		
		this.categoryService = categoryService;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Category.class, new CategoryEditor(this.categoryService));
	}

	@ModelAttribute("parentCategories")
	public List<Category> populateCategories(){
		return categoryService.getParentCategories();
	}


	@Override
	protected List<Category> list(Integer start, Integer max, String sort, String order) {
		return categoryService.findAllPaginated(start, max);
	}


	@Override
	protected Long getTotalCount() {
		return categoryService.getCount();
	}

	@Override
	protected Category getById(Long id) {
		return categoryService.getById(id);
	}

	@Override
	protected void save(Category category) {
		categoryService.create(category);
	}

	@Override
	protected void delete(Category category) {
		categoryService.delete(category);
	}

	@Override
	protected void update(Category command) {
		Category category = categoryService.getById(command.getId());
		category.setName(command.getName());
		category.setParent(command.getParent());
		
		categoryService.update(category);
	}

	@Override
	protected void addAttributesToEditModel(ModelMap model) {
		// TODO Auto-generated method stub
		
	}

}
