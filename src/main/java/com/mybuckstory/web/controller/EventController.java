package com.mybuckstory.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.EventService;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Event;
import com.mybuckstory.web.command.EditEventCommand;
import com.mybuckstory.web.validator.EditEventCommandValidator;
import com.mybuckstory.web.validator.EventValidator;

@Controller
public class EventController {

	private final EventService eventService;
	private final EventValidator validator;
	private final EditEventCommandValidator editEventCommandValidator;
	
	@Autowired
	public EventController(EventService eventService, EventValidator validator, EditEventCommandValidator editEventCommandValidator) {	
		this.eventService = eventService;		
		this.validator = validator;
		this.editEventCommandValidator = editEventCommandValidator;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), false));
	}
	
	@RequestMapping(value="/event/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception{
		ModelAndView mav = new ModelAndView("create.event.dialog");
		mav.addObject("command", new Event());
		return mav;
	}
	
	@RequestMapping(value="/events.html", method=RequestMethod.GET)
	public ModelAndView legacyList(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max) {
		ModelAndView mav = new ModelAndView("listEvents");
		mav.addObject("events", eventService.findAllPaginated(start, max));
		mav.addObject("totalEvents", eventService.getCount());
		return mav;
	}
	
	@RequestMapping(value="/events/{year}/{month}/{day}/{title}/*", method=RequestMethod.GET)
	public ModelAndView show(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day, @PathVariable("title") String title){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showEvent");
		
		String uri = String.format("/events/%s/%s/%s/%s/", year, month, day, title);
		
		Event event = eventService.findByUri(uri);
		mav.addObject("event", event);
		
		return mav;
	}
	
	@RequestMapping(value="/event/manage", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, @RequestParam(value="sort", required=false) String sort, @RequestParam(value="order", required=false) String order) {
		ModelAndView mav = new ModelAndView("manageEvents");
		mav.addObject("events", eventService.findAllPaginated(start, max));
		mav.addObject("totalEvents", eventService.getCount());
		return mav;
	}
	
	@RequestMapping(value="/event/show/{id}", method=RequestMethod.GET)
	public ModelAndView getById(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("showEvent");
		mav.addObject("event", eventService.getById(id));
		
		return mav;		
	}
	
	@RequestMapping(value="/event/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) throws Exception{
		ModelAndView mav = new ModelAndView("eventForm");
		mav.addObject("command", new EditEventCommand(eventService.getById(id)));
		return mav;
	}

	@RequestMapping(value="/event/save", method = RequestMethod.POST)
	public ModelAndView save(Event event, BindingResult result) throws Exception{
		ModelAndView mav = new ModelAndView();	

		validator.validate(event, result);		
		if(result.hasErrors()){		
			mav.addObject("command", event);
			mav.setViewName("create.event.dialog");
			mav.addObject("errorMessage", "There are some issues with the event you are creating");
		}else{	
			eventService.create(event);
			mav.addObject("command", event);
			mav.setViewName("create.event.success.dialog");			
		}
		
		return mav;
	}

	@RequestMapping(value="/event/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id){
		Event event = eventService.getById(id);
		eventService.delete(event);
		return "redirect:/event/manage";
	}
	
	@RequestMapping(value="/event/comment/{id}", method = RequestMethod.POST)
	public void comment(@PathVariable("id") Long id, Comment comment, HttpServletResponse response) throws Exception{
		Event event = eventService.comment(id, comment);
		response.sendRedirect(event.getUri());	
	}

	@RequestMapping(value="/event/update", method = RequestMethod.POST)
	public ModelAndView update(EditEventCommand command, BindingResult result, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		editEventCommandValidator.validate(command, result);
		if(result.hasErrors()){
			mav.addObject("errorMessage", "There are some issues with the event you are updating");
			mav.setViewName("eventForm");
		}else{		
			Event event = eventService.getById(command.getId());
			event.setDate(command.getDate());
			event.setDescription(command.getDescription());
			event.setMetaDescription(command.getMetaDescription());
			event.setMetaKeywords(command.getMetaKeywords());
			event.setTitle(command.getTitle());
			event.setVersion(command.getVersion());		
			
			eventService.update(event);
			mav.setViewName("redirect:" + event.getUri());	
		}
		
		return mav;
	}

}
