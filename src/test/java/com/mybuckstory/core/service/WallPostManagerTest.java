package com.mybuckstory.core.service;

import com.mybuckstory.model.Like;
import com.mybuckstory.model.WallPost;

public class WallPostManagerTest extends AbstractSecureContextTest {

	protected WallPostService wallPostService;	

	public void testGetMostRecentStatus() {
		Long userId = 1L;
		WallPost post = wallPostService.getMostRecentStatus(userId);
		assertNotNull(post);
		logger.debug("post date created: " + post.getDateCreated());
		logger.debug("post id: " + post.getId());
		assertEquals(userId, post.getCreatedBy().getId());
		assertEquals(userId, post.getTarget().getId());
	}
	
	public void testLike(){
		Long wallPostId = 33L;
		
		Like like =	wallPostService.like(wallPostId);
		assertNotNull(like);
		assertNotNull(like.getPK());
		assertNotNull(like.getPK().getWallPost());
		assertEquals(wallPostId, like.getPK().getWallPost().getId());
	}

}
