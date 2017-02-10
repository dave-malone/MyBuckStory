package com.mybuckstory.core.service;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.StoryRating;
import com.mybuckstory.model.StoryRatingPK;

@Service
@Transactional
public class StoryRatingService  {

	private final GenericHibernateDao<StoryRating, StoryRatingPK> hibernateDao;
	private final StoryService storyService;
	
	public StoryRatingService(){
		this(null, null);
	}
	
	@Autowired
	public StoryRatingService(@Qualifier("storyRatingDao") GenericHibernateDao<StoryRating, StoryRatingPK> hibernateDao, StoryService storyService){
		this.hibernateDao = hibernateDao;
		this.storyService = storyService;
	}

	/**
	 * 
	 * @param rating
	 * @return the average rating for the story
	 */
	public double create(StoryRating rating){
		if(rating.getPK().getStoryId() == null){
			throw new IllegalArgumentException("storyId is not set on rating pk");
		}
		
		if(rating.getPK().getVoterId() == null){
			throw new IllegalArgumentException("voterId is not set on rating pk");
		}				
				
		hibernateDao.saveOrUpdate(rating);
		return calculateAverageRating(rating.getPK().getStoryId());
	}
	
	private synchronized double calculateAverageRating(final Long storyId){
		final Story story = (Story) storyService.getById(storyId);
		
		List<Integer> ratings = (List<Integer>) hibernateDao.getCurrentSession().createCriteria(StoryRating.class)
											.add(Restrictions.eq("PK.storyId", storyId))
											.setProjection(Projections.property("value"))
											.list();
		
		int ratingCount = ratings.size();		
		double total = 0.0;
		
		for(Integer rating : ratings){
			total += rating;
		}
		
		double averageRating = total/ratingCount;
		
		story.setAverageRating(averageRating);
		storyService.update(story);
		
		return averageRating;
	}	

}
