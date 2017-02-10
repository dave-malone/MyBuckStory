package com.mybuckstory.web.dwr;

import com.mybuckstory.model.Image;

public class ImageClient extends AbstractMbsClient{
	
	public void likeImage(Long imageId){
		if(imageId == null){
			throw new IllegalArgumentException("imageId may not be null");
		}
		
		getImageManager().like(imageId);
	}
	
	public void deleteImage(Long imageId){
		if(imageId == null){
			throw new IllegalArgumentException("imageId may not be null");
		}
		
		Image image = getImageManager().getById(imageId);
		
		if(image == null){
			throw new IllegalArgumentException(imageId + "is not a valid image id");
		}
		
		getImageManager().delete(image);
	}
	

}
