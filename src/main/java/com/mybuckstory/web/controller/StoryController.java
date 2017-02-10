package com.mybuckstory.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.core.service.TwitterService;
import com.mybuckstory.model.Category;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Story;
import com.mybuckstory.web.command.EditStoryCommand;
import com.mybuckstory.web.command.StorySearch;
import com.mybuckstory.web.editor.CategorySortedSetEditor;
import com.mybuckstory.web.validator.EditStoryCommandValidator;
import com.mybuckstory.web.validator.StoryValidator;

@Controller
public class StoryController {

	private static final Logger logger = Logger.getLogger(StoryController.class);
	
	@Autowired
	private StoryValidator storyValidator;	
	@Autowired
	private EditStoryCommandValidator editStoryCommandValidator;
	
	@Autowired
	private StoryService storyService;
	@Autowired
	private TwitterService twitterService;
	@Autowired
	private CategoryService categoryService;
	
		
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Set.class, "categories", new CategorySortedSetEditor(this.categoryService));
//		binder.setValidator(storyValidator);
	}

	@ModelAttribute("categories")
	public List<Category> populateCategories(){
		return categoryService.getParentCategories();
	}

	@RequestMapping(value="/story/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception{
		return new ModelAndView("formStory", "story", new Story());
	}
	
	
	@RequestMapping(value="/stories/{year}/{month}/{day}/{title}/*", method=RequestMethod.GET)
	public ModelAndView show(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day, @PathVariable("title") String title){
		ModelAndView mav = new ModelAndView("showStory");
				
		String uri = String.format("/stories/%s/%s/%s/%s/", year, month, day, title);
		logger.debug("looking for story with uri " + uri);
		Story story = storyService.findByUri(uri);
		mav.addObject("story", story);
		
		return mav;
	}
	
	@RequestMapping(value="/stories.html", method=RequestMethod.GET)
	public ModelAndView legacyList(StorySearch search) {
		ModelAndView mav = new ModelAndView("listStory");
		mav.addObject("stories", storyService.search(search));
		mav.addObject("totalStories", storyService.getSearchCount(search));
		return mav;
	}
	
	@RequestMapping(value="/story/manage", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, @RequestParam(value="sort", required=false) String sort, @RequestParam(value="order", required=false) String order) {
		ModelAndView mav = new ModelAndView("manageStories");
		mav.addObject("stories", storyService.findAllPaginated(start, max));
		mav.addObject("totalStories", storyService.getCount());
		return mav;
	}
	
	@RequestMapping(value="/story/show/{id}", method=RequestMethod.GET)
	public ModelAndView getById(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showStory");		
		mav.addObject("story", storyService.getById(id));
		
		return mav;		
	}
	
	@RequestMapping(value="/story/tweet/{id}", method=RequestMethod.GET)
	public String tweet(@PathVariable("id") Long id, HttpServletRequest request){
		Story story = storyService.getById(id);
		
		if(story != null){
			try{
				String status = twitterService.updateStatus(story);				
		        request.setAttribute("status", status);
			}catch(Exception e){
				request.setAttribute("status", "Failed to update Twitter status;\n" + e.getMessage());
			}
		}else{
			request.setAttribute("status", "Could not find story");
		}
		
		return "redirect:manage";	
	}

	@RequestMapping(value="/story/save", method = RequestMethod.POST)
	public ModelAndView save(Story story, final BindingResult result, HttpServletResponse response) throws Exception{
		storyValidator.validate(story, result);
		if(result.hasErrors()){
			return new ModelAndView("formStory");
		}
		
		storyService.create(story);

		ModelAndView mav = new ModelAndView("submit.story.success");
		mav.addObject("story", story);
		return mav;
	}
	
	@RequestMapping(value="/story/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id){
		ModelAndView mav = new ModelAndView("formStory");
		mav.addObject("story", new EditStoryCommand(storyService.getById(id)));
		return mav;
	}

	@RequestMapping(value="/story/delete", method = RequestMethod.POST)
	public String delete(@RequestParam("id") Long id){
		Story story = storyService.getById(id);
		storyService.delete(story);
		return "redirect:manage";
	}
	
	@RequestMapping(value="/story/comment/{id}", method = RequestMethod.POST)
	public void comment(@PathVariable("id") Long id, Comment comment, HttpServletResponse response) throws Exception{
		if(StringUtils.isNotBlank(comment.getText())){
			Story story = storyService.comment(id, comment);
			response.sendRedirect(story.getUri());	
		}else{
			Story story = storyService.getById(id);
			response.sendRedirect(story.getUri());
		}
	}

	@RequestMapping(value="/story/update", method = RequestMethod.POST)
	public String update(EditStoryCommand command, final BindingResult result, HttpServletResponse response) throws Exception{
		editStoryCommandValidator.validate(command, result);
		if(result.hasErrors()){
			//TODO - change this so that the command gets added back into the view
			return "formStory";
		}
		
		Story story = storyService.getById(command.getId());
		story.setCategories(command.getCategories());
		story.setText(command.getText());
		story.setFile(command.getFile());
		story.setMetaDescription(command.getMetaDescription());
		story.setMetaKeywords(command.getMetaKeywords());
		story.setTitle(command.getTitle());
		story.setVersion(command.getVersion());		
		
		storyService.update(story);
		response.sendRedirect(story.getUri());
		
		return null;
	}

}
