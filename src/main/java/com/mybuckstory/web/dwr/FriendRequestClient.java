package com.mybuckstory.web.dwr;

import com.mybuckstory.model.User;

public class FriendRequestClient extends AbstractMbsClient{
	
	public String sendRequest(Long toId){
		logger.debug("sending friend request to " + toId);
		String responseMessage = "Failed to send friend request";
		try{
			User currentUser = getCurrentUser();
			
			if(getFriendRequestManager().friendRequestAlreadySent(toId, currentUser.getId())){
				responseMessage = "This friend has sent you a friend request.  Please check your inbox";
			}else{
				if(!getFriendRequestManager().friendRequestAlreadySent(currentUser.getId(), toId)){
					if(getFriendRequestManager().sendRequest(toId)){
						responseMessage = "Friend request sent";
					}	
				}else{
					responseMessage = "You have already sent this person a friend request";
				}
			}
		}catch(Exception e){
			logger.warn(e, e);
		}
		logger.debug(responseMessage);
		return responseMessage;
	}
	
	public boolean respondToRequest(Long requestId, boolean accept){
		logger.debug("Responding to friend request");
		logger.debug("Request id: " + requestId);
		logger.debug("Accepted? " + accept);
		try{
			getCurrentUser();
			getFriendRequestManager().respondToRequest(requestId, accept);
			return true;
		}catch(Exception e){
			logger.warn("Friend request response failed", e);
		}
		return false;
	}
	
	public boolean removeFriend(Long friendId){
		logger.debug("Removing friend");
		logger.debug("Friend id: " + friendId);
		try{
			getCurrentUser();
			getFriendRequestManager().removeFriend(friendId);
			return true;
		}catch(Exception e){
			logger.warn("Failed to remove friend", e);
		}
		return false;
	}

}
