package com.mybuckstory.model;


public class StoryVote extends AbstractAuditable{

	private StoryVotePK pk;	
	
	public StoryVote(){}
	
	public StoryVote(StoryVotePK pk){
		this.pk = pk;
	}

	public StoryVotePK getPk() {
		return this.pk;
	}

	public void setPk(StoryVotePK pk) {
		this.pk = pk;
	}
	
}
