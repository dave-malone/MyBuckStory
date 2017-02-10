package com.mybuckstory.core.service;

import java.util.List;

import com.mybuckstory.model.Story;

public class StoryManagerTest extends AbstractSecureContextTest {

	protected StoryService storyService;
	
	public void testGetRecentStories(){
		int max = 1;
		List<Story> stories = storyService.getRecentStories(max);
		assertNotNull(stories);
		assertEquals(1, stories.size());
	}
	
	public void testGetStoriesCount(){
		Long testUserId = 1L;
		Long storyCount = storyService.getStoryCount(testUserId);
		assertNotNull(storyCount);
		logger.debug("User id " + testUserId + " has " + storyCount + " stories");
	}
	
	public void testFindAllStoriesPaginated(){
		Long testUserId = 1L;
		int max = 10;
		int start = 0;
		List<Story> stories = storyService.getStoriesPaginated(testUserId, start, max);
		assertNotNull(stories);
		assertTrue(stories.size() <= max);
		logger.debug("User id " + testUserId + " # stories returned (paginated): " + stories.size());
		for(Story story : stories){
			logger.debug(story.getTitle());
			assertEquals("The id of the author didn't equal the expected test user id", testUserId, story.getCreatedBy().getId());
		}
	}
}
