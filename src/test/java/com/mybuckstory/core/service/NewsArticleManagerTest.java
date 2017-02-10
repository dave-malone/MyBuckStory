package com.mybuckstory.core.service;



import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.common.Constants;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.NewsArticle;
import com.mybuckstory.util.UriUtil;

public class NewsArticleManagerTest extends AbstractSecureContextTest {

	protected NewsArticleService newsArticleService;
	
	public void testAddUri(){
		List<NewsArticle> articles = newsArticleService.findAll();
		
		for(NewsArticle news : articles){
			if(StringUtils.isEmpty(news.getUri())){
				String uri = UriUtil.buildPath(Constants.NEWS_ARTICLES, news.getDateCreated(), news.getTitle());
				int count = 1;
				while(newsArticleService.uriInUse(uri)){
					uri = UriUtil.buildPath(Constants.NEWS_ARTICLES, news.getDateCreated(), news.getTitle() + count++);
				}
				news.setUri(uri);
				newsArticleService.update(news);
			}
		}
	}
	
	public void testCreateArticle() throws Exception{
		NewsArticle article = new NewsArticle();
		article.setTitle("Testing news articles - article number ");
		article.getCategories().add(categoryService.createOrFind("Hunting"));
		article.setContent(LOREM_IPSUM);
		Image image = getTestImage(".jpg", newsArticleService.getThumbnailMaxWidth(), newsArticleService.getThumbnailMaxHeight());
		image.setType(Image.Type.NEWS_ARTICLE.toString());
		article.setImage(image);
		article.setWebsite("http://www.cnn.com");
		
		newsArticleService.create(article);
	}	

}
