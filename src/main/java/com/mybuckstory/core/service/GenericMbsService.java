package com.mybuckstory.core.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;

@Transactional
public abstract class GenericMbsService<T> implements ApplicationContextAware{
	
	protected final Logger logger = Logger.getLogger(getClass());

	protected final Class<T> type;
	protected final GenericHibernateDao<T, Long> hibernateDao;

	private ApplicationContext applicationContext;
		
	
	protected GenericMbsService(Class<T> type, GenericHibernateDao<T, Long> hibernateDao) {
		this.type = type;
		this.hibernateDao = hibernateDao;
	}
	
	public Long create(T o) {
		return hibernateDao.save(o);
	}

	public void delete(T o) {
		hibernateDao.delete(o);
	}

	public List<T> executeQuery(String query) {
		return hibernateDao.executeQuery(query);
	}

	public void executeUpdate(String query) {
		hibernateDao.executeUpdate(query);
	}

	@Transactional(readOnly = true)
	public List<T> findAll(final String orderByField, final boolean ascending){
		return hibernateDao.getCurrentSession().createCriteria(type)
		.addOrder(ascending ? Order.asc(orderByField) : Order.desc(orderByField))
		.list();
	}
	
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return hibernateDao.findAll();
	}

	/** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
	@Transactional(readOnly = true)
	public T getById(Long id) {
		return hibernateDao.getById(id);
	}

	/** Retrieves a proxy of an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
	@Transactional(readOnly = true)
	public T loadById(Long id) {
		return hibernateDao.loadById(id);
	}

	public void refresh(T object) {
		hibernateDao.refresh(object);
	}
	
	public void flush(){
		hibernateDao.flush();
	}
	
	public void evict(T object){
		hibernateDao.evict(object);
	}

	public void saveOrUpdate(T object) {
		hibernateDao.saveOrUpdate(object);
	}

	public void update(T o) {
		hibernateDao.update(o);
	}	
	
	@Transactional(readOnly = true)
	public List<T> findAllPaginated(Integer start, Integer max) {
		Session session = hibernateDao.getCurrentSession();
		
		
		@SuppressWarnings("unchecked")
		List<T> pagedResults= (List<T>)session.createCriteria(type)
			.setFirstResult(start != null ? start : 0)
			.setMaxResults(max != null ? max : 10)
			.addOrder(Order.desc("id"))
			.list();
		
		return pagedResults;
			
	}
	
	@Transactional(readOnly = true)
	public Long getCount(){
		return hibernateDao.getCount();
	}	

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
	
	public Session getCurrentSession(){
		return hibernateDao.getCurrentSession();
	}
	
	public SessionFactory getSessionFactory(){
		return hibernateDao.getSessionFactory();
	}

	public Session openSession() {
		return hibernateDao.openSession();
	}
	
	

}