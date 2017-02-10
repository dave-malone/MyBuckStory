package com.mybuckstory.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.common.Constants;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.NewsArticle;
import com.mybuckstory.util.PreviewGenerator;
import com.mybuckstory.util.UriUtil;
@Service
@Transactional
public class NewsArticleService extends SingleImageContainerService<NewsArticle> implements ResourceLookupService{
	
	public NewsArticleService(){
		this(null, 0, 0);
	}
	
	
	@Autowired
	public NewsArticleService(@Qualifier("newsArticleDao") GenericHibernateDao<NewsArticle, Long> dao, @Value("${news.width}") int thumbWidth, @Value("${news.height}") int thumbHeight) {
		super(NewsArticle.class, dao, thumbWidth, thumbHeight);
	}
	
	@Transactional(readOnly=true)
	public List<NewsArticle> getNewsByCategory(Integer start, Integer max, final String categoryName){
		Session session = getCurrentSession();
		
		if(start == null || max == null){
			start = 0; 
			max = 10;
		}
		
		Criteria criteria = session.createCriteria(NewsArticle.class)
					.setFirstResult(start)
					.setMaxResults(max)
					.addOrder(Order.desc("dateCreated"))
					.setCacheable(true);
			
		if(StringUtils.isNotBlank(categoryName)){
			criteria.createCriteria("categories").add(Restrictions.eq("name", categoryName));
		}
			
		@SuppressWarnings("unchecked")
		List<NewsArticle> news = criteria.list();	
		
		return news;
	}
	
	@Transactional(readOnly=true)
	public Long getNewsByCategoryCount(final String categoryName){
		Session session = getCurrentSession();
		
		Criteria criteria = session.createCriteria(NewsArticle.class).setCacheable(true).setProjection(Projections.rowCount());
			
		if(StringUtils.isNotBlank(categoryName)){
			criteria.createCriteria("categories").add(Restrictions.eq("name", categoryName));
		}
		
		return (Long)criteria.uniqueResult();
	}	
	
	@Transactional(readOnly=true)
	public NewsArticle findByUri(final String uri){
		return (NewsArticle)getCurrentSession().createCriteria(NewsArticle.class)
					.add(Restrictions.eq("uri", uri))
					.setCacheable(true)
					.uniqueResult();
	}
	
	@Transactional(readOnly=true)
	public boolean uriInUse(String uri){
		return findByUri(uri) != null;
	}


	@Override
	public Long create(NewsArticle news) {
		setUri(news);		
		setSEOFields(news);
		return super.create(news);
	}


	public void setSEOFields(NewsArticle news) {
		if(StringUtils.isBlank(news.getMetaKeywords())){
			news.setMetaKeywords(news.getTitle());
		}
		
		if(StringUtils.isBlank(news.getMetaDescription())){
			try {
				news.setMetaDescription(PreviewGenerator.generatePreview(news.getContent(), 175));
			} catch (ParserException e) {
				logger.error("failed to automatically create meta description for news article " + news.getTitle(), e);
			}
		}
	}


	public void setUri(NewsArticle news) {
		String uri = UriUtil.buildPath(Constants.NEWS_ARTICLES, news.getDateCreated(), news.getTitle());
		int count = 1;
		while(uriInUse(uri)){
			uri = UriUtil.buildPath(Constants.NEWS_ARTICLES, news.getDateCreated(), news.getTitle() + count++);
		}
		news.setUri(uri);
	}


	@Override
	public void update(NewsArticle news) {
		setSEOFields(news);
		super.update(news);
	}
	
	@Override
	public void saveOrUpdate(NewsArticle news) {
		setSEOFields(news);
		super.saveOrUpdate(news);
	}
	
	public NewsArticle comment(Long id, Comment comment){
		NewsArticle article = (NewsArticle) getById(id);
		article.getComments().add(comment);
		update(article);
		return article;
	}
	
	
}