package com.mybuckstory.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.SellableItemService;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.SellableItem;

@Controller
public class StoreController{

	private static final Logger LOG = Logger.getLogger(StoreController.class);
	
	@Autowired
	private SellableItemService sellableItemService;

	@RequestMapping(value="/store", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, @RequestParam(value="sort", required=false) String sort, @RequestParam(value="order", required=false) String order) {
		ModelAndView mav = new ModelAndView("listSellableItem");
		mav.addObject("items", sellableItemService.findAllPaginated(start, max));
		mav.addObject("totalItems", sellableItemService.getCount());
		return mav;
	}
	
	@RequestMapping(value = "/store/addToCart/{id}", method = RequestMethod.GET)
	public ModelAndView addToCart(@PathVariable("id") Long itemId){
		ModelAndView mav = new ModelAndView("addtocart.dialog");
		
		SellableItem item = sellableItemService.getById(itemId);		
		mav.addObject("item", item);
		
		return mav;
	}
	
	
	@RequestMapping(value="/item/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception{
		return new ModelAndView("formSellableItem", "command", new SellableItem());
	}
	
	
	@RequestMapping(value="/item/show/{id}", method=RequestMethod.GET)
	public ModelAndView getById(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showItem");		
		mav.addObject("item", sellableItemService.getById(id));
		
		return mav;		
	}

	
	@RequestMapping(value="/item/save", method = RequestMethod.POST)
	public String save(SellableItem item, final ModelMap model, final BindingResult result, HttpServletResponse response) throws Exception{
		//TODO - build item validator
//		itemValidator.validate(item, result);
		if(result.hasErrors()){
			return "formSellableItem";
		}
		
		sellableItemService.create(item);

		return "redirect:/store/";
	}
	
	@RequestMapping(value="/item/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id){
		ModelAndView mav = new ModelAndView("formSellableItem");
		mav.addObject("item", sellableItemService.getById(id));
		return mav;
	}

	@RequestMapping(value="/item/delete", method = RequestMethod.POST)
	public String delete(@RequestParam("id") Long id){
		SellableItem item = sellableItemService.getById(id);
		sellableItemService.delete(item);
		return "redirect:/store/";
	}
	
	@RequestMapping(value="/item/comment/{id}", method = RequestMethod.POST)
	public void comment(@PathVariable("id") Long id, Comment comment, HttpServletResponse response) throws Exception{
		//TODO - implement
//		if(StringUtils.isNotBlank(comment.getText())){
//			SellableItem item = sellableItemService.comment(id, comment);
//			response.sendRedirect(item.getUri());	
//		}else{
//			SellableItem item = sellableItemService.getById(id);
//			response.sendRedirect(item.getUri());
//		}
	}

	@RequestMapping(value="/item/update", method = RequestMethod.POST)
	public String update(final SellableItem item, final ModelMap model, final BindingResult result) throws Exception{
		//TODO - validate
		try{
			sellableItemService.update(item);
			model.addAttribute("message", String.format("Updated Item %s", item.getId()));
			return "redirect:/store/";
		}catch(Exception e){
			LOG.warn("An error occurred while updating Item", e);
			model.addAttribute("command", item);
			model.addAttribute("exceptionMessage", e.getMessage());
			return "formSellableItem";
		}
	}
}
