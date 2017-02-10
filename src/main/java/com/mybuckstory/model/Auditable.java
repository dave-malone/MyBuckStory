package com.mybuckstory.model;

import java.util.Date;

public interface Auditable extends Identifiable {

	public User getLastUpdatedBy();
	public void setLastUpdatedBy(User User);
	public User getCreatedBy();
	public void setCreatedBy(User User);
	public Integer getVersion();
	public void setVersion(Integer version);
	public Date getDateCreated();
	public void setDateCreated(Date creationDate);
	public Date getLastUpdated();
	public void setLastUpdated(Date lastUpdated);
	
}
