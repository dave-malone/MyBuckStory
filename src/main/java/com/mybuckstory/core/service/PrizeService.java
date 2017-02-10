package com.mybuckstory.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Prize;

@Service
@Transactional
public class PrizeService extends SingleImageContainerService<Prize>{
	
	public PrizeService(){
		this(null, null, null);
	}
	
	@Autowired
	public PrizeService(@Qualifier("prizeDao") GenericHibernateDao<Prize, Long> dao, @Value("${prize.width}") Integer thumbWidth, @Value("${prize.height}") Integer thumbHeight){
		super(Prize.class, dao, thumbWidth, thumbHeight);
	}	
	
}
