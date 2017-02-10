package com.mybuckstory.web.spring;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mybuckstory.core.service.AdService;
import com.mybuckstory.core.service.AffiliateService;
import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.core.service.EventService;
import com.mybuckstory.core.service.FooterLinkService;
import com.mybuckstory.core.service.FriendRequestService;
import com.mybuckstory.core.service.ImageService;
import com.mybuckstory.core.service.MemberFeedService;
import com.mybuckstory.core.service.MessageService;
import com.mybuckstory.core.service.NewsArticleService;
import com.mybuckstory.core.service.RoleService;
import com.mybuckstory.core.service.StoryRatingService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.core.service.StoryVoteService;
import com.mybuckstory.core.service.UserService;
import com.mybuckstory.core.service.WallPostService;

public class SpringHelper {

	private static ApplicationContext ctx;
	private static SpringHelper instance;
	
	private SpringHelper(ServletContext context){
		ctx = WebApplicationContextUtils.getWebApplicationContext(context);
	}	
	
	
	public static SpringHelper getInstance(ServletContext servletContext){
		if(instance == null){			
			instance = new SpringHelper(servletContext);
		}
		
		return instance;
	}
	
	public <T> T getBean(Class<T> beanType){
		return ctx.getBean(beanType);
	}
	
		
	public RoleService getRoleManager(){
		return getBean(RoleService.class);
	}

	public UserService getUserManager() {
		return getBean(UserService.class);
	}
	
	public AffiliateService getAffiliateManager() {
		return getBean(AffiliateService.class);
	}
	
	public AdService getAdManager() {
		return getBean(AdService.class);
	}
	
	public EventService getEventManager(){
		return getBean(EventService.class);
	}
	
	public StoryService getStoryManager(){
		return getBean(StoryService.class);
	}
	
	public FooterLinkService getFooterLinkManager(){
		return getBean(FooterLinkService.class);
	}
	
	public NewsArticleService getNewsArticleManager(){
		return getBean(NewsArticleService.class);
	}


	public FriendRequestService getFriendRequestManager() {
		return getBean(FriendRequestService.class);
	}


	public ImageService getImageManager() {
		return getBean(ImageService.class);
	}


	public MemberFeedService getMemberFeedManager() {
		return getBean(MemberFeedService.class);
	}


	public MessageService getMessageManager(){
		return getBean(MessageService.class);
	}


	public StoryRatingService getStoryRatingManager(){
		return getBean(StoryRatingService.class);
	}


	public StoryVoteService getStoryVoteManager(){
		return getBean(StoryVoteService.class);
	}
	
	public CategoryService getCategoryManager(){
		return getBean(CategoryService.class);	
	}


	public WallPostService getWallPostManager() {
		return getBean(WallPostService.class);
	}
	
}
