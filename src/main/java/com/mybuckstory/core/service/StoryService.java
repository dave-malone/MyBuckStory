package com.mybuckstory.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
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
import com.mybuckstory.core.event.BadgeAwardingEvent;
import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.core.event.StoryCommentEvent;
import com.mybuckstory.core.event.TwitterEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Category;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.User;
import com.mybuckstory.util.PreviewGenerator;
import com.mybuckstory.util.UriUtil;
import com.mybuckstory.web.command.StorySearch;

@Service
@Transactional
public class StoryService extends SingleImageContainerService<Story> implements ResourceLookupService{

	private final ApplicationEventService eventService;
	private final CategoryService categoryService;
	
	public StoryService(){
		this(null, null, null, 0, 0);
	}
	
	@Autowired
	public StoryService(ApplicationEventService eventService, CategoryService categoryService, @Qualifier("storyDao") GenericHibernateDao<Story, Long> dao, @Value("${story.width}") int thumbWidth, @Value("${story.height}") int thumbHeight) {
		super(Story.class, dao, thumbWidth, thumbHeight);
		this.eventService = eventService;
		this.categoryService = categoryService;
	}
	
	@Transactional(readOnly=true)
	public List<Story> getRecentStories(final int max){
		return getRecentStories(max, null, null);
	}

