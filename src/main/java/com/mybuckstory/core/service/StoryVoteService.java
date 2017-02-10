package com.mybuckstory.core.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.StoryVote;
import com.mybuckstory.model.StoryVotePK;

@Service
@Transactional
public class StoryVoteService  {

	private final GenericHibernateDao<StoryVote, StoryVotePK> hibernateDao;
	private final StoryService storyService;
	
	public StoryVoteService(){
		this(null, null);
	}
	
	@Autowired
	public StoryVoteService(@Qualifier("storyVoteDao") GenericHibernateDao<StoryVote, StoryVotePK> hibernateDao, StoryService storyService){
		this.hibernateDao = hibernateDao;
		this.storyService = storyService;
	}

	/**
	 * 
	 * @param vote
	 * @return the average rating for the story
	 */
	public void create(StoryVote vote){
		if(vote.getPk().getStoryId() == null){
			throw new IllegalArgumentException("storyId is not set on rating pk");
		}
		
		if(vote.getPk().getVoterId() == null){
			throw new IllegalArgumentException("voterId is not set on rating pk");
		}				
				
		hibernateDao.saveOrUpdate(vote);
		hibernateDao.flush();
		updateStoryVoteCount(vote);
	}
	
	private void updateStoryVoteCount(final StoryVote vote){
		BigInteger voteCount = (BigInteger)hibernateDao.getCurrentSession().createSQLQuery("select count(*) from STORY_VOTE where STORY_ID=:storyId")
				.setParameter("storyId", vote.getPk().getStoryId())
				.uniqueResult();
		
		Story story = storyService.getById(vote.getPk().getStoryId());
		story.setVoteCount(voteCount.intValue());
		storyService.update(story);
	}

}
