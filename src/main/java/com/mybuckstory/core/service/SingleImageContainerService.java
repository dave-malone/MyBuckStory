package com.mybuckstory.core.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.SingleImageContainer;

@Transactional
public abstract class SingleImageContainerService<T extends SingleImageContainer> extends GenericMbsService<T> {

	protected final Integer thumbnailMaxHeight;
	protected final Integer thumbnailMaxWidth;
	
	protected SingleImageContainerService(Class<T> type, GenericHibernateDao<T, Long> hibernateDao, Integer thumbnailMaxWidth, Integer thumbnailMaxHeight) {
		super(type, hibernateDao);		
		this.thumbnailMaxHeight = thumbnailMaxHeight;
		this.thumbnailMaxWidth = thumbnailMaxWidth;
	}

	@Override
	public Long create(T obj) {		
		saveImage(obj);		
		return super.create(obj);
	}	
	

	private void saveImage(T obj) {
		try{
			if(obj.containsNewlyUploadedImage()){
				logger.debug("setting image type to " + obj.getImageType());
				Image image = obj.getImage();
				image.setType(obj.getImageType());
				Long imageId = getBean(ImageService.class).create(image);
				image.setId(imageId);				
				obj.setImage(image);			
			}	
		}catch(IOException e){
			logger.error("IOException when checking containsNewlyUploadedImage", e);
		}catch(Exception e){
			logger.error("an unexpected exception occurred while attempting to save the image related to a SingleImageContainer of type " + (obj != null ? obj.getClass() : "<SingleImageContainer was null>"), e);
		}
	}

	@Override
	public void saveOrUpdate(T obj) {
		saveImage(obj);
		super.saveOrUpdate(obj);
	}

	@Override
	public void update(T obj) {
		saveImage(obj);
		super.update(obj);
	}
	
	public int getThumbnailMaxHeight() {
		return thumbnailMaxHeight;
	}

	public int getThumbnailMaxWidth() {
		return thumbnailMaxWidth;
	}

}
