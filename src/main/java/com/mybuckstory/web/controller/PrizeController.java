package com.mybuckstory.web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybuckstory.core.service.PrizeService;
import com.mybuckstory.model.Prize;

@Controller
@RequestMapping("/prize")
public class PrizeController extends AbstractBaseController<Prize, Long>{

	private final PrizeService prizeService;
	
	@Autowired
	public PrizeController(PrizeService prizeService) {
		super(Prize.class);		
		this.prizeService = prizeService;		
	}	

	@Override
	protected List<Prize> list(Integer start, Integer max, String sort, String order) {
		return prizeService.findAllPaginated(start, max);
	}
	
	@Override
	protected Long getTotalCount() {
		return prizeService.getCount();
	}

	@Override
	protected Prize getById(Long id) {
		return prizeService.getById(id);
	}

	@Override
	protected void save(Prize prize) {
		prizeService.create(prize);
	}

	@Override
	protected void delete(Prize prize) {
		prizeService.delete(prize);
	}


	@Override
	protected void update(Prize command) {
		Prize prize = prizeService.getById(command.getId());
		prize.setDescription(command.getDescription());
		try {
			prize.setFile(command.getFile());
		} catch (IOException e) {
			LOG.error("Failed to set image file on Prize", e);
		}
		prize.setTitle(command.getTitle());
		
		prizeService.update(prize);
	}

	@Override
	protected void addAttributesToEditModel(ModelMap model) {
		// TODO Auto-generated method stub
		
	}

}
