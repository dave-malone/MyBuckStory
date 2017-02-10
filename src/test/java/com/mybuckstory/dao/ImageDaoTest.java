package com.mybuckstory.dao;

import java.io.IOException;
import java.util.List;

import com.mybuckstory.model.Image;

public class ImageDaoTest extends AbstractMbsDaoTest {

	
	
	public void testFindAll(){
		List all = imageDao.findAll();
		assertNotNull(all);
		logger.debug("Find all found " + all.size());
	}
	
	public void testCreateImage() throws IOException{		
		Image img = new Image();
		img.setContent(getFile("test/com/mybuckstory/dao/bears.jpg"));
		img.setTitle("Da Bears");
		
		imageDao.save(img);
		assertNotNull(img.getId());		
	}
	
	public void testGetImageAsStream() throws Exception{
		testCreateImage();
		
		logger.debug("Total Memory: " + getTotalMemoryInMB());
		logger.debug("Initial Free Memory: " + getFreeMemoryInMB());
		List<Image> images = imageDao.findAll();
		logger.debug("Free Memory after getting all images in List: " + getFreeMemoryInMB());
		assertNotNull(images);
		assertFalse(images.isEmpty());
		logger.debug("Found " + images.size() + " images");
		for(Image image : images){		
			imageDao.refresh(image);
			logger.debug("Image: " + image.getTitle());
			logger.debug("Input Stream: " + image.getInputStream());			
			logger.debug("Free Memory After getting input stream: " + getFreeMemoryInMB());
			logger.debug("Bytes: " + image.getBytes());
			logger.debug("Free Memory After getting byte[]: " + getFreeMemoryInMB());
		}
		
		logger.debug("Final Free Memory: " + getFreeMemoryInMB());
	}
	
}