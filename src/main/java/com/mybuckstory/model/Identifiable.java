package com.mybuckstory.model;

import java.io.Serializable;

public interface Identifiable extends Serializable, Comparable{

	public Long getId();
	public void setId(Long id);
	
}
