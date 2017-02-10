package com.mybuckstory.core.service;

import java.util.List;

import com.mybuckstory.model.Message;

public class MessageManagerTest extends AbstractSecureContextTest{

	protected MessageService messageService;
	protected UserService userService;
	
	public void testCreateMessage(){
		Message message = makeMessage();
		
		
		Long id = messageService.create(message);
		assertNotNull(id);
	}

	private Message makeMessage(){
		Message message = new Message();
		
		message.setContent("This is a test message");
		message.setSubject("Subject: Test message");
		message.setTo(userService.findByName(admin_username));
		return message;
	}
	
	public void testGetMessages(){
		List<Message> messages = messageService.getMessages(userService.findByName(admin_username), 0, 100);
		assertNotNull(messages);		
	}
	
	public void testReply(){
		Message original = makeMessage();
		
		Long id = messageService.create(original);
		assertNotNull(id);		
		assertTrue(original.isMostRecent());
		
		for(int i = 0; i < 10; i++){
			Message response = new Message();		
			response.setContent("This is a test response");		
			response.setTo(userService.findByName(admin_username));
			
			
			Long replyId = messageService.reply(original, response);
			assertNotSame(original.getId(), response.getId());
			
			assertNotNull(replyId);
			assertFalse(original.isMostRecent());
			assertTrue(response.isMostRecent());
			assertFalse(response.getSubject().startsWith("RE: RE: "));
			logger.debug(replyId);
			original = response;
		}
	}
	
	public void testReplyLongMessage(){
		Message original = makeMessage();
		
		Long id = messageService.create(original);
		assertNotNull(id);		
		assertTrue(original.isMostRecent());
		
		for(int i = 0; i < 10; i++){
			Message response = new Message();		
			response.setContent("This is a test response");		
			response.setTo(userService.findByName(admin_username));
			
			
			Long replyId = messageService.reply(original.getId(), response);
			assertNotSame(original.getId(), response.getId());
			
			assertNotNull(replyId);
			assertFalse(original.isMostRecent());
			assertTrue(response.isMostRecent());
			assertFalse(response.getSubject().startsWith("RE: RE: "));
			logger.debug(replyId);
			original = response;
		}
	}

}
