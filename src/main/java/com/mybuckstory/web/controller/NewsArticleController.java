package com.mybuckstory.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.core.service.NewsArticleService;
import com.mybuckstory.model.Category;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.NewsArticle;
import com.mybuckstory.web.command.EditNewsArticleCommand;
import com.mybuckstory.web.editor.CategorySortedSetEditor;

@Controller
public class NewsArticleController {

	@Autowired
	private NewsArticleService newsArticleService;
	@Autowired
	private CategoryService categoryService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Set.class, "categories", new CategorySortedSetEditor(this.categoryService));
	}

	@ModelAttribute("categories")
	public List<Category> populateCategories(){
		return categoryService.getParentCategories();
	}
	
	@RequestMapping(value="/newsArticle/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception{
		ModelAndView mav = new ModelAndView("create.article.dialog");
		mav.addObject("command", new NewsArticle());
		return mav;
	}
	
	@RequestMapping(value="/articles/{year}/{month}/{day}/{title}/*", method=RequestMethod.GET)
	public ModelAndView show(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day, @PathVariable("title") String title){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showNewsArticle");
		
		String uri = String.format("/articles/%s/%s/%s/%s/", year, month, day, title);
		
		NewsArticle newsArticle = newsArticleService.findByUri(uri);
		mav.addObject("article", newsArticle);
		
		return mav;
	}
	
	@RequestMapping(value="/news.html", method=RequestMethod.GET)
	public ModelAndView legacyList(@RequestParam(value="category", required=false) String category, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max) {
		ModelAndView mav = new ModelAndView("listNewsArticle");
		mav.addObject("newsArticles", newsArticleService.getNewsByCategory(start, max, category));
		mav.addObject("totalNewsArticles", newsArticleService.getNewsByCategoryCount(category));
		return mav;
	}
	
	@RequestMapping(value="/newsArticle/manage", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, @RequestParam(value="sort", required=false) String sort, @RequestParam(value="order", required=false) String order) {
		ModelAndView mav = new ModelAndView("manageNewsArticles");
		mav.addObject("newsArticles", newsArticleService.findAllPaginated(start, max));
		mav.addObject("totalNewsArticles", newsArticleService.getCount());
		return mav;
	}
	
	@RequestMapping(value="/newsArticle/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) throws Exception{
		ModelAndView mav = new ModelAndView("formNewsArticle");
		mav.addObject("command", new EditNewsArticleCommand(newsArticleService.getById(id)));
		return mav;
	}
	
	@RequestMapping(value="/newsArticle/show/{id}", method=RequestMethod.GET)
	public ModelAndView getById(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showNewsArticle");		
		mav.addObject("newsArticle", newsArticleService.getById(id));
		
		return mav;		
	}

	@RequestMapping(value="/newsArticle/save", method = RequestMethod.POST)
	public ModelAndView save(NewsArticle newsArticle, HttpServletResponse response) throws Exception{
		newsArticleService.create(newsArticle);
		ModelAndView mav = new ModelAndView("create.article.success.dialog");
		mav.addObject("command", newsArticle);
		return mav;		
	}

	@RequestMapping(value="/newsArticle/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id){
		NewsArticle newsArticle = newsArticleService.getById(id);
		newsArticleService.delete(newsArticle);
		return "redirect:list";
	}
	
	@RequestMapping(value="/newsArticle/comment/{id}", method = RequestMethod.POST)
	public void comment(@PathVariable("id") Long id, Comment comment, HttpServletResponse response) throws Exception{
		if(StringUtils.isNotBlank(comment.getText())){
			NewsArticle newsArticle = newsArticleService.comment(id, comment);
			response.sendRedirect(newsArticle.getUri());	
		}else{
			NewsArticle newsArticle = newsArticleService.getById(id);
			response.sendRedirect(newsArticle.getUri());
		}
	}

	@RequestMapping(value="/newsArticle/update", method = RequestMethod.POST)
	public void update(EditNewsArticleCommand command, HttpServletResponse response) throws Exception{
		NewsArticle newsArticle = newsArticleService.getById(command.getId());
		newsArticle.setCategories(command.getCategories());
		newsArticle.setContent(command.getContent());
		newsArticle.setFile(command.getFile());
		newsArticle.setMetaDescription(command.getMetaDescription());
		newsArticle.setMetaKeywords(command.getMetaKeywords());
		newsArticle.setTitle(command.getTitle());
		newsArticle.setVersion(command.getVersion());
		
		newsArticleService.update(newsArticle);
		response.sendRedirect(newsArticle.getUri());
	}

}
