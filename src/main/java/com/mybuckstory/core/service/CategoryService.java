package com.mybuckstory.core.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Category;
import com.mybuckstory.util.AlphanumComparator;

@Service
@Transactional
public class CategoryService extends GenericMbsService<Category> {
	
	public CategoryService(){
		this(null);
	}
	
	@Autowired
	public CategoryService(@Qualifier("categoryDao") GenericHibernateDao<Category, Long> dao){
		super(Category.class, dao);
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getParentCategories(){
		List<Category> categories = (List<Category>)getCurrentSession().createCriteria(Category.class)
			.add(Restrictions.isNull("parent"))
			.addOrder(Order.asc("name"))
			.list();
		
		
		Collections.sort(categories, new AlphanumComparator());
		
		return categories;
	}
	
	public Category createOrFind(String name){
		Category category = findByName(name);
		if(category == null){
			category = new Category(name);
		}
		
		return category;
	}
	
	@Transactional(readOnly=true)
	public Category findByName(final String name){
		return (Category)getCurrentSession().createCriteria(Category.class)
			.add(Restrictions.eq("name", name))
			.setCacheable(true)
			.uniqueResult();
	}

}
