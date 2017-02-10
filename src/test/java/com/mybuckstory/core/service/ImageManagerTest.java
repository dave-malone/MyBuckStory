package com.mybuckstory.core.service;

import java.io.IOException;
import java.util.List;

import com.mybuckstory.model.Image;
import com.mybuckstory.model.MimeType;

public class ImageManagerTest extends AbstractSecureContextTest {

	protected ImageService imageService;

	
	public void testCreateImage() throws IOException{		
		Image img = new Image();
		img.setContent(getFile("test/com/mybuckstory/dao/bears.jpg"));
		img.setTitle("Da Bears");
		img.setType(Image.Type.STORY.toString());
		img.setMimeType(MimeType.JPEG);
		
		imageService.create(img);
		assertNotNull(img.getId());		
	}
	
	public void testFindProfileImages(){
		Long authorId = new Long(1l);
		logger.debug("Finding by author");
		List<Image> images = imageService.findProfileImages(authorId);
		assertNotNull(images);
		assertEquals(6, images.size());
	}
	
}