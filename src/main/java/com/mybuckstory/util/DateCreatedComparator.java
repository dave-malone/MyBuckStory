package com.mybuckstory.util;

import java.util.Comparator;

import com.mybuckstory.model.Auditable;


public class DateCreatedComparator implements Comparator<Auditable>{

	public int compare(Auditable obj1, Auditable obj2){
		if(obj1.getDateCreated() == null && obj2.getDateCreated() == null){
			return 0;
		}
		
		if(obj1.getDateCreated() != null && obj2.getDateCreated() == null){
			return 1;
		}
		
		if(obj1.getDateCreated() == null && obj2.getDateCreated() != null){
			return -1;
		}
		
		return obj1.getDateCreated().compareTo(obj2.getDateCreated());
	}

}
