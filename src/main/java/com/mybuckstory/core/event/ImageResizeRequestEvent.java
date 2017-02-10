package com.mybuckstory.core.event;

import org.springframework.context.ApplicationEvent;

public class ImageResizeRequestEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -523513148206511184L;
	protected final Long imageId;
	protected final Integer width;
	protected final Integer height;
	
	
	public ImageResizeRequestEvent(Object source, Long imageId, Integer width, Integer height) {
		super(source);		
		this.imageId = imageId;
		this.width = width;
		this.height = height;
	}


	public Long getImageId() {
		return imageId;
	}


	public Integer getWidth() {
		return width;
	}


	public Integer getHeight() {
		return height;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((imageId == null) ? 0 : imageId.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
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
		ImageResizeRequestEvent other = (ImageResizeRequestEvent) obj;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (imageId == null) {
			if (other.imageId != null)
				return false;
		} else if (!imageId.equals(other.imageId))
			return false;
		if (width == null) {
			if (other.width != null)
				return false;
		} else if (!width.equals(other.width))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ImageResizeRequestEvent [imageId=" + imageId + ", width="
				+ width + ", height=" + height + "]";
	}
	
	
}
