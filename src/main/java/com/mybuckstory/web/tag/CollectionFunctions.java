package com.mybuckstory.web.tag;

import java.util.Collection;

import org.apache.log4j.Logger;



public class CollectionFunctions {

	protected static final Logger logger = Logger.getLogger(CollectionFunctions.class);	
	
	public static boolean contains(Collection collection, Object element){
		if(collection == null){
			logger.info("collection was null");
			return false;
		}
		if(element == null){
			logger.info("element was null");
			return false;
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Testing to see if collection (" + collection + ") contains element: " + element);
		}
		
		return collection.contains(element);	
	}
	
}
