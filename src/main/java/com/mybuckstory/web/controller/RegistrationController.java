package com.mybuckstory.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.RegistrationService;
import com.mybuckstory.model.User;
import com.mybuckstory.web.validator.RegistrationValidator;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private RegistrationValidator registrationValidator;
	@Autowired
	private RegistrationService registrationService;	
		
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), false));
	}	
	
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public ModelAndView signin(){
		ModelAndView mav = new ModelAndView("signin");		
		return mav;
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public ModelAndView signup(){
		ModelAndView mav = new ModelAndView("signup");
		mav.addObject("user", new User());
		return mav;
	}
	
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public ModelAndView submit(User user, BindingResult result){
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		
		registrationValidator.validate(user, result);
		
		if(result.hasErrors()){
			mav.setViewName("signup");			
		}else{		
			registrationService.register(user);
			mav.setViewName("conf.email.sent");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/confirm", method=RequestMethod.GET)
	public ModelAndView confirm(@RequestParam(value="uid") Long userId){
		ModelAndView mav = new ModelAndView("acct.enabled");			
		User user = registrationService.confirm(userId);
		mav.addObject("email", user.getUsername());
		mav.addObject("user", user);		
		return mav;
	}
}
