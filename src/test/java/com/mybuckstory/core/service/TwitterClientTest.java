package com.mybuckstory.core.service;

import com.mybuckstory.model.Category;
import com.mybuckstory.model.Story;

public class TwitterClientTest extends AbstractSecureContextTest {

	protected TwitterService twitterService;

	public void testUpdateStatus() throws Exception {
		String fullUrl = "/stories/test/url";
		
		Story story = new Story();
		story.getCategories().add(new Category("hunting"));
		story.setUri(fullUrl);
		story.setTitle("Test Story");
		story.setText("Testing automated Twitter updates - this will be deleted " + AbstractSecureContextTest.LOREM_IPSUM);
		
		twitterService.updateStatus(story);
	}

}
