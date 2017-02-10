package com.mybuckstory.model;


public class Ad extends AbstractSingleImageContainer {

	private String name;
	private String website;
	private boolean enabled;
	private Integer rank;
	
	private String rawCode;	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getWebsite() {
		return website;
	}



	public void setWebsite(String website) {
		this.website = website;
	}


	public int compareTo(Object o) {
		if(o == this){
			return 0;
		}
		
		if(!(o instanceof Ad)){
			return -1;
		}
		
		Ad ad = (Ad)o;
		
		if(ad.rank != null && this.rank != null){
			return ad.rank.compareTo(this.rank);
		}else if(ad.rank == null && this.rank != null){
			return -1;
		}else if(ad.rank != null && this.rank == null){
			return 1;
		}
		
		return 0;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public Integer getRank() {
		return rank;
	}



	public void setRank(Integer rank) {
		this.rank = rank;
	}



	public String getRawCode() {
		return rawCode;
	}



	public void setRawCode(String rawCode) {
		this.rawCode = rawCode;
	}

	

	
}
