package com.mybuckstory.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybuckstory.core.service.BadgeService;
import com.mybuckstory.core.service.ContestPrizeService;
import com.mybuckstory.core.service.ContestService;
import com.mybuckstory.core.service.PrizeService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.model.Badge;
import com.mybuckstory.model.Contest;
import com.mybuckstory.model.ContestPrize;
import com.mybuckstory.model.Prize;
import com.mybuckstory.model.Story;
import com.mybuckstory.web.editor.BadgeEditor;
import com.mybuckstory.web.editor.ContestEditor;
import com.mybuckstory.web.editor.PrizeEditor;
import com.mybuckstory.web.editor.StoryEditor;

@Controller
@RequestMapping("/contestPrize")
public class ContestPrizeController extends	AbstractBaseController<ContestPrize, Long> {

	private final PrizeService prizeService;
	private final BadgeService badgeService;
	private final ContestService contestService;
	private final ContestPrizeService contestPrizeService;
	private final StoryService storyService;

	@Autowired
	public ContestPrizeController(
			ContestPrizeService contestPrizeService,
			PrizeService prizeService, BadgeService badgeService,
			ContestService contestService,
			StoryService storyService) {
		super(ContestPrize.class);
		this.contestPrizeService = contestPrizeService;
		this.prizeService = prizeService;
		this.badgeService = badgeService;
		this.contestService = contestService;
		this.storyService = storyService;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Badge.class, new BadgeEditor(badgeService));
		binder.registerCustomEditor(Contest.class, new ContestEditor(contestService));
		binder.registerCustomEditor(Prize.class, new PrizeEditor(prizeService));
		binder.registerCustomEditor(Story.class, new StoryEditor(storyService));
	}

	@ModelAttribute("badges")
	public List<Badge> populateBadges() {
		return badgeService.findAll();
	}

	@ModelAttribute("contests")
	public List<Contest> populateContests() {
		return contestService.findAll();
	}

	@ModelAttribute("prizes")
	public List<Prize> populatePrizes() {
		return prizeService.findAll();
	}
	
	@Override
	protected List<ContestPrize> list(Integer start, Integer max, String sort, String order) {
		return contestPrizeService.findAllPaginated(start, max);
	}

	@Override
	protected Long getTotalCount() {
		return contestPrizeService.getCount();
	}

	@Override
	protected ContestPrize getById(Long id) {
		return contestPrizeService.getById(id);
	}

	@Override
	protected void save(ContestPrize contestPrize) {
		contestPrizeService.create(contestPrize);
	}

	@Override
	protected void delete(ContestPrize contestPrize) {
		contestPrizeService.delete(contestPrize);
	}

	@Override
	protected void update(ContestPrize contestPrize) {
		contestPrizeService.update(contestPrize);
	}

	@Override
	protected void addAttributesToEditModel(ModelMap model) {
		ContestPrize contestPrize = (ContestPrize)model.get("command");
		if(contestPrize.getContest() != null && contestPrize.getContest().getStoryCategory() != null){
			List<Story> storiesInCompetitionCategory = storyService.findStoriesByCategory(contestPrize.getContest().getStoryCategory().getName());
			model.addAttribute("storiesInCompetitionCategory", storiesInCompetitionCategory);		
		}
	}	

}
