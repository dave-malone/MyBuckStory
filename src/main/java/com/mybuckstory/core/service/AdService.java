package com.mybuckstory.core.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Ad;
import com.mybuckstory.model.AdClick;

@Service
@Transactional
public class AdService extends SingleImageContainerService<Ad>{

	public AdService(){
		this(null, 0, 0);
	}
	
	@Autowired
	public AdService(@Qualifier("adDao") GenericHibernateDao<Ad, Long> dao, @Value("${ad.width}") int thumbWidth, @Value("${ad.height}") int thumbHeight){
		super(Ad.class, dao, thumbWidth, thumbHeight);
	}
	
	@Transactional(readOnly=true)
	public List<Ad> findAllEnabled(){
		Session session = getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<Ad> enabledAds = session.createCriteria(Ad.class)
			.add(Restrictions.eq("enabled", true))
			.addOrder(Order.asc("rank"))
			.setCacheable(true)
			.list();
		
		return enabledAds;		
	}

	public void save(AdClick adClick) {
		getCurrentSession().save(adClick);		
	}

	
	@Transactional(readOnly=true)
	public List<AdClick> getAdClicks(Ad ad, Integer firstResult, Integer maxResults){
		Session session = getCurrentSession();
		
		if(firstResult == null || maxResults == null){
			firstResult = 0; 
			maxResults = 10;
		}
		
		@SuppressWarnings("unchecked")
		List<AdClick> adClicks = session.createCriteria(AdClick.class)
			.add(Restrictions.eq("ad", ad))
			.addOrder(Order.desc("dateCreated"))
			.setFirstResult(firstResult)
			.setMaxResults(maxResults)
			.setCacheable(true)
			.list();
		
		return adClicks;		
	}
	
	@Transactional(readOnly=true)
	public Long getTotalAdClicks(Ad ad){
		Session session = getCurrentSession();
		
		Long totalAdClicks = (Long)session.createCriteria(AdClick.class)
			.add(Restrictions.eq("ad", ad))
			.setProjection(Projections.rowCount())
			.uniqueResult();
		
		return totalAdClicks;
	}
	
}
