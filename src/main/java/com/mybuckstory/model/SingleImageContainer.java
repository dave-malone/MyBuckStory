package com.mybuckstory.model;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface SingleImageContainer {

	void setImageType(String imageType);
	
	String getImageType();
	
	Image getImage();

	void setImage(Image image);

	MultipartFile getFile();
	
	boolean containsNewlyUploadedImage() throws IOException;

}