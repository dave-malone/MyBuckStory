package com.mybuckstory.core.service;

import java.util.List;

import com.mybuckstory.model.Comment;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.WallPost;

public class MemberFeedManagerTest extends AbstractSecureContextTest {

	protected MemberFeedService memberFeedService;
	protected UserService userService;
	
	public MemberFeedManagerTest(){
		setDefaultRollback(false);
		setPopulateProtectedVariables(true);		
	}
	
	public void testGetMemberFeed() {
		int max = 20;
		
		List<MemberFeedItem> items = memberFeedService.getMemberFeed(1L, 0, max);
		
		assertNotNull(items);
		assertFalse(items.isEmpty());
		assertTrue(items.size() <= max);
		
		logger.debug("Found " + items.size() + " member feed items for the current user");
	}

	public void testCreateWallPostComment(){
		WallPost post = new WallPost();
		post.setTarget(userService.getById(1L).getProfile());
		post.setMessage("This is a test of the emergency wall post system");
		Comment comment = new Comment();
		comment.setText("If this were an actual emergency, you'd be screwed");
		
		MemberFeedItem item = new MemberFeedItem();
		item.setWallPostComment(post, comment);
		memberFeedService.create(item);
		memberFeedService.refresh(item);
		assertNotNull(item.getId());
		assertEquals(MemberFeedItem.Type.WALL_POST_COMMENT.toString(), item.getType());
		
	}
	
}
