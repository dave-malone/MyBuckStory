package com.mybuckstory.core.service;

import java.util.Set;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Badge;
import com.mybuckstory.model.ContestPrize;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.Profile;

@Service
@Transactional
public class ProfileService extends SingleImageContainerService<Profile>{

	private final ApplicationEventService appEventService;
	
	public ProfileService(){
		this(null, null, 0, 0);
	}
	
	
	@Autowired
	public ProfileService(ApplicationEventService appEventService, @Qualifier("profileDao") GenericHibernateDao<Profile, Long> dao, @Value("${profile.width}") int thumbWidth, @Value("${profile.height}") int thumbHeight){
		super(Profile.class, dao, thumbWidth, thumbHeight);
		this.appEventService = appEventService;
	}
	
	public void award(Long profileId, Badge badge) {
		Profile profile = getById(profileId);
		if(!profile.getBadges().contains(badge)){
			profile.getBadges().add(badge);
			update(profile);
			appEventService.publish(new MemberFeedEvent(this, new MemberFeedItem(badge, profile)));
		}
	}
	
	public void award(Long profileId, ContestPrize prize) {
		Profile profile = getById(profileId);
		if(!profile.getContestPrizes().contains(prize)){
			profile.getContestPrizes().add(prize);
			update(profile);
			appEventService.publish(new MemberFeedEvent(this, new MemberFeedItem(prize, profile)));
		}
	}


	public Set<Badge> getBadges(Long id) {
		Profile profile= (Profile) getCurrentSession().createCriteria(Profile.class)
			.add(Restrictions.idEq(id))
			.setFetchMode("badges", FetchMode.JOIN)
			.uniqueResult();
		return profile.getBadges();
	}


	public Set<ContestPrize> getContestPrizes(Long id) {
		Profile profile= (Profile) getCurrentSession().createCriteria(Profile.class)
			.add(Restrictions.idEq(id))
			.setFetchMode("contestPrizes", FetchMode.JOIN)
			.uniqueResult();
		return profile.getContestPrizes();
	}


	public void changeProfilePicture(Profile profile) {
		boolean success = false;
		try{
			super.update(profile);
			success = true;
		}catch(Exception e){
			logger.error("Failed to change profile picture: " + e.getMessage());
		}
		
		if(success){
			try {
				MemberFeedItem feedItem = new MemberFeedItem();
				feedItem.setProfile(profile);
				feedItem.setImage(profile.getImage());
				feedItem.setType(MemberFeedItem.Type.PROFILE_IMAGE);
				
				appEventService.publish(new MemberFeedEvent(this, feedItem));
			} catch (Exception e) {
				logger.error("Failed to create member feed item for profile image update event: " + e.getMessage());
			}
		}
	}
	
}
