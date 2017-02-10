package com.mybuckstory.model;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractSingleImageContainer extends AbstractAuditable implements SingleImageContainer {

	protected Image image;
	protected MultipartFile file;
	protected String imageType;
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) throws IOException {
		this.file = file;
		if(containsNewlyUploadedImage()){
			this.image = new Image(file);			
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean containsNewlyUploadedImage() throws IOException{
		return (getFile() != null && getFile().getBytes() != null && getFile().getBytes().length > 0);		
	}

	public void setImageType(String imageType){
		this.imageType = imageType;		
	}

	public String getImageType(){
		return this.imageType;
	}

}