package com.mybuckstory.core.service;

import com.mybuckstory.model.Comment;
import com.mybuckstory.model.FriendRequest;
import com.mybuckstory.model.Message;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.User;



public class EmailServiceIntegrationTest extends AbstractSecureContextTest {

	protected EmailService emailService;
	protected UserService userService;
	
		
	private Story getTestStory(){
		Story story = new Story();
		story.setCreatedBy(getTestUser());
		return story;
	}

	private Comment getTestComment(){
		Comment comment = new Comment();		
		comment.setCreatedBy(getTestUser());		
		return comment;
	}

	
	
	private FriendRequest getTestFriendRequest(){
		FriendRequest request = new FriendRequest();		
		request.setTo(getTestUser());
		request.setCreatedBy(getTestUser());		
		return request;
	}

	
	private Message getTestMessage(){
		Message message = new Message();		
		message.setTo(getTestUser());
		message.setCreatedBy(getTestUser());		
		return message;
	}

	

	private User getTestUser(){
		final User user = new User();
		user.setUsername("avidmalone@gmail.com");
		user.getProfile().setFirstName("Dave");
		user.getProfile().setLastName("Malone");
		return user;
	}
	

//	private void sendMessageToUser(final User user){
//		MimeMessagePreparator preparator = new MimeMessagePreparator() {
//		     public void prepare(MimeMessage mimeMessage) throws Exception {
//		        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//		        message.setTo(user.getUsername());
//		        
//		        String from = "MyBuckStory<avidmalone@gmail.com>";
//		        
//		        message.setFrom(from); // could be parameterized...
//		        message.setReplyTo("MyBuckStory<admin@mybuckstory.com>");
//		        message.setSubject("Win Up To $500 for Your Hunting & Fishing Stories");
//		        
//		        StringBuffer buffer = new StringBuffer();
//		    
//
//		        buffer.append("Dear " + user.getProfile().getFirstName() + ", ")
//		        .append("<br />")
//		        .append("  As a token of our appreciation for your support, we would like to reward you with up to $500 for posting your hunting and fishing stories to our homepage.")
//		        .append("<br /><br />")
//		        .append("  Whoever writes a story and publishes it to our homepage is eligible for this competition!  Simply log into your account, click the &quot;Write A Story&quot; link in the upper right corner and brag your way into a cash and prizes.  Hunting season is here, so get out their and bag some game!")
//		        .append("<br /><br />")
//		        .append("  The MyBuckStory Staff will review all of the stories published to the homepage from September 15th through December 31st and announce the Winner of the competition and their Story on January 5th.")
//		        .append("<br /><br />")
//		        .append("Click <a href=\"http://www.mybuckstory.com/stories/2009/September/15/Win_500_for_Your_Hunting_Fishing_Stories/\">this link</a> for Competition Rules and Story Judging Formula!")
//		        .append("<br /><br />")
//		        .append("Now get out their and earn your Bragging Rights!")
//		        .append("<br /><br />")
//		        .append("Thanks, and Good Luck!")
//		        .append("<br />")
//		        .append("  The MyBuckStory Team");
//		        
//		        message.setText(buffer.toString(), true);
//		     }
//		};
//		emailService.getSender().send(preparator);
//	}

}
