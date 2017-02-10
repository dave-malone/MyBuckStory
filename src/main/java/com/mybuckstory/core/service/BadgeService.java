package com.mybuckstory.core.service;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.BadgeAwardingEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Badge;
import com.mybuckstory.model.Category;
import com.mybuckstory.model.Profile;
import com.mybuckstory.model.Story;

@Service
@Transactional
public class BadgeService extends SingleImageContainerService<Badge> implements ApplicationListener<BadgeAwardingEvent>{
	
	private final ProfileService profileService;
	private final StoryService storyService;
	
	public BadgeService(){
		this(null, null, null, null, null);
	}
	
	@Autowired
	public BadgeService(ProfileService profileService, StoryService storyService, @Qualifier("badgeDao") GenericHibernateDao<Badge, Long> dao, @Value("${badge.width}") Integer thumbWidth, @Value("${badge.height}") Integer thumbHeight){
		super(Badge.class, dao, thumbWidth, thumbHeight);
		this.profileService = profileService;
		this.storyService = storyService;		
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void onApplicationEvent(BadgeAwardingEvent event) {
		awardStoryBadges(event);
	}

	public void awardStoryBadges(BadgeAwardingEvent event) {
		Story story = event.getStory();
		
		for(Category category : story.getCategories()){
			awardStoryCategoryBadges(story, category);
		}
	}

	public void awardStoryCategoryBadges(Story story, Category category) {
		Long numberOfStoriesPublishedInCategory = storyService.getStoriesPublishedByAuthorInCategoryCount(category.getName(), story.getCreatedBy());
		List<Badge> badgesForStoryCategory = getBadgesForStoryCategory(category);
		for(Badge badge : badgesForStoryCategory){
			if(badge.getNumberOfStoriesInCategory() <= numberOfStoriesPublishedInCategory){
				Profile profile = story.getCreatedBy().getProfile();
				profileService.award(profile.getId(), badge);
			}
		}
	}	
	
	@SuppressWarnings("unchecked")
	public List<Badge> getBadgesForStoryCategory(Category storyCategory){
		return (List<Badge>)getCurrentSession().createCriteria(Badge.class)
			.add(Restrictions.eq("storyCategory", storyCategory))
			.list();
	}
	
}
