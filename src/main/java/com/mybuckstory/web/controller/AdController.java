package com.mybuckstory.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.AdService;
import com.mybuckstory.model.Ad;
import com.mybuckstory.model.AdClick;

@Controller
@RequestMapping("/ad")
public class AdController extends AbstractBaseController<Ad, Long> implements InitializingBean{

	private final AdService adService;
	
	@Autowired
	public AdController(AdService adService) {
		super(Ad.class);		
		this.adService = adService;
	}	
	
	@Override
	public void afterPropertiesSet(){
		if(this.adService == null){
			throw new IllegalStateException("[adManager] is null");
		}
	}
	
	@RequestMapping(value = "/click/{id}", method = RequestMethod.GET)
	public void trackClick(@PathVariable("id") final Long adId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Ad ad = adService.getById(adId);
		
		AdClick adClick = new AdClick();
		adClick.setAd(ad);
		adClick.setDateCreated(new Date());
		adClick.setUserAgent(request.getHeader("User-Agent"));
		adClick.setUserIpAddress(request.getRemoteAddr());
		adClick.setReferrer(request.getHeader("Referer"));
		
		adService.save(adClick);
		
		response.sendRedirect(ad.getWebsite());		
	}
	
	@RequestMapping(value = "/clicks/{id}", method = RequestMethod.GET)
	public ModelAndView getAdClicks(@PathVariable("id") final Long adId, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max) throws Exception{
		ModelAndView mav = new ModelAndView("adclicks");
		
		Ad ad = adService.getById(adId);
		List<AdClick> adClicks = adService.getAdClicks(ad, start, max);
		Long totalAdClicks = adService.getTotalAdClicks(ad);
		
		mav.addObject("ad", ad);
		mav.addObject("adClicks", adClicks);
		mav.addObject("totalAdClicks", totalAdClicks);
		
		return mav;
	}

	@Override
	protected List<Ad> list(Integer start, Integer max, String sort, String order) {
		return adService.findAllPaginated(start, max);
	}



	@Override
	protected Long getTotalCount() {
		return adService.getCount();
	}

	@Override
	protected Ad getById(Long id) {
		return adService.getById(id);
	}

	@Override
	protected void save(Ad ad) {
		adService.create(ad);
	}

	@Override
	protected void delete(Ad ad) {
		adService.delete(ad);
	}


	@Override
	protected void update(Ad ad) {
		adService.update(ad);
	}

	@Override
	protected void addAttributesToEditModel(ModelMap model) {
		// TODO Auto-generated method stub
		
	}

}
