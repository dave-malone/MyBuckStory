package com.mybuckstory.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class WallPost extends AbstractAuditable {

	private String message;
	private Profile target;
	private SortedSet<Like> likes = Collections.synchronizedSortedSet(new TreeSet<Like>());
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());
	
	public WallPost(){}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Profile getTarget() {
		return target;
	}

	public void setTarget(Profile target) {
		this.target = target;
	}

	public SortedSet<Like> getLikes() {
		return likes;
	}

	public void setLikes(SortedSet<Like> likes) {
		this.likes = likes;
	}

	public SortedSet<Comment> getComments() {
		return comments;
	}

	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}
	
	public int compareTo(Object obj) {
		if(!(obj instanceof WallPost)){
			return -1;
		}
		
		WallPost that = (WallPost)obj;
		
		if(that.dateCreated != null && this.dateCreated != null){
			return that.dateCreated.compareTo(this.dateCreated);
		}else if(that.dateCreated == null && this.dateCreated != null){
			return -1;
		}else if(that.dateCreated != null && this.dateCreated == null){
			return 1;
		}
		
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		
		if(!(obj instanceof WallPost)){
			return false;
		}
		
		WallPost that = (WallPost)obj;
		
		return that.id.equals(this.id);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	
	
}
