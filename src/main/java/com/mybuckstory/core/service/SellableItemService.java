package com.mybuckstory.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.SellableItem;

@Service
@Transactional
public class SellableItemService extends SingleImageContainerService<SellableItem> /*implements ApplicationListener<PaymentEvent>*/ {
	
	private UserService userService;
	
	public SellableItemService(){
		this(null);
	}
	
	@Autowired
	public SellableItemService(@Qualifier("sellableItemDao") GenericHibernateDao<SellableItem, Long> dao){
		super(SellableItem.class, dao, 100, 100);
	}	
	
}
