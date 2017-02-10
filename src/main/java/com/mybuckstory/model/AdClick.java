package com.mybuckstory.model;

import java.util.Date;

public class AdClick{

	private Long id;	
	private Ad ad;
	private Date dateCreated;
	private String userIpAddress;
	private String userAgent;
	private String referrer;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Ad getAd() {
		return ad;
	}
	public void setAd(Ad ad) {
		this.ad = ad;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getUserIpAddress() {
		return userIpAddress;
	}
	public void setUserIpAddress(String userIpAddress) {
		this.userIpAddress = userIpAddress;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	
}
