package com.mybuckstory.integration;

import java.util.Date;

import com.mybuckstory.core.service.NewsArticleService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.dao.AbstractMbsDaoTest;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.NewsArticle;
import com.mybuckstory.model.Profile;
import com.mybuckstory.model.Role;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.User;

public class IntegrationTests extends AbstractMbsDaoTest {
		
	protected StoryService storyService;
	protected NewsArticleService newsArticleService;
	
	public IntegrationTests(){
		super();
		setDefaultRollback(false);
	}
	
	public void grantUserAuthorities(){		
		Role adminRole = findByName("ROLE_ADMIN");
		
		if(adminRole == null){
			adminRole = new Role("ROLE_ADMIN"); 
			roleDao.save(adminRole);
		}
					
		assertNotNull(adminRole.getId());
		
		Role authorRole = findByName("ROLE_AUTHOR");
		
		if(authorRole == null){
			authorRole = new Role("ROLE_AUTHOR"); 
			roleDao.save(authorRole);
		}
		
		assertNotNull(authorRole.getId());
		
		User user = findByUsername("david_mm8212@msn.com");
		
		if(user == null){
			user = new User();
			user.setProfile(new Profile());
			user.setUsername("dave");
			user.setPassword("password");
			user.getProfile().setFirstName("David");
			user.getProfile().setLastName("Malone");
			user.getProfile().setDob(new Date());		
		}
		
		user.getRoles().add(adminRole);
		user.getRoles().add(authorRole);
		
		userDao.saveOrUpdate(user);		
//		userDao.refresh(user);
		
		assertNotNull(user.getId());
		assertNotNull(user.getProfile());
		assertNotNull(user.getProfile().getId());
		assertFalse(user.getRoles().isEmpty());
		assertTrue(user.getRoles().contains(adminRole));
		assertTrue(user.getRoles().contains(authorRole));				
	}
	
	public void createMultipleStoriesWithTheSameTitle() throws Exception{
		for(int i = 0; i < 10; i++){
			Story story = new Story();
			story.setTitle("Testing stories with URI and Same Title");
			story.getCategories().add(categoryService.createOrFind("Hunting"));
			story.setText(LOREM_IPSUM);
			Image image = getTestImage(".jpg", storyService.getThumbnailMaxWidth(), storyService.getThumbnailMaxHeight());
			image.setType(Image.Type.STORY.toString());
			story.setImage(image);
			Long id  = storyService.create(story);
			assertNotNull(id);
			assertNotNull(story.getId());
			assertNotNull(story.getDateCreated());
			assertNotNull(story.getLastUpdated());
			assertNotNull(story.getCreatedBy());
			assertNotNull(story.getLastUpdatedBy());
		}		
	}
	
	public void testCreateMultipleNewsArticles() throws Exception{
		for(int i = 0; i < 10; i++){
			NewsArticle article = new NewsArticle();
			article.setTitle("Testing news articles - article number " + i);
			article.getCategories().add(categoryService.createOrFind("Hunting"));
			article.setContent(LOREM_IPSUM);
			Image image = getTestImage(".jpg", newsArticleService.getThumbnailMaxWidth(), newsArticleService.getThumbnailMaxHeight());
			image.setType(Image.Type.NEWS_ARTICLE.toString());
			article.setImage(image);
			article.setWebsite("http://www.cnn.com");
			
			newsArticleService.create(article);
		}		
	}
	
}
