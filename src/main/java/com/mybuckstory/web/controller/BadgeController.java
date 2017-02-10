package com.mybuckstory.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybuckstory.core.service.BadgeService;
import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.model.Badge;
import com.mybuckstory.model.Category;
import com.mybuckstory.web.editor.CategoryEditor;

@Controller
@RequestMapping("/badge")
public class BadgeController extends AbstractBaseController<Badge, Long>{

	private final BadgeService badgeService;
	private final CategoryService categoryService;
	
	@Autowired
	public BadgeController(BadgeService badgeService, CategoryService categoryService) {
		super(Badge.class);		
		this.badgeService = badgeService;
		this.categoryService = categoryService;
	}	
		
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Category.class, new CategoryEditor(this.categoryService));
	}

	@ModelAttribute("categories")
	public List<Category> populateCategories(){
		return categoryService.getParentCategories();
	}

	@Override
	protected List<Badge> list(Integer start, Integer max, String sort, String order) {
		return badgeService.findAllPaginated(start, max);
	}
	
	@Override
	protected Long getTotalCount() {
		return badgeService.getCount();
	}

	@Override
	protected Badge getById(Long id) {
		return badgeService.getById(id);
	}

	@Override
	protected void save(Badge badge) {
		badgeService.create(badge);
	}

	@Override
	protected void delete(Badge badge) {
		badgeService.delete(badge);
	}


	@Override
	protected void update(Badge badge) {
		badgeService.update(badge);
	}

	@Override
	protected void addAttributesToEditModel(ModelMap model) {
		// TODO Auto-generated method stub
		
	}

}
