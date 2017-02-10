package com.mybuckstory.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Affiliate;
import com.mybuckstory.model.MemberFeedItem;

@Service
@Transactional
public class AffiliateService extends SingleImageContainerService<Affiliate> {
	
	private final ApplicationEventService eventService;
	
	public AffiliateService(){
		this(null, null, 0, 0);
	}
	
	
	@Autowired
	public AffiliateService(ApplicationEventService eventService, @Qualifier("affiliateDao") GenericHibernateDao<Affiliate, Long> dao, @Value("${affiliate.width}") int thumbWidth, @Value("${affiliate.height}") int thumbHeight){
		super(Affiliate.class, dao, thumbWidth, thumbHeight);
		this.eventService = eventService;
	}

	@Override
	public Long create(Affiliate affiliate) {
		Long id = super.create(affiliate);		
		eventService.publish(new MemberFeedEvent(this, new MemberFeedItem(affiliate)));		
		return id;
	}	
	
}