package com.mybuckstory.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority, Identifiable {

	private Long id;
	private String name;	
	private String title;
	private Set<User> users = new HashSet<User>();
	
	
	public Role(){}
	
	public Role(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int compareTo(Object obj) {
		if(!(obj instanceof Role)){
			return -1;
		}
		Role r = (Role)obj;
		
		return r.getName() == null ? (this.getName() == null ? 0 : 1) : r.getName().compareToIgnoreCase(this.getName());
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public boolean addUser(User user){
		return this.users.add(user);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		
		if(!(obj instanceof Role)){
			return false;
		}
		
		Role role = (Role)obj;
		
		return StringUtils.equalsIgnoreCase(role.name, this.name);
	}

	@Override
	public int hashCode() {
		int result = 23;
		result = 17 * result + (this.name == null ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getAuthority() {
		return this.name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
