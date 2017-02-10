package com.mybuckstory.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.ContestPrize;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.Profile;
import com.mybuckstory.model.Story;

@Service
@Transactional
public class ContestPrizeService extends GenericMbsService<ContestPrize>{
	
	private final ApplicationEventService applicationEventService;
	private final ProfileService profileService;
	
	public ContestPrizeService(){
		this(null, null, null);
	}
	
	@Autowired
	public ContestPrizeService(ApplicationEventService eventService, ProfileService profileService, @Qualifier("contestPrizeDao") GenericHibernateDao<ContestPrize, Long> dao){
		super(ContestPrize.class, dao);
		this.applicationEventService = eventService;
		this.profileService = profileService;
	}
	
	@Override
	public Long create(ContestPrize prize) {
		Long id = super.create(prize);
		checkForAward(prize);
		return id;		
	}

	@Override
	public void saveOrUpdate(ContestPrize prize) {
		super.saveOrUpdate(prize);
		checkForAward(prize);
	}

	@Override
	public void update(ContestPrize prize) {
		super.update(prize);
		checkForAward(prize);
	}
	
	private void checkForAward(ContestPrize prize){
		if(prize.getWinningStory() != null){
			award(prize, prize.getWinningStory());
		}else if(prize.getWinningImage() != null){
			award(prize, prize.getWinningImage());
		}
	}	
	
	public void award(ContestPrize contestPrize, Story story) {
		// assign winning story to prize & vice-versa
		contestPrize.setWinningStory(story);
		story.getPrizes().add(contestPrize);

		// assign prize and badge to user profile
		Profile profile = story.getCreatedBy().getProfile();		
		profileService.award(profile.getId(), contestPrize.getBadge());
		profileService.award(profile.getId(), contestPrize);		

		//persist updates
		getCurrentSession().update(contestPrize);
		getCurrentSession().update(story);
	}

	//TODO - this currently isn't being used
	public void award(ContestPrize prize, Image image) {
		// assign winning story to prize & vice-versa
		prize.setWinningImage(image);
		image.getPrizes().add(prize);

		// assign prize and badge to user profile
		Profile profile = image.getCreatedBy().getProfile();
		profile.getContestPrizes().add(prize);
		profile.getBadges().add(prize.getBadge());

		//persist updates
		getCurrentSession().update(prize);
		getCurrentSession().update(image);
		getCurrentSession().update(profile);

		// TODO - publish prize & badge assignment to member feed
	}
	
}
