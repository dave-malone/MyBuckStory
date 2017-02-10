package com.mybuckstory.core.service;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ApplicationEventService implements ApplicationEventPublisherAware{

	private static final Logger logger = Logger.getLogger(ApplicationEventService.class);
	private ApplicationEventPublisher applicationEventPublisher;

	@Async
	public void publishAsynch(ApplicationEvent event) {
		applicationEventPublisher.publishEvent(event);
	}
	
	public void publish(ApplicationEvent event) {
		try{
			applicationEventPublisher.publishEvent(event);
		}catch(Exception e){
			logger.error("an error occurred during synchronous event publishing", e);
		}
	}
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;		
	}



	

}
