package com.mybuckstory.web.dwr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.NonUniqueObjectException;

import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.Like;
import com.mybuckstory.model.User;
import com.mybuckstory.model.WallPost;



public class WallClient extends AbstractMbsClient{
	
	private static final String DATE_FORMAT = "MM/dd/yyyy 'at' hh:mma";
	private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	
	public Map<String, Object> like(Long wallPostId){
		Map<String, Object> response = new HashMap<String, Object>();
		
		try{
			logger.debug("'liking' wall post with id " + wallPostId);
			Like like = getWallPostManager().like(wallPostId);
			
			response.put("dateCreated", DATE_FORMATTER.format(like.getDateCreated()));
			response.put("createdById", like.getPK().getCreatedBy().getId());		
			response.put("createdByFullName", like.getPK().getCreatedBy().getProfile().getFullName());
			
			return response;
		}catch(Exception e){
			if(e.getCause() instanceof NonUniqueObjectException){
				logger.info("User has most likely already liked this wall post " + wallPostId + "; exception: " + e.getMessage());
			}else{
				logger.error("an error occurred 'liking' wall post with id " + wallPostId, e);
			}
			return null;
		}
	}
	
	public Map<String, Object> comment(Long wallPostId, String text){
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(text)){
			logger.debug("commenting on wall post with id " + wallPostId);
			logger.debug("comment: " + text);
			Comment comment = new Comment(text);
			getWallPostManager().comment(wallPostId, comment);
			
			response.put("text", text);
			response.put("dateCreated", DATE_FORMATTER.format(comment.getDateCreated()));
			response.put("createdById", comment.getCreatedBy().getId());		
			response.put("createdByFullName", comment.getCreatedBy().getProfile().getFullName());
		}
		
		return response;
	}
	
	public Map<String, Object> post(Long targetProfileId, String text){
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(text)){
			logger.debug("creating new wall post");
			logger.debug("target profile id: " + targetProfileId);
			logger.debug("comment: " + text);
			User user = getUserManager().getById(targetProfileId);
			
			WallPost post = new WallPost();
			post.setTarget(user.getProfile());
			post.setMessage(text);
			getWallPostManager().create(post);
			user.getProfile().getWallPosts().add(post);
			
			getUserManager().update(user);
			
			
			response.put("id", post.getId());
			response.put("message", text);		
			response.put("dateCreated", DATE_FORMATTER.format(post.getDateCreated()));
			response.put("createdById", post.getCreatedBy().getId());
			Image createdByProfilePic = post.getCreatedBy().getProfile().getImage();
			response.put("createdByImageId", createdByProfilePic != null ? createdByProfilePic.getId() : null);
			response.put("createdByFullName", post.getCreatedBy().getProfile().getFullName());
		}
		
		return response;
	}

}
