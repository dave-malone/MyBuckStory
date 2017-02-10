package com.mybuckstory.core.service;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.FooterLink;

@Service
@Transactional
public class FooterLinkService extends GenericMbsService<FooterLink> {

	public FooterLinkService(){
		this(null);
	}
	
	@Autowired
	public FooterLinkService(@Qualifier("footerLinkDao") GenericHibernateDao<FooterLink, Long> dao) {
		super(FooterLink.class, dao);
	}
	
	@Transactional(readOnly=true)
	public List<FooterLink> findByCategory(final String category){
		return (List<FooterLink>)getCurrentSession().createCriteria(FooterLink.class)
			.add(Restrictions.eq("category", category))
			.setCacheable(true)
			.list();
	}

}
