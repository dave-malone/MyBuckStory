package com.mybuckstory.web.tag;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mybuckstory.core.service.AdService;
import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.core.service.EventService;
import com.mybuckstory.core.service.FooterLinkService;
import com.mybuckstory.core.service.FriendRequestService;
import com.mybuckstory.core.service.GenericMbsService;
import com.mybuckstory.core.service.ImageService;
import com.mybuckstory.core.service.MemberFeedService;
import com.mybuckstory.core.service.MessageService;
import com.mybuckstory.core.service.NewsArticleService;
import com.mybuckstory.core.service.RoleService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.core.service.UserService;
import com.mybuckstory.core.service.WallPostService;
import com.mybuckstory.model.Affiliate;
import com.mybuckstory.web.common.WebConstants;

public abstract class MbsTagSupport extends SpringTagSupport {

	protected final Logger logger = Logger.getLogger(getClass());	
	protected static final String HOME_PAGE_PATH = "/";	
	protected String scope;
	
	protected void setAttribute(String scope, String name, Object object){
		if(StringUtils.isNotBlank(scope)){
			if(scope.equalsIgnoreCase(WebConstants.PAGE_SCOPE)){
				this.pageContext.setAttribute(name, object);
			}else if(scope.equalsIgnoreCase(WebConstants.REQUEST_SCOPE)){
				this.pageContext.getRequest().setAttribute(name, object);
			}else if(scope.equalsIgnoreCase(WebConstants.SESSION_SCOPE)){
				this.pageContext.getSession().setAttribute(name, object);
			}else if(scope.equalsIgnoreCase(WebConstants.APPLICATION_SCOPE)){
				this.pageContext.getServletContext().setAttribute(name, object);
			}else{
				throw new IllegalArgumentException("An invalid scope was specified");
			}
		}else{
			pageContext.setAttribute(name, object);
		}
	}
	
	protected void setAttribute(String name, Object object){
		setAttribute(this.scope, name, object);
	}
	
	protected String adjustPoorlyBuiltParamValue(String paramValue){
		if(StringUtils.isNotBlank(paramValue) && paramValue.indexOf("=") != -1){
			return paramValue.substring(paramValue.lastIndexOf("=") + 1);			
		}else{
			return paramValue;
		}
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
		
	protected RoleService getRoleManager(){
		return getWebAppCtx().getRoleManager();
	}

	protected UserService getUserManager() {
		return getWebAppCtx().getUserManager();
	}
	
	protected GenericMbsService<Affiliate> getAffiliateManager() {
		return getWebAppCtx().getAffiliateManager();
	}
	
	protected AdService getAdManager() {
		return getWebAppCtx().getAdManager();
	}
	
	protected EventService getEventManager(){
		return getWebAppCtx().getEventManager();
	}
	
	protected StoryService getStoryManager(){
		return getWebAppCtx().getStoryManager();
	}
	
	protected FooterLinkService getFooterLinkManager(){
		return getWebAppCtx().getFooterLinkManager();
	}
	
	protected MessageService getMessageManager(){
		return getWebAppCtx().getMessageManager();
	}
	
	protected MemberFeedService getMemberFeedManager(){
		return getWebAppCtx().getMemberFeedManager();
	}
	
	protected NewsArticleService getNewsArticleManager(){
		return getWebAppCtx().getNewsArticleManager();
	}
	
	protected FriendRequestService getFriendRequestManager(){
		return getWebAppCtx().getFriendRequestManager();
	}
	
	protected ImageService getImageManager(){
		return getWebAppCtx().getImageManager();
	}
	
	protected CategoryService getCategoryManager(){
		return getWebAppCtx().getCategoryManager();
	}
	
	protected WallPostService getWallPostManager(){
		return getWebAppCtx().getWallPostManager();
	}

	protected HttpServletRequest getRequest() {		
		return (HttpServletRequest)pageContext.getRequest();
	}
}
