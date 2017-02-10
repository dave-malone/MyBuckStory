package com.mybuckstory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.event.TellAFriendEvent;
import com.mybuckstory.core.service.ApplicationEventService;
import com.mybuckstory.web.command.TellAFriend;
import com.mybuckstory.web.validator.TellAFriendValidator;

@Controller
@RequestMapping("/refer")
public class ReferralController {
	
	@Autowired
	private TellAFriendValidator validator;
	
	@Autowired
	private ApplicationEventService applicationEventService;
	
	@RequestMapping(value="form", method=RequestMethod.GET)
	public ModelAndView form(){
		ModelAndView mav = new ModelAndView("refer.form");		
		mav.addObject("command", new TellAFriend());		
		return mav;
	}
	
	@RequestMapping(value="submit", method=RequestMethod.POST)
	public ModelAndView submit(TellAFriend tellAFriendCommand, BindingResult result) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		validator.validate(tellAFriendCommand, result);
		if(result.hasErrors()){
			mav.setViewName("refer.form");
			mav.addObject("command", tellAFriendCommand);
			mav.addObject("errorText", "There were some problems with the information you entered");
		}else{
			applicationEventService.publishAsynch(new TellAFriendEvent(this, tellAFriendCommand));
			mav.setViewName("refer.success");
		}
		
		return mav;
	}
	
}
