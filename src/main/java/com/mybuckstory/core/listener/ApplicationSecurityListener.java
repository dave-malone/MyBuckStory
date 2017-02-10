package com.mybuckstory.core.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.mybuckstory.core.service.UserService;
import com.mybuckstory.model.User;

@Component
public class ApplicationSecurityListener implements ApplicationListener<AbstractAuthenticationEvent> {

	@Autowired
	private UserService userService;
	private static final Logger logger = Logger.getLogger(ApplicationSecurityListener.class);

	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		if(event instanceof AuthenticationSuccessEvent || event instanceof InteractiveAuthenticationSuccessEvent){
			
			logger.debug("authSuccess:" + event);
			Object principal = event.getAuthentication().getPrincipal();
			if(principal instanceof User){				
				User userDetails = (User)principal;
				User user = userService.getById(userDetails.getId());
				user.setLastLoginDate(event.getTimestamp());
				userService.update(user);				
			}
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
