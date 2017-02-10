package com.mybuckstory.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.core.event.MessageSentEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Message;
import com.mybuckstory.model.User;

@Service
@Transactional
public class MessageService extends GenericMbsService<Message>{
	
	private final UserService userService;
	private final ApplicationEventService eventService;
	
	public MessageService(){
		this(null, null, null);
	}
	
	@Autowired
	public MessageService(UserService userService, ApplicationEventService eventService, @Qualifier("messageDao") GenericHibernateDao<Message, Long> dao){
		super(Message.class, dao);		
		this.userService = userService;
		this.eventService = eventService;
	}	

	public Long reply(Long originalMessageId, Message reply){
		Message originalMessage = getById(originalMessageId);
		return reply(originalMessage, reply);
	}
	
	public Long reply(Message originalMessage, Message reply){
		if(originalMessage == null){
			throw new IllegalArgumentException("[originalMessage] was null");
		}
		if(reply == null){
			throw new IllegalArgumentException("[reply] was null");
		}
		
		/*
		User currentUser = UserUtil.getCurrentUser();
		
		//if original message was also sent from this user, then set the mostRecent flag to false
		if(originalMessage.getCreatedBy().getId() == currentUser.getId()){
			originalMessage.setMostRecent(false);
		}
		*/
		originalMessage.setMostRecent(false);
		reply.setInResponseTo(originalMessage);
		
		if(!originalMessage.getSubject().startsWith("RE: ")){
			reply.setSubject("RE: " + originalMessage.getSubject());
		}else{
			reply.setSubject(originalMessage.getSubject());
		}
		
		return create(reply);
	}
	

	@Override
	public Long create(Message message){
		User to = userService.getById(message.getTo().getId());
		message.setTo(to);
		super.create(message);
		eventService.publishAsynch(new MessageSentEvent(this, message));		
		return message.getId();
	}

	@Transactional(readOnly=true)
	public List<Message> getMessages(final User to, final int start, final int max){
		Criteria criteria = getCurrentSession().createCriteria(Message.class)
			.add(Restrictions.eq("to", to))			
			//.add(Restrictions.eq("mostRecent", true))
			.add(Restrictions.ne("createdBy", to))
			.add(Restrictions.eq("deleted", false))
			.setFirstResult(start)
			.setMaxResults(max > 0 ? max : 10)
			.addOrder(Order.desc("dateCreated"))
			.setCacheable(true);
		
		
		Criteria createdByCriteria = criteria.setFetchMode("createdBy", FetchMode.SELECT)
				.createCriteria("createdBy").setFetchMode("profile", FetchMode.SELECT)
					.createCriteria("profile").setFetchMode("image", FetchMode.SELECT);
		
		Criteria toCriteria = criteria.setFetchMode("to", FetchMode.SELECT)
				.createCriteria("to").setFetchMode("profile", FetchMode.SELECT)
					.createCriteria("profile").setFetchMode("image", FetchMode.SELECT);
		
		return (List<Message>)criteria.list();
	}
	
	@Transactional(readOnly=true)
	public Long getReceivedMessagesCount(final User to){
		Criteria criteria = getCurrentSession().createCriteria(Message.class)
			.add(Restrictions.eq("to", to))			
			//.add(Restrictions.eq("mostRecent", true))
			.add(Restrictions.ne("createdBy", to))
			.add(Restrictions.eq("deleted", false))
			.setCacheable(true);
				
		return (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@Transactional(readOnly=true)
	public Long getSentMessagesCount(final User from){
		Criteria criteria = getCurrentSession().createCriteria(Message.class)
				.add(Restrictions.eq("createdBy", from))			
				//.add(Restrictions.eq("mostRecent", true))
				.add(Restrictions.ne("to", from))
				.add(Restrictions.eq("deletedFromSentMessages", false))
			.setCacheable(true);
				
		return (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@Transactional(readOnly=true)
	public List<Message> getSentMessages(final User from, final int start, final int max){
		Criteria criteria = getCurrentSession().createCriteria(Message.class)
			.add(Restrictions.eq("createdBy", from))			
			//.add(Restrictions.eq("mostRecent", true))
			.add(Restrictions.ne("to", from))
			.add(Restrictions.eq("deletedFromSentMessages", false))
			.setFirstResult(start)
			.setMaxResults(max > 0 ? max : 10)
			.addOrder(Order.desc("dateCreated"))
			.setCacheable(true);
		
		Criteria createdByCriteria = criteria.setFetchMode("createdBy", FetchMode.SELECT)
				.createCriteria("createdBy").setFetchMode("profile", FetchMode.SELECT)
					.createCriteria("profile").setFetchMode("image", FetchMode.SELECT);
		
		Criteria toCriteria = criteria.setFetchMode("to", FetchMode.SELECT)
				.createCriteria("to").setFetchMode("profile", FetchMode.SELECT)
					.createCriteria("profile").setFetchMode("image", FetchMode.SELECT);
		
		return (List<Message>)criteria.list();
	}

	public void delete(Long messageId){
		delete(getById(messageId));
	}
	
	@Override
	public void delete(Message message){
		message.setDeleted(true);
		super.update(message);
	}	
	
	public void deleteFromSentMessages(Long messageId) {
		Message message = getById(messageId);
		message.setDeletedFromSentMessages(true);
		super.update(message);
	}
	
	
	
	@Transactional(readOnly=true)
	public List<Message> getMessageChain(Message message){
		List<Message> history = new ArrayList<Message>();
		history.add(message);
		
		while(message.getInResponseTo() != null){
			history.add(message = message.getInResponseTo());
		}
		
		Collections.reverse(history);
		return history;
	}

	

}
