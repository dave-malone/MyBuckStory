package com.mybuckstory.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is synonymous with the term "Partners"
 * 
 * @author Dave Malone
 *
 */
public class Affiliate extends AbstractSingleImageContainer {

	private String name;
	private String website;	

	private String description;
	
	private Address address = new Address();
	private String phone;
	private String fax;	
	
	private Set<MemberFeedItem> memberFeedItems = Collections.synchronizedSet(new HashSet<MemberFeedItem>());
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<MemberFeedItem> getMemberFeedItems() {
		return memberFeedItems;
	}

	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems) {
		this.memberFeedItems = memberFeedItems;
	}
	

}
