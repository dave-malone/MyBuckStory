package com.mybuckstory.web.dwr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public abstract class AbstractDwrClient {

	protected static Log logger = LogFactory.getLog(AbstractDwrClient.class);
	
	protected WebContext getWebContext(){
		return WebContextFactory.get();
	}
	
	
	
}
