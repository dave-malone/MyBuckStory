package com.mybuckstory.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails, Identifiable {
	
	private Long id;
	private String passwordResetToken;
	private String username;
	private String reEnteredUsername;
	private String password;
	private String secretQuestion;
	private String answer;	
	private String reEnteredPassword;	
	
	private boolean acceptedTOS = false;
	private boolean enabled = false;
	private boolean credentialsNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean accountNonExpired = true;
	private boolean proStaff = false;
	
	private Date signupDate = new Date();	
	private Profile profile = new Profile();
	private Set<Role> roles = new HashSet<Role>();
	private Date lastLoginDate;	
	
	public User(){}
	
	public User(String username){
		this.username = username;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		if(profile != null){
			profile.setUser(this);
		}
		this.profile = profile;		
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public int compareTo(Object obj) {
		if(!(obj instanceof User)){
			return -1;
		}
		
		User p = (User)obj;
		
		return p.getUsername() == null ? (this.getUsername() == null ? 0 : 1) : p.getUsername().compareToIgnoreCase(this.getUsername());
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(this.roles.size());
		authorities.addAll(this.roles);		
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public boolean isEnabled() {
		return this.enabled;
	}
	
	public Set<Role> getRoles(){
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		
		if(!(obj instanceof User)){
			return false;
		}
		
		User user = (User)obj;
		
		return StringUtils.equalsIgnoreCase(user.username, this.username);
	}

	@Override
	public int hashCode() {
		int result = 23;
		result = 17 * result + (this.username == null ? 0 : this.username.hashCode());
		return result;
	}

	
	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public String getReEnteredPassword() {
		return reEnteredPassword;
	}

	public void setReEnteredPassword(String reEnteredPassword) {
		this.reEnteredPassword = reEnteredPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}

	public String getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public boolean isAcceptedTOS() {
		return acceptedTOS;
	}

	public void setAcceptedTOS(boolean acceptedTOS) {
		this.acceptedTOS = acceptedTOS;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("username: ")
			.append(getUsername())
			.append(" First Name: ")
			.append(profile.getFirstName())
			.append(" Last Name: ")
			.append(profile.getLastName());
			
		return buffer.toString();
	}

	public String getReEnteredUsername() {
		return reEnteredUsername;
	}

	public void setReEnteredUsername(String reEnteredUsername) {
		this.reEnteredUsername = reEnteredUsername;
	}

	public void setLastLoginDate(long timestamp) {
		this.lastLoginDate = new Date(timestamp);		
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public boolean isProStaff() {
		return proStaff;
	}

	public void setProStaff(boolean proStaff) {
		this.proStaff = proStaff;
	}
	
}
