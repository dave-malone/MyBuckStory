package com.mybuckstory.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuLinkTag extends BodyTagSupport{

	/**
	 * generated
	 */
	private static final long	serialVersionUID	= -171767588855749789L;
	private static final Logger LOG = LoggerFactory.getLogger(MenuLinkTag.class);
	private static final String MENU_LINK_TEMPLATE = "<li class=\"%s\"><a href=\"%s\"><em>%s</em></a></li>";
	private static final String DEFAULT_CSS_CLASS = "activelink selected";
	private String uri;
	private String title;
	private String cssClassIfSelected;
	private String otherUris;
	

	@Override
	public int doStartTag() throws JspException{
		if(StringUtils.isBlank(cssClassIfSelected)){
			cssClassIfSelected = DEFAULT_CSS_CLASS;
		}
		
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		
		//Using Tiles, the request URI gets lost due to the fact that Tiles is forwarding us to a view
		String requestUri = (String)request.getAttribute("javax.servlet.forward.request_uri");
		//If we are hitting a non-Tiles view, then the attribute above would be null
		if(requestUri == null){
			requestUri = request.getRequestURI();
		}
		
		LOG.debug("RequestURI: " + requestUri);
		LOG.debug("uri: " + uri);
		
		boolean selected = requestUri.indexOf(uri) != -1;
		
		if(!selected && StringUtils.isNotBlank(otherUris)){
			LOG.debug("checking other URIs");
			for(String otherUri : otherUris.split(",")){
				otherUri = StringUtils.trim(otherUri);
				if(otherUri.startsWith("contains:")){
					otherUri = otherUri.replaceFirst("contains:", "");
					if(requestUri.indexOf(otherUri) != -1){
						selected = true;
						break;
					}
				}else if(otherUri.startsWith("startsWith:")){
					otherUri = otherUri.replaceFirst("startsWith:", "");
					if(requestUri.startsWith(otherUri)){
						selected = true;
						break;
					}
				}else if(otherUri.startsWith("equals:")){
					otherUri = otherUri.replaceFirst("equals:", "");
					if(requestUri.equalsIgnoreCase(otherUri)){
						selected = true;
						break;
					}
				}
			}
		}
		
		LOG.debug("selected: " + selected);
		
		String cssClass = selected ? cssClassIfSelected : "";		
		
		String contextRoot = request.getContextPath();
		
		if(!uri.startsWith(contextRoot)){
			uri = contextRoot + uri;
		}
		
		String menuLink = String.format(MENU_LINK_TEMPLATE, cssClass, uri, title);
		
		try{
			pageContext.getOut().write(menuLink);
		}catch(IOException e){
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}

	public void setUri(String uri){
		this.uri = uri;
	}


	public void setTitle(String title){
		this.title = title;
	}


	public void setCssClassIfSelected(String cssClassIfSelected){
		this.cssClassIfSelected = cssClassIfSelected;
	}


	public void setOtherUris(String otherUris) {
		this.otherUris = otherUris;
	}
	
}
