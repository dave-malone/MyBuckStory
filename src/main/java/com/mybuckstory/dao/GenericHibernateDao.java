package com.mybuckstory.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericHibernateDao<T, PK extends Serializable> implements BeanNameAware{
	
	public static final int DEFAULT_PAGE_START = 0;
	public static final int DEFAULT_PAGE_MAX = 10;
	
	private String beanName;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected final Class<T> entityType;
	
	public GenericHibernateDao(Class<T> entityType){
		this.entityType = entityType;
	}	
	
	public GenericHibernateDao(Class<T> entityType, SessionFactory sessionFactory){
		this.entityType = entityType;
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * uses the SessionFactory to 1st get the current session, and
	 * then to create a Criteria using the {@link #entityType} as 
	 * an argument
	 * @return
	 */
	public Criteria getBaseCriteria(){
		return getCurrentSession().createCriteria(entityType);
	}
	
	@SuppressWarnings("unchecked")
	
	public List<T> findAll(){
		return getBaseCriteria().list();		
	}
	
	
	public List<T> findAllPaginated(Integer start, Integer max){
		return findAllPaginated(start, max, null, null);
	}

	@SuppressWarnings("unchecked")
	
	public List<T> findAllPaginated(Integer start, Integer max, String sortBy, String orderBy){
		Criteria paginationCriteria = getPaginationCriteria(start, max, sortBy,	orderBy);
		
		return paginationCriteria.list();
	}

	public Criteria getPaginationCriteria(Integer start, Integer max,
			String sortBy, String orderBy) {
		if((start == null || max == null) || (start < 0 || max < 1)){
			start = DEFAULT_PAGE_START;
			max = DEFAULT_PAGE_MAX;
		}		
		
		Criteria paginationCriteria = getBaseCriteria().setFirstResult(start).setMaxResults(max);
		
		if(StringUtils.isNotBlank(sortBy) && StringUtils.isNotBlank(orderBy)){
			Order order = null;
			if(StringUtils.equalsIgnoreCase("asc", orderBy)){
				order = Order.asc(sortBy);
			}else if(StringUtils.equalsIgnoreCase("desc", orderBy)){
				order = Order.desc(sortBy);
			}else{
				throw new IllegalArgumentException("The order by value provided (" + orderBy + ") is invalid.  Valid values are 'asc' and 'desc'");
			}
			
			paginationCriteria.addOrder(order);
		}
		return paginationCriteria;
	}

	@SuppressWarnings("unchecked")	
	public PK save(T entity){
		return (PK)getCurrentSession().save(entity);
	}
	
	@SuppressWarnings("unchecked")
	public T merge(T entity){
		final Session session = getCurrentSession();
		return (T) session.merge(entity);
	}

	
	public void update(T entity){		
		final Session session = getCurrentSession();
		session.update(entity);
	}

	
	public Long getCount(){
		return (Long) getBaseCriteria().setProjection(Projections.rowCount()).uniqueResult();		
	}

	/**
	 * The difference between get and load may seem subtle, but get
	 * will return a proxy, while load will actually load an instance
	 * from the database.
	 * @see {@link org.hibernate.Session#get(Class, Serializable)}
	 * @see {@link org.hibernate.Session#load(Class, Serializable)}
	 */
	@SuppressWarnings("unchecked")	
	public T getById(PK id){
		return (T)getCurrentSession().get(entityType, id);
	}
	
	/**
	 * Per the Hibernate documentation: 
	 * &quot;You should not use this method to determine if an instance exists (use get() instead). &quot;
	 * 
	 * @see {@link org.hibernate.Session#get(Class, Serializable)}
	 * @see {@link org.hibernate.Session#load(Class, Serializable)}
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T loadById(PK id){
		return (T)getCurrentSession().load(entityType, id);
	}

	@SuppressWarnings("unchecked")	
	public List<T> queryByExample(T example){
		return getBaseCriteria().add(Example.create(example)).list();		
	}

	
	public void delete(T entity){
		getCurrentSession().delete(entity);
	}
	
	public void flush(){
		getCurrentSession().flush();
	}

	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public Session openSession(){
		return sessionFactory.openSession();
	}	
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void clear(){
		getCurrentSession().clear();
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String query) {
		return getCurrentSession().createSQLQuery(query).list();
	}

	public void executeUpdate(String query) {
		getCurrentSession().createSQLQuery(query).executeUpdate();		
	}
	
	public void saveOrUpdate(T obj){
		getCurrentSession().saveOrUpdate(obj);
	}
	
	public void refresh(T obj){
		getCurrentSession().refresh(obj);
	}
	
	public void evict(T obj){
		getCurrentSession().evict(obj);
	}
	
}