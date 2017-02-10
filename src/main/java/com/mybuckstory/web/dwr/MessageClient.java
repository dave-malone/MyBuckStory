package com.mybuckstory.web.dwr;


public class MessageClient extends AbstractMbsClient{
	
	public boolean deleteMessage(Long messageId){
		boolean success = false;
		logger.debug("marking message with id " + messageId + " as deleted");
		try{
			getMessageManager().delete(messageId);
			success = true;
		}catch(Exception e){
			logger.warn("an error occurred when deleting message with id " + messageId, e);
		}
		
		return success;
	}
	
	public boolean deleteSentMessage(Long messageId){
		boolean success = false;
		logger.debug("marking sent message with id " + messageId + " as deleted");
		try{
			getMessageManager().deleteFromSentMessages(messageId);
			success = true;
		}catch(Exception e){
			logger.warn("an error occurred when deleting message with id " + messageId, e);
		}
		
		return success;
	}

}
