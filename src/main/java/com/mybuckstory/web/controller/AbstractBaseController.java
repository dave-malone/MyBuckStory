package com.mybuckstory.web.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Generic Spring MVC Controller providing some basic functionality for displaying a list of 
 * objects of type T, a single object of type T, creating, saving, updating, and deleting single
 * instances of type T.  This class also expects to perform validation using the {@link Validator} 
 * instance passed into the constructor.  This class will construct view names for the list, show, 
 * and form views using the {@link #type} field, which is also passed in as a constructor argument.   
 * 
 * @author Dave Malone
 *
 * @param <T>
 */
public abstract class AbstractBaseController<T, PK extends Serializable> {

	protected final Logger		LOG	= LoggerFactory.getLogger(getClass());
	protected final Class<T>	type;
	protected final String		typeName;
	protected final String		listView;
	protected final String		showView;
	protected final String		formView;

	protected AbstractBaseController(Class<T> type){
		this.type = type;
		this.typeName = type.getSimpleName();	

		this.listView = String.format("list%s", this.typeName);
		this.showView = String.format("show%s", this.typeName);
		this.formView = String.format("form%s", this.typeName);
	}
	
	/**
	 * Provides a CustomDateEditor using the format yyyyMMddHHmmss	
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));		
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(){
		return "redirect:list";
	}

	/**
	 * Adds an attribute to the ModelMap with the name <code>WordUtils.uncapitalize(this.typeName) + "List"</code>,
	 * calling the {@link #list()} method, using the returned values as the attribute value, and will return
	 * the "list view," which is created in the constructor using  <code>String.format("list%s", this.typeName)</code>
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, @RequestParam(value="sort", required=false) String sort, @RequestParam(value="order", required=false) String order){
		LOG.debug(String.format("listing %s", this.typeName));
		model.addAttribute(String.format("%sList", WordUtils.uncapitalize(this.typeName)), list(start, max, sort, order));
		model.addAttribute(String.format("%sTotal", WordUtils.uncapitalize(this.typeName)), getTotalCount());
		return listView;
	}

	/**
	 * Implementors are required to return a List of the generic type for the "list" view.  The {@link #list(ModelMap, Integer, Integer)} 
	 * method may pass in the start and max params, but those values may be null
	 * @param start - for pagination
	 * @param max - for pagination
	 * @param sort
	 * @param order
	 * @return
	 */
	protected abstract List<T> list(final Integer start, final Integer max, final String sort, final String order);
	
	protected abstract Long getTotalCount();

	/**
	 * Adds an attribute to the ModelMap with the name <code>typeName.toLowerCase()</code>, using
	 * the {@link #getById(Serializable)} return value as the attribute value, and returns the "show view", 
	 * which is created in the constructor using <code>String.format("show%s", this.typeName)</code>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") final PK id, final ModelMap model){
		LOG.debug(String.format("showing %s with id %s", this.typeName, id));
		model.addAttribute(WordUtils.uncapitalize(this.typeName), getById(id));
		return showView;
	}

	/**
	 * Implementors are required to return a single instance of the generic type for the "show" view
	 * @param id
	 * @return
	 */
	protected abstract T getById(PK id);

	/**
	 * Instantiates a new intance of {@link #type}, adding it as an attribute to the ModelMap
	 * with the name "command", which is the default name for form views, and returns the "form"
	 * view, which is created in the constructor using <code>String.format("form%s", this.typeName)</code>
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(final ModelMap model){
		LOG.debug(String.format("creating %s", this.typeName));
		try{
			model.addAttribute("command", type.newInstance());
		}catch(Exception e){
			LOG.error(String.format("unable to instantiate a new Model instance of %s", this.typeName), e);
		}
		
		return formView;
	}

	/**
	 * Uses the {@link #validator} to call {@link Validator#validate(Object, org.springframework.validation.Errors)}.
	 * If the validation results in any errors, then this method will return to the "form" view.  Otherwise,
	 * this method will delegate to {@link #save(Object)}.  If there are any exceptions thrown from {@link #save(Object)},
	 * this method will return to the "form" view, adding the exception message to the model with the attribute name
	 * <code>exceptionMessage</code>.  On successful save, this method will return with a redirect to the "list" method.
	 * 
	 * @param command
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("command") final T command, final ModelMap model){
//		getValidator().validate(command, result);
//		if(result.hasErrors()){
//			return formView;
//		}

		try{
			LOG.debug("saving new " + this.typeName);
			save(command);
			model.addAttribute("message", String.format("Saved new %s", this.typeName));
			return "redirect:list";
		}catch(Exception e){
			LOG.warn(String.format("An error occurred while saving %s", this.typeName), e);
			model.addAttribute("command", command);
			model.addAttribute("exceptionMessage", e.getMessage());
			return formView;
		}
	}	

	/**
	 * Implementors are required to persist this command however they see fit
	 * @param command
	 */
	protected abstract void save(T command);
	

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam("id") final PK id, final ModelMap model){
		try{
			T entity = getById(id);			

			if(entity == null){
				String message = String.format("%s with id %s does not exist", this.typeName, id);
				LOG.debug(message);
				model.addAttribute("exceptionMessage", message);
				return "redirect:list";
			}
			
			LOG.debug(String.format("deleting %s with id %s", this.typeName, id));
			
			delete(entity);
			
			model.addAttribute("message", String.format("Deleted %s with ID %s", this.typeName, id));
			
			return "redirect:list";
		}catch(Exception e){
			LOG.warn(String.format("An error occurred while deleting %s with id %s", this.typeName, id), e);
			model.addAttribute("exceptionMessage", e.getMessage());
			return "redirect:list";
		}
	}
	
	protected abstract void delete(T entity);
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final PK id, final ModelMap model){
		LOG.debug(String.format("editing %s with id %s", this.typeName, id));
		model.addAttribute("command", getById(id));
		addAttributesToEditModel(model);
		return formView;
	}
	
	protected abstract void addAttributesToEditModel(final ModelMap model);
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("command") final T command, final ModelMap model){
//		getValidator().validate(command, result);
//		if(result.hasErrors()){
//			return formView;
//		}

		try{
			LOG.debug(String.format("updating %s", this.typeName));
			update(command);
			model.addAttribute("message", String.format("Updated %s", this.typeName));
			return "redirect:list";
		}catch(Exception e){
			LOG.warn(String.format("An error occurred while updating %s", this.typeName), e);
			model.addAttribute("command", command);
			model.addAttribute("exceptionMessage", e.getMessage());
			return formView;
		}
	}
	
	protected abstract void update(T command);

}
