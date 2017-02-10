package com.mybuckstory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.AffiliateService;
import com.mybuckstory.model.Affiliate;

@Controller
public class AffiliateController {

	@Autowired
	private AffiliateService affiliateService;

	@RequestMapping(value="/affiliates.html", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView("list.affiliates");
		mav.addObject("affiliates", affiliateService.findAll());
		return mav;
	}

	@RequestMapping(value="/affiliate/manage", method=RequestMethod.GET)
	public ModelAndView manage() {
		ModelAndView mav = new ModelAndView("manage.affiliates");
		mav.addObject("affiliates", affiliateService.findAll());
		return mav;
	}

	@RequestMapping(value="/affiliate/create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("affiliates.form");
		mav.addObject("command", new Affiliate());
		return mav;
	}
	
	@RequestMapping(value="/affiliate/save", method=RequestMethod.POST)
	public String save(Affiliate affiliate) {
		affiliateService.create(affiliate);
		return "redirect:manage";
	}
	
	@RequestMapping(value="/affiliate/edit/{id}", method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("affiliates.form");
		Affiliate affiliate = affiliateService.getById(id);
		mav.addObject("command", affiliate);
		return mav;
	}
	
	@RequestMapping(value="/affiliate/update", method=RequestMethod.POST)
	public String update(Affiliate affiliate) {
		affiliateService.update(affiliate);
		return "redirect:manage";
	}
	
	@RequestMapping(value="/affiliate/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		Affiliate affiliate = affiliateService.getById(id);
		affiliateService.delete(affiliate);
		return "redirect:/affiliate/manage";
	}

}
