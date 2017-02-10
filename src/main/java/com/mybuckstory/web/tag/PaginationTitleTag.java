package com.mybuckstory.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

public class PaginationTitleTag extends MbsTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7631070047084623897L;

	private Long total;

	
	@Override
	public int doStartTag() throws JspException {
		StringBuffer titleText = new StringBuffer();
		
		if(total > 0){
			String startParam = pageContext.getRequest().getParameter("start");
			String maxParam = pageContext.getRequest().getParameter("max");

			
			int start = StringUtils.isBlank(startParam) ? 0 : Integer.valueOf(startParam);
			int max = StringUtils.isBlank(maxParam) ? 10 : Integer.valueOf(maxParam);			
			//check again just in case the params were set to invalid values
			if(max <= 0){
	    		max = 10;
	    		start = 0;
	    	}
	    	
			logger.debug("Max: " + max);		
			logger.debug("Start: " + start);		
			logger.debug("Total: " + total);			
		
			if(start + max < total){	
				titleText.append("  ")
				  .append(start + 1)
				  .append(" - ")
				  .append(start + max)
				  .append(" of ")
				  .append(total)
				  .append("  ");				
			}else{
				titleText.append("  ")
				  .append(start + 1)
				  .append(" - ")
				  .append(total)
				  .append(" of ")
				  .append(total)
				  .append("  ");
			}
		}
		
		try {
            this.pageContext.getOut().write(titleText.toString());
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);            
        }
		
		
		return EVAL_BODY_BUFFERED;
	}

	public void setTotal(Long total) {
		this.total = total;
	}	
	
}
