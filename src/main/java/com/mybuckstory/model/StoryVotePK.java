package com.mybuckstory.model;

import java.io.Serializable;

public class StoryVotePK implements Serializable{

	private Long storyId;
	private Long voterId; 
	
	public StoryVotePK(){}
	
	public StoryVotePK(Long storyId, Long voterId) {
		this.storyId = storyId;
		this.voterId = voterId;
	}

	public Long getStoryId(){
		return storyId;
	}
	public void setStoryId(Long storyId){
		this.storyId = storyId;
	}
	public Long getVoterId(){
		return voterId;
	}
	public void setVoterId(Long voterId){
		this.voterId = voterId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((storyId == null) ? 0 : storyId.hashCode());
		result = prime * result + ((voterId == null) ? 0 : voterId.hashCode());
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
		StoryVotePK other = (StoryVotePK) obj;
		if (storyId == null) {
			if (other.storyId != null)
				return false;
		} else if (!storyId.equals(other.storyId))
			return false;
		if (voterId == null) {
			if (other.voterId != null)
				return false;
		} else if (!voterId.equals(other.voterId))
			return false;
		return true;
	}	
	
}
