package com.mybuckstory.core.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Album;
import com.mybuckstory.model.Image;

@Service
public class AlbumService extends GenericMbsService<Album> {

	public AlbumService(){
		this(null);
	}
	
	@Autowired
	public AlbumService(@Qualifier("albumDao") GenericHibernateDao<Album, Long> albumDao){
		super(Album.class, albumDao);
	}
	
	
	public List<Album> findByAuthor(Long authorId){
		Criteria baseCriteria = getCurrentSession().createCriteria(Album.class)
				.addOrder(Order.desc("dateCreated"))
				//.setProjection(Projections.projectionList().add(Projections.property("name")).add(Projections.id()))
				.setCacheable(true);
		
		baseCriteria.createCriteria("createdBy")
			.add(Restrictions.eq("id", authorId))	
			.setFetchMode("profile", FetchMode.LAZY);	
//		
//		
//		
//		baseCriteria.setFetchMode("images", FetchMode.EAGER);
//		baseCriteria.createCriteria("images").setFetchMode("resizedCopies", FetchMode.LAZY);
		baseCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")		
		List<Album> albums = (List<Album>)baseCriteria.list();		
		return albums;
	}
		
	public Map<String, Object> getGalleryDisplayModel(Image currentImage, Collection<Image> images){
		logger.debug("Images: " + images);
		
		Long imageId = currentImage.getId();
		Long nextImageId = null;
		Long prevImageId = null;
		Long firstImageId = null;
		Long lastImageId = null;
		
		Integer imageIndex = 1;
		
		boolean foundCurrentImage = false;
		
		for(Iterator<Image> iter = images.iterator(); iter.hasNext();){
			Image image = iter.next();
			if(firstImageId == null){
				firstImageId = image.getId();
			}else if(lastImageId == null && !iter.hasNext()){
				lastImageId = image.getId();
			}			
		}
		
		for(Image image : images){
			if(!foundCurrentImage){
				if(currentImage.getId() != image.getId()){
					imageIndex++;
					prevImageId = image.getId();
				}else{
					foundCurrentImage = true;
				}
			}else{
				if(currentImage.getId() != image.getId()){
					nextImageId = image.getId();
				}
				break;
			}
		}
		
//		if(prevImageId != null){			
//			if((lastImageId != nextImageId) && (lastImageId != imageId)){
//				prevImageId = lastImageId;
//			}
//		}
//		
//		if(nextImageId != null){
//			if((firstImageId != prevImageId) && (firstImageId != imageId)){
//				nextImageId = firstImageId;
//			}
//		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("prevImageId", prevImageId);
		model.put("nextImageId", nextImageId);
		model.put("image", currentImage);
		model.put("id", imageId);
		model.put("imageIndex", imageIndex);
		model.put("imageTotal", images.size());
				
		return model;
	}
	
}