	@Transactional(readOnly=true)
	public List<Story> getRecentStories(final int max, final Long authorId, final String category){
		Criteria criteria = getStoriesByCategoryCriteria(category)
				.setMaxResults(max).addOrder(Order.desc("dateCreated"));

		if (authorId != null) {
			criteria.add(Restrictions.eq("createdBy.id", authorId));
		}
		
		criteria.setCacheable(true);
		return (List<Story>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Story> findAllPaginated(final int start, final int max) {		
		return (List<Story>)getCurrentSession().createCriteria(Story.class)
			.setFirstResult(start)
			.setMaxResults(max)
			.addOrder(Order.desc("averageRating"))
			.setCacheable(true)
			.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Story> findStoriesByCategory(final String category){
		Criteria criteria = getStoriesByCategoryCriteria(category); 
		
		criteria.setCacheable(true)
				.addOrder(Order.asc("title"));
		
		return (List<Story>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Story> findStoriesByCategory(final int max, final int start, final String category){
		Criteria criteria = getStoriesByCategoryCriteria(category); 
		
		criteria.setMaxResults(max)
				.setFirstResult(start)
				.setCacheable(true)
				.addOrder(Order.desc("averageRating"));
		
		return (List<Story>)criteria.list();
	}
	
	@Transactional(readOnly=true)
	public Long getStoriesPublishedByAuthorInCategoryCount(final String category, final User author){
		return (Long)getStoriesByCategoryCriteria(category)
				.add(Restrictions.eq("createdBy", author))
				.setProjection(Projections.count("id"))				
				.uniqueResult();
	}
	
	@Transactional(readOnly=true)
	public Integer getStoriesByCategoryCount(final String category){
		return (Integer)getStoriesByCategoryCriteria(category)
				.setProjection(Projections.count("id"))
				.uniqueResult();
	}
	
	@Transactional(readOnly=true)
	private Criteria getStoriesByCategoryCriteria(String categoryName){
		Criteria criteria = getCurrentSession().createCriteria(Story.class);				
		
		if(StringUtils.isNotBlank(categoryName)){
			Category category = categoryService.findByName(categoryName);
			
			if(category != null){
				if(category.getChildren().isEmpty()){
					criteria.createCriteria("categories")
						.add(Restrictions.eq("name", categoryName));
				}else{
					criteria.createCriteria("categories")
						.add(Restrictions.disjunction()
							.add(Restrictions.eq("name", categoryName))
							.add(Restrictions.eq("parent", category)));
				}
			}
		}
		
		return criteria;
	}
	
	@Transactional(readOnly=true)
	public boolean uriInUse(final String uri){
		return findByUri(uri) != null;
	}

	

	@Override
	public Long create(Story story) {
		setURI(story);		
		setSEOFields(story);		
		Long id = super.create(story);
		
		try{
			eventService.publishAsynch(new BadgeAwardingEvent(this, story));
			eventService.publishAsynch(new TwitterEvent(this, story));	
			eventService.publishAsynch(new MemberFeedEvent(this, new MemberFeedItem(story)));
		}catch(Exception e){
			logger.error("failed to publish story events asynchronously", e);
		}
		
		logger.error("A new story has been submitted: " + story.getUri());
		
		return id;
	}

	private void setURI(Story story) {
		String uri = UriUtil.buildPath(Constants.STORIES, story.getDateCreated(), story.getTitle());
		
		int count = 1;		
		while(uriInUse(uri)){
			uri = UriUtil.buildPath(Constants.STORIES, story.getDateCreated(), story.getTitle() + count++);
		}
		
		story.setUri(uri);
	}

	private void setSEOFields(Story story) {
		try{
			if(StringUtils.isBlank(story.getMetaKeywords())){
				story.setMetaKeywords(story.getTitle());
			}
			
			if(StringUtils.isBlank(story.getMetaDescription())){
				try {
					story.setMetaDescription(PreviewGenerator.generatePreview(story.getText(), 175));
				} catch (ParserException e) {
					logger.error("failed to automatically create meta description for story " + story.getTitle(), e);
				}
			}
		}catch(Exception e){
			logger.error("Failed to set the SEO fields for the story", e);
		}
	}

	@Transactional(readOnly=true)
	public Story findByUri(final String uri) {
		Session session = getCurrentSession();
		
		return (Story)session.createCriteria(Story.class)
			.add(Restrictions.eq("uri", uri))
			.setCacheable(true)
			.uniqueResult();
	}

	@Transactional(readOnly=true)
	public Long getStoryCount(final Long authorId) {
		Session session = getCurrentSession();
		
		Criteria criteria = session.createCriteria(Story.class).setProjection(Projections.rowCount());						
		
		if(authorId != null && authorId != 0){
			criteria.createCriteria("createdBy").add(Restrictions.eq("id", authorId));
		}
		
		return (Long)criteria.uniqueResult();
	}

	@Transactional(readOnly=true)
	public List<Story> getStoriesPaginated(final Integer start, final Integer max) {
		return getStoriesPaginated(null, start, max);
	}
	
	@Transactional(readOnly=true)
	public List<Story> getStoriesPaginated(final Long authorId, final Integer start, final Integer max) {
		Session session = getCurrentSession();
		
		Criteria criteria = session.createCriteria(Story.class)
			.setMaxResults(max != null ? max : 10)
			.setFirstResult(start != null ? start : 0)
			.addOrder(Order.desc("dateCreated"));
		
		if (authorId != null && authorId != 0) {
			criteria.createCriteria("createdBy").add(Restrictions.eq("id", authorId));
		}

		@SuppressWarnings("unchecked")
		List<Story> stories = criteria.list();
		return stories;
	}


	@Override
	public void update(Story story) {
		setSEOFields(story);		
		super.update(story);
	}

	@Transactional(readOnly=true)
	public List<Story> search(StorySearch search) {
		Criteria criteria = getStoriesByCategoryCriteria(search.getCategory()).setCacheable(true); 
		
		criteria.setMaxResults(search.getMax() != null ? search.getMax() : 15)
				.setFirstResult(search.getStart() != null ? search.getStart() : 0)
				.setCacheable(true);
		
		if (StringUtils.isNotBlank(search.getGeneralSearch())) {
			criteria.add(Restrictions.disjunction()
						.add(Restrictions.ilike("title", search.getGeneralSearch(), MatchMode.ANYWHERE))
						.add(Restrictions.ilike("text", search.getGeneralSearch(), MatchMode.ANYWHERE)));
		}
		
		if("mostPopular".equalsIgnoreCase(search.getFilter())){
			criteria.addOrder(Order.desc("averageRating"));
		}else if("topVoted".equalsIgnoreCase(search.getFilter())){
			criteria.addOrder(Order.desc("voteCount"));
		}else{
			criteria.addOrder(Order.desc("dateCreated"));
		}
		
		@SuppressWarnings("unchecked")
		List<Story> stories = criteria.list();		
		return stories;
		
	}

	@Transactional(readOnly=true)
	public Long getSearchCount(StorySearch search) {
		Criteria criteria = getStoriesByCategoryCriteria(search.getCategory()).setCacheable(true);

		if (StringUtils.isNotBlank(search.getGeneralSearch())) {
			criteria.add(Restrictions.disjunction()
						.add(Restrictions.ilike("title", search.getGeneralSearch(), MatchMode.ANYWHERE))
						.add(Restrictions.ilike("text", search.getGeneralSearch(), MatchMode.ANYWHERE)));
		}

		return (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	public Story comment(Long id, Comment comment){
		Story story = (Story)getById(id);
		story.getComments().add(comment);
		update(story);
		flush();
		
		if(!story.getCreatedBy().equals(comment.getCreatedBy())){
			logger.info("sending story comment email notification");
			flush();
			eventService.publishAsynch(new StoryCommentEvent(this, story, comment));					
		}
		MemberFeedItem item = new MemberFeedItem();				
		item.setStoryComment(story, comment);
		eventService.publish(new MemberFeedEvent(this, item));
		return story;
	}
	
}