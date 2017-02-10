package com.mybuckstory.web.dwr;

import org.apache.log4j.Logger;

import com.mybuckstory.core.service.FriendRequestService;
import com.mybuckstory.core.service.ImageService;
import com.mybuckstory.core.service.MessageService;
import com.mybuckstory.core.service.StoryRatingService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.core.service.StoryVoteService;
import com.mybuckstory.core.service.UserService;
import com.mybuckstory.core.service.WallPostService;
import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;


public abstract class AbstractMbsClient extends AbstractSpringDwrClient {
	
	protected final Logger logger = Logger.getLogger(getClass());	
	
	protected StoryService getStoryManager(){
		return getWebAppCtx().getStoryManager();
	}
	
	protected StoryRatingService getStoryRatingManager(){
		return getWebAppCtx().getStoryRatingManager();
	}
	
	protected StoryVoteService getStoryVoteManager(){
		return getWebAppCtx().getStoryVoteManager();
	}
	
	protected FriendRequestService getFriendRequestManager(){
		return getWebAppCtx().getFriendRequestManager();
	}
	
	protected MessageService getMessageManager(){
		return getWebAppCtx().getMessageManager();
	}
	
	protected WallPostService getWallPostManager(){
		return getWebAppCtx().getWallPostManager();
	}
	
	protected UserService getUserManager(){
		return getWebAppCtx().getUserManager();
	}
	
	protected User getCurrentUser(){
		return UserUtil.getCurrentUser();
	}
	
	protected ImageService getImageManager(){
		return getWebAppCtx().getImageManager();
	}
	
}
