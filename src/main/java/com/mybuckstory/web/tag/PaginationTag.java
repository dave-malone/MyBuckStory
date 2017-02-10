package com.mybuckstory.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

public class PaginationTag extends MbsTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7631070047084623897L;

	private String cssClass;
	private Long total;
	private Integer defaultMax;
	private String params;
	
	@Override
	public int doStartTag() throws JspException {
		StringBuffer paginationLinksAndText = new StringBuffer();
		
		if(total > 0){
			String startParam = pageContext.getRequest().getParameter("start");
			String maxParam = pageContext.getRequest().getParameter("max");
			String sortBy = pageContext.getRequest().getParameter("sort");
			String orderBy = pageContext.getRequest().getParameter("order");			
			
			
			sortBy = StringUtils.trimToEmpty(sortBy);
			orderBy = StringUtils.trimToEmpty(orderBy);
			
			int start = StringUtils.isBlank(startParam) ? 0 : Integer.valueOf(startParam);
			int max = StringUtils.isBlank(maxParam) ? (defaultMax != null ? defaultMax : 15) : Integer.valueOf(maxParam);			
			//check again just in case the params were set to invalid values
			if(max <= 0){
	    		max = 10;
	    		start = 0;
	    	}
	    	
			logger.debug("Max: " + max);		
			logger.debug("Start: " + start);		
			logger.debug("Total: " + total);
			logger.debug("Sort by: " + sortBy);
			logger.debug("Order by: " + orderBy);			
			
			if(start > 0){		    
				paginationLinksAndText.append(getPrevLink(start, max, sortBy, orderBy));
			}	
		
			if(start + max < total){	
				paginationLinksAndText.append("  ")
				  .append(start + 1)
				  .append(" - ")
				  .append(start + max)
				  .append(" of ")
				  .append(total)
				  .append("  ");
				
				paginationLinksAndText.append(getNextLink(start, max, sortBy, orderBy));
			}else{
				paginationLinksAndText.append("  ")
				  .append(start + 1)
				  .append(" - ")
				  .append(total)
				  .append(" of ")
				  .append(total)
				  .append("  ");
			}
		}
		
		try {
            this.pageContext.getOut().write(paginationLinksAndText.toString());
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);            
        }
		
		
		return EVAL_BODY_BUFFERED;
	}
	
	private String getPrevLink(int start, int max, String sortBy, String orderBy){
		final String previousTitle = "&lt; Previous " + max;
		
		StringBuffer href = new StringBuffer();
		href.append("?max=").append(max);
		href.append("&start=").append(start - max);
		href.append("&sort=").append(sortBy);
		href.append("&order=").append(orderBy);
		appendOtherParams(href);
		
		return getLink(href.toString(), previousTitle);
	}
	
	private String getNextLink(int start, int max, String sortBy, String orderBy){
		final String nextTitle = "Next " + max + "&gt;"; 
		
		StringBuffer href = new StringBuffer();
		href.append("?max=").append(max);
		href.append("&start=").append(start + max);
		href.append("&sort=").append(sortBy);
		href.append("&order=").append(orderBy);
		appendOtherParams(href);
		
		return getLink(href.toString(), nextTitle);
	}
	
	private void appendOtherParams(StringBuffer href){
		if(StringUtils.isNotBlank(params)){
			String[] paramNames = params.split("[,]");
			for(String paramName : paramNames){
				paramName = StringUtils.trimToEmpty(paramName);
				String paramValue = StringUtils.trimToEmpty(pageContext.getRequest().getParameter(paramName));
				href.append("&").append(paramName).append("=").append(paramValue);
			}
		}
	}
	
	private String getLink(String href, String text){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<a href=\"").append(href).append("\"");
		
		if(StringUtils.isNotBlank(this.cssClass)){
    		buffer.append(" class=\"" + this.cssClass + "\"");
    	}
	  	
		buffer.append(">").append(text).append("</a>");
		
		return buffer.toString();
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setDefaultMax(Integer defaultMax) {
		this.defaultMax = defaultMax;
	}	
	
}
