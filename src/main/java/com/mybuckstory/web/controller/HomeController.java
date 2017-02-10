package com.mybuckstory.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.MemberFeedService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.core.service.VideoService;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.Video;

@Controller
public class HomeController{
	
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private StoryService storyService;
	
	@Autowired 
	private MemberFeedService memberFeedService;
	
	@RequestMapping(value={"/", "/index.html"})
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("home");
		
		//get featured stories (3 max)		
		List<Story> recentStories = storyService.getRecentStories(3);
		mav.addObject("recentStories", recentStories);
		
		//TODO get trending stories
		
		//get featured video
		Video newestVideo = videoService.getFeaturedVideo();
		mav.addObject("featuredVideo", newestVideo);
		
		//get member feed activity (4 max)
		List<MemberFeedItem> memberFeed = memberFeedService.findAllPaginated(0, 4);
		mav.addObject("memberFeed", memberFeed);
		
		
		return mav;
	}

	
}
