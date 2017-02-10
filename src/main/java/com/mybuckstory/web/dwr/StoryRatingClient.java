package com.mybuckstory.web.dwr;

import org.hibernate.NonUniqueObjectException;

import com.mybuckstory.model.StoryRating;
import com.mybuckstory.model.StoryRatingPK;
import com.mybuckstory.model.StoryVote;
import com.mybuckstory.model.StoryVotePK;
import com.mybuckstory.model.User;


public class StoryRatingClient extends AbstractMbsClient{
	
	public Double rateStory(Integer value, Long storyId){
		logger.debug("rating story");
		logger.debug("Story id: " + storyId);
		logger.debug("value: " + value);
		
		User user = getCurrentUser();
		StoryRating rating = new StoryRating();
		StoryRatingPK pk = new StoryRatingPK();
		pk.setStoryId(storyId);
		pk.setVoterId(user.getId());
		rating.setPK(pk);
		rating.setValue(value);
		
		try{
			return new Double(getStoryRatingManager().create(rating));
		}catch(Exception e){
			logger.error("failed to rate story", e);
		}
		
		return null;	
	}
	
	public String voteStory(Long storyId){		
		logger.debug("voting on Story id: " + storyId);		
		User user = getCurrentUser();
		logger.debug("user voting: " + user);		
		
		try{			
			StoryVotePK pk = new StoryVotePK(storyId, user.getId());
			StoryVote vote = new StoryVote(pk);
			getStoryVoteManager().create(vote);
			
			return "Your vote has been submitted";
		}catch(Exception e){
			if(e instanceof NonUniqueObjectException || e.getCause() instanceof NonUniqueObjectException){
				return "You have already voted on this story.  You may only vote once per story";
			}
			logger.error("failed to process story vote", e);
			return "Failed to process story vote.  Please try again later.  If the problem persists, please contact the site admin.";
		}
	}
	
	

}
