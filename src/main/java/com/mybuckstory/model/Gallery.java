package com.mybuckstory.model;

import java.util.HashSet;
import java.util.Set;

public class Gallery extends AbstractAuditable {

	private String title;
	private Image cover;
	private Set<Image> images = new HashSet<Image>();
	
	public Image getCover() {
		return cover;
	}
	public void setCover(Image cover) {
		this.cover = cover;
	}
	public Set<Image> getImages() {
		return images;
	}
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
