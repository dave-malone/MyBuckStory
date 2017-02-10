package com.mybuckstory.core.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.common.Constants;
import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Event;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.util.PreviewGenerator;
import com.mybuckstory.util.UriUtil;

@Service
@Transactional
public class EventService extends GenericMbsService<Event> implements ResourceLookupService{
	
	private final ApplicationEventService eventService;
	
	public EventService(){
		this(null, null);
	}
	
	@Autowired
	public EventService(ApplicationEventService eventService, @Qualifier("eventDao") GenericHibernateDao<Event, Long> dao){
		super(Event.class, dao);
		this.eventService = eventService;
	}

	public Event findByUri(final String uri){
		return (Event)getCurrentSession().createCriteria(Event.class)
			.add(Restrictions.eq("uri", uri))
			.setCacheable(true)
			.uniqueResult();
	}
	
	public boolean uriInUse(String uri){
		return findByUri(uri) != null;
	}


	@Override
	public Long create(Event event) {		
		setUri(event);		
		setSEOFields(event);		
		Long id = super.create(event);		
		eventService.publish(new MemberFeedEvent(this, new MemberFeedItem(event)));		
		return id;
	}
	
	@Override
	public void saveOrUpdate(Event event) {
		setSEOFields(event);
		super.saveOrUpdate(event);
	}

	@Override
	public void update(Event event) {
		setSEOFields(event);
		super.update(event);
	}

	public void setSEOFields(Event event) {
		if(StringUtils.isBlank(event.getMetaKeywords())){
			event.setMetaKeywords(event.getTitle());
		}
		
		if(StringUtils.isBlank(event.getMetaDescription())){
			try {
				event.setMetaDescription(PreviewGenerator.generatePreview(event.getDescription(), 175));
			} catch (ParserException e) {
				logger.error("failed to automatically create meta description for event " + event.getTitle(), e);
			}
		}
	}

	public void setUri(Event event) {
		Date date;
		try {
			date = event.getDate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String uri = UriUtil.buildPath(Constants.EVENTS, date, event.getTitle());
		int count = 1;
		while(uriInUse(uri)){
			uri = UriUtil.buildPath(Constants.EVENTS, date, event.getTitle() + count++);
		}
		event.setUri(uri);
	}

	public Event comment(Long id, Comment comment){
		Event event = getById(id);
		event.getComments().add(comment);
		update(event);
		return event;
	}
	
	
}
