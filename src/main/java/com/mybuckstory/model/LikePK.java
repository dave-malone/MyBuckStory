package com.mybuckstory.model;

import java.io.Serializable;

public class LikePK implements Serializable{

	private WallPost wallPost;
	private User createdBy;
	
	public LikePK(){}
	
	public LikePK(WallPost wallPost, User createdBy) {
		super();
		this.wallPost = wallPost;
		this.createdBy = createdBy;
	}

	public WallPost getWallPost() {
		return wallPost;
	}

	public void setWallPost(WallPost wallPost) {
		this.wallPost = wallPost;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((wallPost == null) ? 0 : wallPost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LikePK other = (LikePK) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (wallPost == null) {
			if (other.wallPost != null)
				return false;
		} else if (!wallPost.equals(other.wallPost))
			return false;
		return true;
	}
	
	
}
