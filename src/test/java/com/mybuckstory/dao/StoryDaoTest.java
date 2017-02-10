package com.mybuckstory.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.mybuckstory.model.Image;
import com.mybuckstory.model.Story;

public class StoryDaoTest extends AbstractMbsDaoTest {
	
	public void testCreate(){
		Story story = new Story();
		story.setTitle("Test Story " + new Date());
		story.setText("This is the actual story");
		Long id = storyDao.save(story);
		assertNotNull(id);
		assertNotNull(story.getId());
		assertNotNull(story.getDateCreated());
		assertNotNull(story.getLastUpdated());
		assertNotNull(story.getCreatedBy());
		assertNotNull(story.getLastUpdatedBy());
	}
	
	public void testLoadById(){
		Story story = new Story();
		story.setTitle("Test Story " + new Date());
		story.setText("This is the actual story");
		storyDao.save(story);
		assertNotNull(story.getId());
		assertNotNull(story.getDateCreated());
		assertNotNull(story.getLastUpdated());
		assertNotNull(story.getCreatedBy());
		assertNotNull(story.getLastUpdatedBy());
		
		assertNotNull(storyDao.loadById(story.getId()));
	}
	
	public void testGetById(){
		Story story = new Story();
		story.setTitle("Test Story " + new Date());
		story.setText("This is the actual story");
		storyDao.save(story);
		assertNotNull(story.getId());
		assertNotNull(story.getDateCreated());
		assertNotNull(story.getLastUpdated());
		assertNotNull(story.getCreatedBy());
		assertNotNull(story.getLastUpdatedBy());
		
		assertNotNull(storyDao.getById(story.getId()));
	}
	
	public void testDelete(){
		Story story = new Story();
		story.setTitle("Test Story " + new Date());
		story.setText("This is the actual story");
		storyDao.save(story);
		assertNotNull(story.getId());
		assertNotNull(story.getDateCreated());
		assertNotNull(story.getLastUpdated());
		assertNotNull(story.getCreatedBy());
		assertNotNull(story.getLastUpdatedBy());
		
		assertNotNull(storyDao.getById(story.getId()));
		
		storyDao.delete(story);
		
		assertNull(storyDao.getById(story.getId()));
	}
	
	
	public void testFindAll(){
		List<Story> all = storyDao.findAll();
		assertNotNull(all);
		logger.debug("Find all found " + all.size());
	}
	
	public void testCreateWithImage() throws IOException{
		Story story = new Story();
		story.setTitle("Test Story " + new Date());
		story.setText("This is the actual story");
		
		Image img = new Image();
		img.setContent(getFile("test/com/mybuckstory/dao/bears.jpg"));
		img.setTitle("Da Bears");
		
		story.setImage(img);
		
		storyDao.save(story);
		
		assertNotNull(story.getId());
		assertNotNull(story.getImage());
		assertNotNull(story.getImage().getId());
		assertNotNull(story.getDateCreated());
		assertNotNull(story.getLastUpdated());
		assertNotNull(story.getCreatedBy());
		assertNotNull(story.getLastUpdatedBy());
	}
	
}
