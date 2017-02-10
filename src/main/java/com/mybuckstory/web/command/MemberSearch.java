package com.mybuckstory.web.command;

import org.apache.commons.lang.StringUtils;

public class MemberSearch {	
	
	private static final String DEFAULT_LOCATION = "Location";
	private static final String DEFAULT_MEMBER_NAME = "Member Name";
	
	private String memberName;
	private String location;
	private int age;
	private String gender;
	private boolean online;
	private Integer maxResults;
	private Integer firstResult;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		memberName = StringUtils.trim(memberName);
		if(!DEFAULT_MEMBER_NAME.equalsIgnoreCase(memberName)){
			this.memberName = memberName;
		}
	}

	public String getLocation() {		
		return location;
	}

	public void setLocation(String location) {
		location = StringUtils.trim(location);
		if(!DEFAULT_LOCATION.equalsIgnoreCase(location)){
			this.location = location;
		}
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMax(Integer maxResults) {
		if(maxResults == null){
			maxResults = 10;
		}
		this.maxResults = maxResults;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setStart(Integer firstResult) {
		if(firstResult == null){
			firstResult = 0;
		}
		this.firstResult = firstResult;
	}
	
}
