package com.mybuckstory.core.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Like;
import com.mybuckstory.model.LikePK;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.WallPost;
import com.mybuckstory.model.MemberFeedItem.Type;
import com.mybuckstory.util.UserUtil;

@Service
@Transactional
public class WallPostService extends GenericMbsService<WallPost> {
	
	private final ApplicationEventService eventService;
	
	public WallPostService(){
		this(null, null);
	}	
	
	@Autowired
	public WallPostService(ApplicationEventService eventService, @Qualifier("wallPostDao") GenericHibernateDao<WallPost, Long> dao) {
		super(WallPost.class, dao);
		this.eventService = eventService;
	}
	
	public Like like(Long wallPostId){
		WallPost wallPost = hibernateDao.getById(wallPostId);
		if(wallPost == null){
			throw new IllegalArgumentException("Invalid wall post id: " + wallPostId);
		}
		LikePK pk = new LikePK(wallPost, UserUtil.getCurrentUser());
		Like like = new Like(pk);
//		likeDao.save(like);
		wallPost.getLikes().add(like);
		update(wallPost);
		
		MemberFeedItem item = new MemberFeedItem();
		item.setLike(like);
		item.setWallPost(wallPost);
		eventService.publish(new MemberFeedEvent(this, item));
		
		return like;
	}

	@Transactional(readOnly=true)
	public WallPost getMostRecentStatus(final Long userId) {
		Session session = getCurrentSession();
		
		return (WallPost)session.createCriteria(WallPost.class)
			.createAlias("createdBy", "c")
			.createAlias("target", "t")
			.add(Restrictions.eqProperty("c.id", "t.id"))
			.add(Restrictions.eq("c.id", userId))
			.setCacheable(true)	
			.setMaxResults(1)
			.addOrder(Order.desc("dateCreated"))
			.uniqueResult();
	}
	
	@Transactional(readOnly=true)
	public List<WallPost> getWallPosts(final Long userId, Integer start, Integer max) {
		Session session = getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<WallPost> wallPosts = session.createCriteria(WallPost.class)
			.createAlias("target", "t")
			.add(Restrictions.eq("t.id", userId))
			.setCacheable(true)	
			.setFirstResult(start != null ? start : 0)
			.setMaxResults(max != null ? max : 20)
			.addOrder(Order.desc("dateCreated"))
			.list();
		
		return wallPosts;
	}
	
	@Transactional(readOnly=true)
	public Long getWallPostsCount(Long userId) {
		Session session = getCurrentSession();
		
		
		return (Long)session.createCriteria(WallPost.class)
			.createAlias("target", "t")
			.add(Restrictions.eq("t.id", userId))
			.setCacheable(true)
			.setProjection(Projections.rowCount())
			.uniqueResult();
	}

	@Override
	public Long create(WallPost post) {
		Long id = super.create(post);
		MemberFeedItem memberFeedItem = new MemberFeedItem(post);
		memberFeedItem.setType(Type.WALL_POST);
		eventService.publish(new MemberFeedEvent(this, memberFeedItem));
		return id;
	}

	public WallPost comment(Long id, Comment comment) {
		WallPost wallPost = getById(id);
		wallPost.getComments().add(comment);
		update(wallPost);
		flush();
		MemberFeedItem item = new MemberFeedItem();
		item.setWallPostComment(wallPost, comment);
		eventService.publish(new MemberFeedEvent(this, item));
		return wallPost;
	}

}
