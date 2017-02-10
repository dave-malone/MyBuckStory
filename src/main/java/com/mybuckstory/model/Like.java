package com.mybuckstory.model;

public class Like extends AbstractAuditable {

	private LikePK PK;
	
	public Like(){}
	
	public Like(LikePK pk){
		this.PK = pk;
	}

	public LikePK getPK() {
		return PK;
	}

	public void setPK(LikePK pK) {
		PK = pK;
	}
	
}
