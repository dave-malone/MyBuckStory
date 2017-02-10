package com.mybuckstory.web.controller;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.PasswordResetService;
import com.mybuckstory.core.service.RoleService;
import com.mybuckstory.core.service.UserService;
import com.mybuckstory.model.Role;
import com.mybuckstory.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger LOG = Logger.getLogger(UserController.class);
	
	private final UserService userService;
	private final RoleService roleService;
	private final PasswordResetService passwordResetService;	
	
	@Autowired
	public UserController(UserService userService, RoleService roleService, PasswordResetService passwordResetService) {	
		this.userService = userService;
		this.roleService = roleService;
		this.passwordResetService = passwordResetService;
	}
	
	@RequestMapping(value="pwreset", method=RequestMethod.GET)
	public String passwordReset(){
		return "pwreset";
	}
	
	@RequestMapping(value="pwResetRequest", method=RequestMethod.POST)
	public ModelAndView passwordResetRequest(@RequestParam(value="email") String email) throws Exception {
		ModelAndView mav = new ModelAndView();
		try{
			passwordResetService.sendResetEmail(email);
			mav.setViewName("pwreset.conf.email.sent");
		}catch(Exception e){
			mav.setViewName("pwreset");
			mav.addObject("error", "Invalid Password Reset Attempt.  Please contact us at admin@mybuckstory.com for assistance");			
		}
		
		return mav;
	}
	
	@RequestMapping(value="pwResetConfirm", method=RequestMethod.GET)
	public ModelAndView resetPassword(@RequestParam(value="email") String email, @RequestParam(value="pw_rst_tkn") String pwResetToken) throws Exception {
		ModelAndView mav = new ModelAndView();
		try{
			String newPass = passwordResetService.resetPassword(email, pwResetToken);
			mav.addObject("newPassword", newPass);
			mav.setViewName("pwreset");	
		}catch(Exception e){
			mav.setViewName("pwreset");
			mav.addObject("error", "Invalid Password Reset Attempt.  Please contact us at admin@mybuckstory.com for assistance");			
		}
		
		return mav;
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
		model.addAttribute("userList", userService.findAllPaginated(start, max));
		model.addAttribute("userTotal", userService.getCount());
		return "userList";
	}

	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam("id") final Long id, final ModelMap model){
		try{
			User entity = userService.getById(id);			

			if(entity == null){
				String message = String.format("User with id %s does not exist", id);
				LOG.debug(message);
				model.addAttribute("exceptionMessage", message);
				return "redirect:list";
			}
			
			LOG.debug(String.format("deleting User with id %s", id));
			
			userService.delete(entity);
			
			model.addAttribute("message", String.format("Deleted User with ID %s", id));
			
			return "redirect:list";
		}catch(Exception e){
			LOG.warn(String.format("An error occurred while deleting User with id %s", id), e);
			model.addAttribute("exceptionMessage", e.getMessage());
			return "redirect:list";
		}
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, final ModelMap model){
		LOG.debug(String.format("editing User with id %s", id));
		model.addAttribute("command", userService.getById(id));
		model.addAttribute("roles", roleService.findAll());
		return "userForm";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("command") final User command, final ModelMap model){
		try{
			LOG.debug("Updating User");
			LOG.debug("roles from the command: " + command.getRoles());
			
			User user = userService.getById(command.getId());
			user.setEnabled(command.isEnabled());
			user.setPassword(command.getPassword());
			user.setUsername(command.getUsername());
			user.getProfile().setFirstName(command.getProfile().getFirstName());
			user.getProfile().setLastName(command.getProfile().getLastName());	
			user.setProStaff(command.isProStaff());
			
			Set<Role> roles = new HashSet<Role>();
			
			for(Role role : command.getRoles()){
				Role persistedRole = roleService.getById(role.getId());
				roles.add(persistedRole);
			}
			
			user.setRoles(roles);
			
			userService.update(user);
			model.addAttribute("message", "Updated User");
			return "redirect:list";
		}catch(Exception e){
			LOG.warn("An error occurred while updating User", e);
			model.addAttribute("command", command);
			model.addAttribute("exceptionMessage", e.getMessage());
			return "userForm";
		}
	}
	

}
