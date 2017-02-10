package com.mybuckstory.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.common.Constants;
import com.mybuckstory.core.event.UserRegistrationEvent;
import com.mybuckstory.model.User;

@Service
@Transactional
public class RegistrationService{

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;	
	@Autowired
	private ApplicationEventService eventService;
	
	public void register(User user){
		user.getRoles().add(roleService.findByName(Constants.DEFAULT_USER_ROLE));
		userService.create(user);
		eventService.publishAsynch(new UserRegistrationEvent(this, user));
	}	
	
	public User confirm(Long userId){
		User user = userService.getById(userId);
		if(!user.isEnabled() && user.isAccountNonLocked() && user.isAccountNonExpired() && user.isCredentialsNonExpired()){
			user.setEnabled(true);
			userService.saveOrUpdate(user);
		}		
		return user;
	}

}
