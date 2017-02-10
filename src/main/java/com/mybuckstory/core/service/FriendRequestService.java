package com.mybuckstory.core.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.FriendRequestEvent;
import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.FriendRequest;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;

@Service
@Transactional
public class FriendRequestService extends GenericMbsService<FriendRequest> {

	private final ApplicationEventService eventService;
	private final UserService userService;
	
	
	public FriendRequestService(){
		this(null, null, null);
	}

	@Autowired
	public FriendRequestService(ApplicationEventService eventService, UserService userService, @Qualifier("friendRequestDao") GenericHibernateDao<FriendRequest, Long> dao){
		super(FriendRequest.class, dao);
		this.userService = userService;
		this.eventService = eventService;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FriendRequest> getUnAnsweredRequests(final User to){
		return (List<FriendRequest>)getCurrentSession().createCriteria(FriendRequest.class)
		.add(Restrictions.eq("to", to))
		.add(Restrictions.eq("responded", false))
		.setCacheable(true)
		.list();
	}
	
	@Transactional(readOnly=true)
	public boolean friendRequestAlreadySent(final Long fromId, final Long toId){
		Criteria criteria = getCurrentSession().createCriteria(FriendRequest.class).setCacheable(true);	
		criteria.createCriteria("createdBy").add(Restrictions.eq("id", fromId));
		criteria.createCriteria("to").add(Restrictions.eq("id", toId));		
		
		return criteria.uniqueResult() != null;
	}

	public boolean sendRequest(Long toId) {
		if(toId == null){
			throw new IllegalArgumentException("[toId] may not be null");
		}
		
		boolean success = false;
		
		User to = userService.loadById(toId);
		if(to == null){
			throw new IllegalArgumentException("Unable to find a user with id " + toId);
		}
		FriendRequest friendRequest = new FriendRequest();
		friendRequest.setTo(to);
		try{
			create(friendRequest);
			success = true;
			eventService.publishAsynch(new FriendRequestEvent(this, friendRequest));						
		}catch(Exception e){
			logger.warn(e);
		}
		return success;
	}

	public void removeFriend(Long friendId){
		if(friendId == null){
			throw new IllegalArgumentException("[friendId] may not be null");
		}
		logger.debug("Current user requested that a friend be removed");
		User currentUser = UserUtil.getCurrentUser();
		logger.debug("Current user: " + currentUser);
		User friend = userService.getById(friendId);
		logger.debug("Friend: " + friend);
		if(friend == null){
			throw new IllegalArgumentException("unable to find friend with id " + friendId);
		}
		logger.debug("Removing 'friend' relationship");
		currentUser.getProfile().getFriends().remove(friend);
		friend.getProfile().getFriends().remove(currentUser);
	}
	
	public void respondToRequest(Long requestId, boolean accept) {
		if(requestId == null){
			throw new IllegalArgumentException("[requestId] may not be null");
		}
		logger.debug("Request id: " + requestId);
		logger.debug("Accepted? " + accept);
		FriendRequest friendRequest = getById(requestId);
		if(friendRequest == null){
			throw new IllegalArgumentException("Unable to find a Friend Request with id " + requestId);
		}
		friendRequest.setAccepted(accept);
		friendRequest.setResponded(true);
		
		if(accept){
			friendRequest.getCreatedBy().getProfile().getFriends().add(friendRequest.getTo());
			friendRequest.getTo().getProfile().getFriends().add(friendRequest.getCreatedBy());
			
			eventService.publish(new MemberFeedEvent(this, new MemberFeedItem(friendRequest)));			
		}	
		
		update(friendRequest);
		
	}

}
