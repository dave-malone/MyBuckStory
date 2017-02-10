package com.mybuckstory.core.service;

import com.mybuckstory.model.Resource;


public interface ResourceLookupService {

	public boolean uriInUse(String uri);
	public Resource findByUri(final String uri);
	
}
