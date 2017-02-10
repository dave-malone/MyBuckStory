package com.mybuckstory.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

public class SortableColumnTag extends MbsTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6980166389211203032L;
	private static final String ASC = "asc";
	private static final String DESC = "desc";
	private static final String DEFAULT_SORT_BY = DESC;
	
	private static final String DEFAULT_CSS_CLASS = "sortable";
	private static final String ASC_SORTED_CSS_CLASS = "sorted asc";
	private static final String DESC_SORTED_CSS_CLASS = "sorted desc";
	
	private String property;
	private String name;	
	private String params;
	
	/**
	 * The goal is to build a table column header like this:
	 * <code>&lt;th class="sortable sorted asc"&gt;&lt;a href="?sort=name&amp;order=desc"&gt;Name&lt;/a&gt;&lt;/th&gt;</code> 
	 */
	@Override
	public int doStartTag() throws JspException {
		logger.debug("Sort by: " + property);	
		
		StringBuffer sortableColumnHtml = new StringBuffer();
		
		sortableColumnHtml.append("<th class=\"").append(DEFAULT_CSS_CLASS);
		
		String orderBy = pageContext.getRequest().getParameter("order");
		String sortBy = pageContext.getRequest().getParameter("sort");
		
		if(StringUtils.equalsIgnoreCase(property, sortBy)){
			if(StringUtils.equalsIgnoreCase(ASC, orderBy)){				
				sortableColumnHtml.append(" ").append(ASC_SORTED_CSS_CLASS).append("\">");
				orderBy = DESC;
			}else if(StringUtils.equalsIgnoreCase(DESC, orderBy)){
				sortableColumnHtml.append(" ").append(DESC_SORTED_CSS_CLASS).append("\">");
				orderBy = ASC;
			}
		}else{
			orderBy = DEFAULT_SORT_BY;
			sortableColumnHtml.append("\">");
		}
		
		logger.debug("Order by: " + orderBy);
		
		sortableColumnHtml.append("<a href=\"")
			.append("?sort=").append(property)
			.append("&order=").append(orderBy);		
		appendOtherParams(sortableColumnHtml);		
		sortableColumnHtml.append("\"");
		
		sortableColumnHtml.append(">").append(name).append("</a>");
		sortableColumnHtml.append("</th>");		
		
		try {
            this.pageContext.getOut().write(sortableColumnHtml.toString());
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);            
        }		
		
		return EVAL_BODY_BUFFERED;
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

	public void setProperty(String property) {
		this.property = property;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParams(String params) {
		this.params = params;
	}	
	
}
