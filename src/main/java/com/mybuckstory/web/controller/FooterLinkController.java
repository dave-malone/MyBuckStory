package com.mybuckstory.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybuckstory.core.service.FooterLinkService;
import com.mybuckstory.model.FooterLink;

@Controller
@RequestMapping("/footerLink")
public class FooterLinkController extends AbstractBaseController<FooterLink, Long>{

	private final FooterLinkService footerLinkService;
	
	@Autowired
	public FooterLinkController(FooterLinkService footerLinkService) {
		super(FooterLink.class);		
		this.footerLinkService = footerLinkService;
	}


	@Override
	protected List<FooterLink> list(Integer start, Integer max, String sort, String order) {
		return footerLinkService.findAllPaginated(start, max);
	}


	@Override
	protected Long getTotalCount() {
		return footerLinkService.getCount();
	}

	@Override
	protected FooterLink getById(Long id) {
		return footerLinkService.getById(id);
	}

	@Override
	protected void save(FooterLink footerLink) {
		footerLinkService.create(footerLink);
	}

	@Override
	protected void delete(FooterLink footerLink) {
		footerLinkService.delete(footerLink);
	}

	@Override
	protected void update(FooterLink footerLink) {
		footerLinkService.update(footerLink);
	}


	@Override
	protected void addAttributesToEditModel(ModelMap model) {
		// TODO Auto-generated method stub
		
	}

}
