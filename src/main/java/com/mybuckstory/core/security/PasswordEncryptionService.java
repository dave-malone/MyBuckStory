package com.mybuckstory.core.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import com.mybuckstory.model.User;

public class PasswordEncryptionService {

	private MessageDigestPasswordEncoder passwordEncoder;
	private SaltSource saltSource;	
	
	public void encodePassword(UserDetails userDetails){
		Object salt = null;

		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails);
		}
		
		if(userDetails instanceof User){
			User user = (User)userDetails;
			if(user.getPassword() != null){
				user.setPassword(encode(user.getPassword(), salt));
			}
		}
	}
	
	public String encode(String text, Object salt) throws DataAccessException {
		return passwordEncoder.encodePassword(text, salt);
	}	
	
	public MessageDigestPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	public void setPasswordEncoder(MessageDigestPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	public SaltSource getSaltSource() {
		return saltSource;
	}
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}
	
	
	
}
