package com.mybuckstory.model;

import java.util.Date;

public abstract class AbstractAuditable implements Auditable {

	protected User createdBy;
	protected User lastUpdatedBy;
	protected Date dateCreated = new Date();	
	protected Date lastUpdated = new Date();
	protected Integer version;
	protected Long id;
	
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(User lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int compareTo(Object obj) {
		if(!(obj instanceof AbstractAuditable)){
			return -1;
		}
		
		AbstractAuditable auditable = (AbstractAuditable)obj;
		
		if(auditable.dateCreated != null && this.dateCreated != null){
			return this.dateCreated.compareTo(auditable.dateCreated);
		}else if(auditable.dateCreated == null && this.dateCreated != null){
			return 1;
		}else if(auditable.dateCreated != null && this.dateCreated == null){
			return -1;
		}
		
		return 0;
	}

}
