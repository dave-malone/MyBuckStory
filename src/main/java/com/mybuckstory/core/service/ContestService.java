package com.mybuckstory.core.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.common.Constants;
import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Contest;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.util.PreviewGenerator;
import com.mybuckstory.util.UriUtil;

@Service
@Transactional
public class ContestService extends SingleImageContainerService<Contest> implements ResourceLookupService{
	
	private final ApplicationEventService applicationEventService;
	
	public ContestService(){
		this(null, null, null, null);
	}
	
	@Autowired
	public ContestService(ApplicationEventService applicationEventService, @Qualifier("contestDao") GenericHibernateDao<Contest, Long> dao, @Value("${competition.width}") Integer thumbWidth, @Value("${competition.height}") Integer thumbHeight){
		super(Contest.class, dao, thumbWidth, thumbHeight);
		this.applicationEventService = applicationEventService;
	}

	@Override
	public Contest findByUri(final String uri){
		Session session = getCurrentSession();
		Contest contest = (Contest)session.createCriteria(Contest.class)
			.add(Restrictions.eq("uri", uri))
			.setCacheable(true)
			.uniqueResult();
		
		return contest;
	}
	
	@Override
	public boolean uriInUse(String uri){
		return findByUri(uri) != null;
	}

	
	
	@Override
	public Long create(Contest contest) {		
		setUri(contest);		
		setSEOFields(contest);
		
		Long id = super.create(contest);
		//publish in member feeds
		applicationEventService.publish(new MemberFeedEvent(this, new MemberFeedItem(contest)));	
		
		return id;
	}

	public void setSEOFields(Contest contest) {
		if(StringUtils.isBlank(contest.getMetaKeywords())){
			contest.setMetaKeywords(contest.getTitle());
		}
		
		if(StringUtils.isBlank(contest.getMetaDescription())){
			try {
				contest.setMetaDescription(PreviewGenerator.generatePreview(contest.getDescription(), 175));
			} catch (ParserException e) {
				logger.error("failed to automatically create meta description for contest " + contest.getTitle(), e);
			}
		}
	}

	public void setUri(Contest contest) {
		Date date;
		try {
			date = contest.getStartDate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String uri = UriUtil.buildPath(Constants.CONTESTS, date, contest.getTitle());
		int count = 1;
		while(uriInUse(uri)){
			uri = UriUtil.buildPath(Constants.CONTESTS, date, contest.getTitle() + count++);
		}
		contest.setUri(uri);
	}


	public Contest comment(Long id, Comment comment){
		Contest contest = getById(id);
		contest.getComments().add(comment);
		update(contest);
		return contest;
	}

	@Override
	public void saveOrUpdate(Contest contest) {
		setSEOFields(contest);
		super.saveOrUpdate(contest);
	}

	@Override
	public void update(Contest contest) {
		setSEOFields(contest);
		super.update(contest);
	}
	
	
}
