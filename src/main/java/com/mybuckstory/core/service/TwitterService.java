package com.mybuckstory.core.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.mybuckstory.core.event.TwitterEvent;
import com.mybuckstory.model.Story;
import com.mybuckstory.util.PreviewGenerator;

@Service
public class TwitterService implements ApplicationListener<TwitterEvent>{

	private static final Logger logger = Logger.getLogger(TwitterService.class);
	private static final String MAIN_URL = "http://mybuckstory.com";
	private static final int STATUS_MAX_LENGTH = 140;

	@Autowired
	private BitlyService bitly;
	
	@Override
	public void onApplicationEvent(TwitterEvent event) {
		logger.info("twitter event received: " + event);
		try {
			updateStatus(event.getStory());			
		} catch (Exception e) {
			logger.error("Failed to update twitter status: " + e.getMessage());
		} 
	}
	
	/**
	 * 
	 * @param story
	 * @return the status text posted to Twitter
	 * @throws ParserException
	 */
	public String updateStatus(Story story) throws ParserException, TwitterException{
		if(isDisabled()){
			String status = "The TwitterService is disabled";
			logger.warn(status);
			return status;
		}
			
		String fullUrl = MAIN_URL + story.getUri();
		String bitlyUrl = bitly.shorten(fullUrl);
		
		if(StringUtils.isBlank(bitlyUrl)){
			throw new IllegalStateException("Bitly URL was blank");
		}
		
		String prefix = story.getTitle() + " - ";
		String suffix = "..." + bitlyUrl;
		
		int lengthSoFar = prefix.length() + suffix.length();
		int lengthRemaining = STATUS_MAX_LENGTH - lengthSoFar;
		
		String storyPreview = PreviewGenerator.generatePreview(story.getText(), lengthRemaining);		
		String status = prefix + storyPreview + suffix;
		Twitter twitter = new TwitterFactory().getInstance();
		Status twitterStatus = twitter.updateStatus(status);
		logger.debug("Twitter status: " + twitterStatus);
		
		return status;
	}
	
	private boolean isDisabled(){
		return !"prod".equalsIgnoreCase(System.getProperty("environment"));
	}
	
}