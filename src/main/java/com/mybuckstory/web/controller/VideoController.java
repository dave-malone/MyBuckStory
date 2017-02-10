package com.mybuckstory.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.VideoService;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Video;

@Controller
public class VideoController{
	
	private static final Logger logger = Logger.getLogger(VideoController.class);
	
	@Autowired
	private VideoService videoService;
	
	@RequestMapping(value="/video/encodingNotification", method={RequestMethod.GET, RequestMethod.POST})
	public void handleEncodingNotification(@RequestParam(value="xml") String notificationXml){
		try{
			videoService.handleEncodingNotification(notificationXml);
		}catch(Exception e){
			logger.error("Failed to handle encoding notification", e);
		}
	}
	
	
	@RequestMapping(value="/video/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception{
		ModelAndView mav = new ModelAndView("videoForm");
		mav.addObject("command", new Video());
		return mav;
	}
	
	
	@RequestMapping(value="/videos", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max) {
		ModelAndView mav = new ModelAndView("listVideo");
		mav.addObject("videos", videoService.findAllPaginated(start, max));
		mav.addObject("totalVideos", videoService.getCount());
		return mav;
	}
	
	@RequestMapping(value="/video/show/{id}", method=RequestMethod.GET)
	public ModelAndView getById(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("showVideo");
		mav.addObject("video", videoService.getById(id));
		
		return mav;		
	}
	
	@RequestMapping(value="/video/save", method = RequestMethod.POST)
	public ModelAndView save(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("command", video);
		
//		validator.validate(video, result);
		
//		if(result.hasErrors()){		
//			mav.setViewName("videoForm");
//			mav.addObject("errorMessage", "There are some issues with the video you are creating");
//		}else{
		Video video = new Video(multipartRequest.getFile("file"));
		video.setDescription(multipartRequest.getParameter("description"));
		video.setTitle(multipartRequest.getParameter("title"));
		
		videoService.create(video);
		response.sendRedirect("/video/show/" + video.getId());
		return null;
//		}
		
//		return mav;
	}

	
	@RequestMapping(value="/video/comment/{id}", method = RequestMethod.POST)
	public void comment(@PathVariable("id") Long id, Comment comment, HttpServletResponse response) throws Exception{
		Video video = videoService.comment(id, comment);
		response.sendRedirect("/video/show/" + video.getId());	
	}
	
	@RequestMapping(value="/video/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) throws Exception{
		ModelAndView mav = new ModelAndView("videoForm");
		mav.addObject("command", videoService.getById(id));
		return mav;
	}
	
	//TODO - create update method
}
