package com.mybuckstory.web.tag;

import javax.servlet.jsp.JspException;

import com.mybuckstory.core.service.ImageService;
import com.mybuckstory.model.Image;

public class ImageUriTag extends MbsTagSupport{
	
	private Long imageId;
	private Integer width;
	private Integer height;
	private Integer maxWidthAndHeight;
	private String defaultImageUri = "/images/noStoryImage.png";
	
	public int doStartTag() throws JspException {
		final ImageService imageService = getWebAppCtx().getBean(ImageService.class);
		
		try {
			final Image image = imageService.getById(imageId);
			
			if(image != null){
				//return the URI for that image
				
				if(maxWidthAndHeight != null){
					width = maxWidthAndHeight;
					height = maxWidthAndHeight;
				}
				
				final String imageUri = getRequest().getContextPath() + "/static" + imageService.getImagePathGenerateIfNecessary(image, width, height);
				
				setAttribute(getId(), imageUri);
			}else{
				setAttribute(getId(), defaultImageUri);
			}
		} catch (Exception e) {
			logger.warn("Failed to write the image URI - falling back to defaultImageUri " + defaultImageUri);
			setAttribute(getId(), defaultImageUri);			
		}

		return SKIP_BODY;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public void setWidth(Integer imageWidth) {
		this.width = imageWidth;
	}

	public void setHeight(Integer imageHeight) {
		this.height = imageHeight;
	}

	public Integer getMaxWidthAndHeight() {
		return maxWidthAndHeight;
	}

	public void setMaxWidthAndHeight(Integer maxWidthAndHeight) {
		this.maxWidthAndHeight = maxWidthAndHeight;
	}

	public void setDefaultImageUri(String defaultImageUri) {
		this.defaultImageUri = defaultImageUri;
	}
	
}
