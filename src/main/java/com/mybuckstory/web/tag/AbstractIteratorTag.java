package com.mybuckstory.web.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractIteratorTag<T> extends MbsTagSupport {

	protected static final String DEFAULT_PAGINATION_INFO_ID = "paginationInfo";
	protected Iterator<T> iter;

	
	@Override
	public void release() {		
		iter = null;
		super.release();
	}
	
	/**
	 * This method must be implemented in order to use this tag.  This method
	 * is responsible for initializing the Iterator object, where you may optionally
	 * use the start and max fields for pagination support.
	 * @throws JspException 
	 * @return Iterator for the collection to iterate over
	 */
	protected abstract Iterator<T> init() throws JspException;	
	

	@Override
	public int doStartTag() throws JspException {
		iter = init();
		
		if(iter == null){
			throw new IllegalStateException("iter was null.  You must initialize iter using the init method");
		}
		
		if(iter.hasNext()){			
			if(StringUtils.isNotBlank(getScope())){
				setAttribute(getId(), iter.next());
			}else{
				pageContext.setAttribute(getId(), iter.next());
			}			
			
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}	
	
	@Override
	public int doAfterBody() throws JspException {
		if(iter.hasNext()){			
			if(StringUtils.isNotBlank(getScope())){
				setAttribute(getId(), iter.next());
			}else{
				pageContext.setAttribute(getId(), iter.next());
			}			
			
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}	
	
}