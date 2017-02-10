package com.mybuckstory.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.mybuckstory.core.service.AffiliateService;
import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.core.service.ContestService;
import com.mybuckstory.model.Affiliate;
import com.mybuckstory.model.Category;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Contest;
import com.mybuckstory.web.command.EditContestCommand;
import com.mybuckstory.web.editor.AffiliateEditor;
import com.mybuckstory.web.editor.CategoryEditor;
import com.mybuckstory.web.validator.ContestValidator;
import com.mybuckstory.web.validator.EditContestCommandValidator;

@Controller
public class ContestController {

	@Autowired
	private ContestService service;
	@Autowired
	private ContestValidator validator;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private EditContestCommandValidator editContestCommandValidator;

	@Autowired
	private AffiliateService affiliateService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Affiliate.class, new AffiliateEditor(affiliateService));
		binder.registerCustomEditor(Category.class, new CategoryEditor(categoryService));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), false));
	}
	
	
	@ModelAttribute("affiliates")
	public List<Affiliate> populateAffiliates(){
		return affiliateService.findAll();
	}
	
	@ModelAttribute("categories")
	public List<Category> populateCategories(){
		return categoryService.getParentCategories();
	}
	
	@RequestMapping(value="/contest/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception{
		ModelAndView mav = new ModelAndView("contestForm");
		mav.addObject("command", new Contest());
		return mav;
	}
	
	@RequestMapping(value="/contests", method=RequestMethod.GET)
	public ModelAndView legacyList(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max) {
		ModelAndView mav = new ModelAndView("listContests");
		mav.addObject("contests", service.findAllPaginated(start, max));
		mav.addObject("totalContest", service.getCount());
		return mav;
	}
	
	@RequestMapping(value="/contests/{year}/{month}/{day}/{title}/*", method=RequestMethod.GET)
	public ModelAndView show(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day, @PathVariable("title") String title){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showContest");
		
		String uri = String.format("/contests/%s/%s/%s/%s/", year, month, day, title);
		
		Contest contest = service.findByUri(uri);
		mav.addObject("contest", contest);
		
		return mav;
	}
	
	@RequestMapping(value="/contest/manage", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, @RequestParam(value="sort", required=false) String sort, @RequestParam(value="order", required=false) String order) {
		ModelAndView mav = new ModelAndView("manageContests");
		mav.addObject("contests", service.findAllPaginated(start, max));
		mav.addObject("totalContest", service.getCount());
		return mav;
	}
	
	@RequestMapping(value="/contest/show/{id}", method=RequestMethod.GET)
	public ModelAndView getById(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("showContest");
		mav.addObject("contest", service.getById(id));
		
		return mav;		
	}
	
	@RequestMapping(value="/contest/save", method = RequestMethod.POST)
	public ModelAndView save(Contest contest, BindingResult result, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("command", contest);
		
		validator.validate(contest, result);
		
		if(result.hasErrors()){		
			mav.setViewName("contestForm");
			mav.addObject("errorMessage", "There are some issues with the contest you are creating");
		}else{	
			service.create(contest);
			response.sendRedirect(contest.getUri());
			return null;
		}
		
		return mav;
	}

	@RequestMapping(value="/contest/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id){
		Contest contest = service.getById(id);
		service.delete(contest);
		return "redirect:/contest/manage";
	}
	
	@RequestMapping(value="/contest/comment/{id}", method = RequestMethod.POST)
	public void comment(@PathVariable("id") Long id, Comment comment, HttpServletResponse response) throws Exception{
		Contest contest = service.comment(id, comment);
		response.sendRedirect(contest.getUri());	
	}
	
	@RequestMapping(value="/contest/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) throws Exception{
		ModelAndView mav = new ModelAndView("contestForm");
		mav.addObject("command", new EditContestCommand(service.getById(id)));
		return mav;
	}

	@RequestMapping(value="/contest/update", method = RequestMethod.POST)
	public ModelAndView update(EditContestCommand command, BindingResult result, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("command", command);
		editContestCommandValidator.validate(command, result);
		if(result.hasErrors()){
			mav.setViewName("contestForm");			
			mav.addObject("errorMessage", "There are some issues with the contest you are updating");
			return mav;
		}else{	
			Contest contest = service.getById(command.getId());
			
			contest.setDescription(command.getDescription());
			contest.setEndDate(command.getEndDate());
			contest.setFile(command.getFile());
			contest.setMetaDescription(command.getMetaDescription());
			contest.setMetaKeywords(command.getMetaKeywords());
			contest.setRules(command.getRules());
			contest.setSponsor(command.getSponsor());
			contest.setStoryCategory(command.getStoryCategory());
			contest.setStartDate(command.getStartDate());
			contest.setTitle(command.getTitle());
			contest.setVersion(command.getVersion());
			
			service.update(contest);
			mav.addObject("successMessage", "Your contest has been updated");
			mav.setViewName("redirect:/contest/manage");			
		}
		
		return mav;
	}

}
