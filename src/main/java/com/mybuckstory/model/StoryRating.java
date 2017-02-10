package com.mybuckstory.model;


public class StoryRating extends AbstractAuditable{

	private StoryRatingPK PK;
	private Integer value;	
	
	public Integer getValue(){
		return value;
	}
	public void setValue(Integer value){
		this.value = value;
	}
	public StoryRatingPK getPK(){
		return PK;
	}
	public void setPK(StoryRatingPK pk){
		this.PK = pk;
	}
	
	
	
	
}
