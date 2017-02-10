package com.mybuckstory.core.service;

import java.util.List;

import com.mybuckstory.model.FriendRequest;
import com.mybuckstory.model.User;


public class FriendRequestManagerTest extends AbstractSecureContextTest {

	protected FriendRequestService friendRequestService;
	protected UserService userService;	
	
	public void testCreate(){
		User user = userService.findAll().get(0);
		FriendRequest request = new FriendRequest();
		request.setTo(user);
		
		Long id = friendRequestService.create(request);
		assertNotNull(id);
		
		friendRequestService.refresh(request);
		assertFalse(request.isAccepted());
		assertFalse(request.isResponded());
	}
	
	public void testGetUnAnsweredRequests() {
		User user = userService.findAll().get(0);
		List<FriendRequest> requests = friendRequestService.getUnAnsweredRequests(user);
		assertNotNull(requests);
	}

	public void testFriendRequestAlreadySent() {
		User user = userService.findAll().get(0);
		FriendRequest request = new FriendRequest();
		request.setTo(user);
		
		Long id = friendRequestService.create(request);
		assertNotNull(id);
		assertFalse(request.isAccepted());
		assertFalse(request.isResponded());
		
		friendRequestService.refresh(request);
		
		assertTrue(friendRequestService.friendRequestAlreadySent(request.getTo().getId(), request.getCreatedBy().getId()));		
	}

	public void testSendRequest() {
		User user = userService.findAll().get(0);
		boolean result = friendRequestService.sendRequest(user.getId());
		assertTrue(result);
	}
	
	public void testSendRequestNullIdThrowsIllegalArgException() {
		try{
			friendRequestService.sendRequest(null);
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
		}
		
	}

	public void testRemoveFriendNullIdThrowsIllegalArgException() {
		try{
			friendRequestService.removeFriend(null);
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	public void testAcceptFriendRequest() {
		User user = userService.findAll().get(0);
		FriendRequest request = new FriendRequest();
		request.setTo(user);
		
		Long id = friendRequestService.create(request);
		assertNotNull(id);
		assertFalse(request.isAccepted());
		assertFalse(request.isResponded());
		
		friendRequestService.refresh(request);
		
		friendRequestService.respondToRequest(request.getId(), true);
		request = friendRequestService.getById(request.getId());
		assertTrue(request.isAccepted());
		assertTrue(request.isResponded());
	}

}
