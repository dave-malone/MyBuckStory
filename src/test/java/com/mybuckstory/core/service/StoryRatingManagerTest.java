package com.mybuckstory.core.service;

import com.mybuckstory.model.Story;
import com.mybuckstory.model.StoryRating;
import com.mybuckstory.model.StoryRatingPK;
import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;

public class StoryRatingManagerTest extends AbstractSecureContextTest{

	protected StoryService storyService;
	protected StoryRatingService storyRatingService;
	
	public void testCreate(){
		Story story = (Story) storyService.findAll().iterator().next();
		User user = UserUtil.getCurrentUser();
		StoryRating rating = new StoryRating();
		StoryRatingPK pk = new StoryRatingPK();
		pk.setStoryId(story.getId());
		pk.setVoterId(user.getId());
		rating.setPK(pk);
		rating.setValue(3);
		
		storyRatingService.create(rating);
	}
	
}
