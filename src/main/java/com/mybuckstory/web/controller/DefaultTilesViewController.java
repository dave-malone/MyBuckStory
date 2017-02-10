package com.mybuckstory.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultTilesViewController {

	private static final Logger logger = Logger.getLogger(DefaultTilesViewController.class);
	
	private static final String DEFAULT_VIEW = "index";
	
	@RequestMapping("/**/*.html")
	protected String resolveTilesView(HttpServletRequest request) throws Exception {
		String popup = request.getParameter("popup");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		
		logger.debug("RequestURI: " + requestUri);
		logger.debug("Context Path: " + contextPath);
		
		String resource = requestUri;		
		
		if(StringUtils.isNotBlank(contextPath) && resource.startsWith(contextPath)){
			resource = resource.replaceFirst(contextPath, "");
		}
		
		if(resource.indexOf(".") != -1){			
			resource = resource.substring(0, resource.indexOf("."));			
		}
		
		if(resource.startsWith("/") && !"/".equals(resource)){
			resource = resource.replaceFirst("/", "");
		}		
		
		if(StringUtils.isBlank(resource) || "/".equals(resource)){;
			resource = DEFAULT_VIEW;
		}
		
		if("true".equalsIgnoreCase(popup)){
			resource = "popup" + resource;
		}
		
		logger.debug("Resource: "  + resource);		
		request.setAttribute("requestedResource", resource);
		return resource;
	}

	
	
}
