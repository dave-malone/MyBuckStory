/**
 * 
 */
package com.mybuckstory.core.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mortennobel.imagescaling.ProgressListener;
import com.mortennobel.imagescaling.ResampleOp;
import com.mybuckstory.core.event.ImageResizeRequestEvent;
import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.dao.GenericHibernateDao;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.ImageLike;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.MimeType;

/**
 * @author Dave Malone
 *
 */
@Service
@Transactional
public class ImageService extends GenericMbsService<Image> implements ApplicationListener<ImageResizeRequestEvent> {
	
	public static final String[] NON_PROFILE_IMAGE_TYPES = new String[] {"STORY", "NEWS_ARTICLE", "AD", "AFFILIATE", "GAME", "GALLERY", "BADGE", "contest", "ALBUM"};
	
	public static final Integer DEFAULT_THUMB_MAX_WIDTH = 225;
	public static final Integer DEFAULT_THUMB_MAX_HEIGHT = 225;
	
	private final ApplicationEventService eventService;
	private final File imagesDir;
	private static final ExecutorService imageResizingExecutor = Executors.newFixedThreadPool(25);
	private static final CopyOnWriteArraySet<ImageResizeRequestEvent> imageResizeRequests = new CopyOnWriteArraySet<ImageResizeRequestEvent>();
	
	
	public ImageService(){
		this(null, null, null);
	}
	
	@Autowired
	public ImageService(ApplicationEventService eventService, @Qualifier("imageDao") GenericHibernateDao<Image, Long> dao, @Value("${images.dir}") String imagesDirPath){
		super(Image.class, dao);
		this.imagesDir = imagesDirPath != null ? new File(imagesDirPath) : null;
		this.eventService = eventService;
	}
	
	@Override
	public void update(Image image){
		if(image == null){
			throw new IllegalArgumentException("The [image] argument may not be null");
		}
		
		hibernateDao.update(image);		
		hibernateDao.refresh(image);		
		saveImageToFS(image, null, null);		
		hibernateDao.update(image);
	}
	
	@Override
	public Image getById(Long id) {
		Criteria baseCriteria = getCurrentSession().createCriteria(Image.class)
				.add(Restrictions.idEq(id));

		baseCriteria.setFetchMode("likes", FetchMode.JOIN);
		baseCriteria.setFetchMode("comments", FetchMode.JOIN);
		baseCriteria.setFetchMode("createdBy", FetchMode.JOIN);
		baseCriteria.createCriteria("createdBy").setFetchMode("profile", FetchMode.JOIN);

		return (Image)baseCriteria.uniqueResult();
	}
	
	@Transactional
	public void updateFieldsOnly(Image image){
		if(image == null){
			throw new IllegalArgumentException("The [image] argument may not be null");
		}
		hibernateDao.update(image);
	}	
	
	/**
	 * This system automatically generates thumbnail images for you.  If you want to specify
	 * thumbnail max height and max width dimensions (in pixels) use this method.  Otherwise, 
	 * the system will default to use the DEFAULT_THUMB_MAX_WIDTH and DEFAULT_THUMB_MAX_HEIGHT
	 * @param image
	 * @param thumbMaxWidth
	 * @param thumbMaxHeight
	 * @return
	 */
	@Override
	public Long create(Image image){
		if(image == null){
			throw new IllegalArgumentException("The [image] argument may not be null");
		}
		
		hibernateDao.save(image);		
		hibernateDao.refresh(image);		
		saveImageToFS(image, null, null);		
		hibernateDao.update(image);
		
		//TODO - there needs to be a set of dimensions for images by type that can be used
		//to eagerly create all of the resizes when an image is first uploaded.  That will
		//eliminate the need for this default max width/height which probably isn't being used anyways
		//this.eventService.publish(new ImageResizeRequestEvent(this, image.getId(), width, height));
		
		return image.getId();
	}

	
	

	private void saveImageToFS(Image image, Integer width, Integer height){
		try{
			String imagePath = getImagePath(image, width, height);
			File file = new File(imagesDir, imagePath);
			logger.debug("saving image to " + file.getAbsolutePath());
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			image.setPath(imagePath);
		}catch(Exception e){
			logger.error("an error occurred when attempting to write image to file system", e);
		}
	}
	
	public String getImagePathGenerateIfNecessary(final Image image, final Integer width, final Integer height) throws IOException{
		if(image == null){
			return "";
		}
		
		if((width == null || height == null) || (width == 0 || height == 0)){
			return getImagePath(image, width, height);
		}
		
		final String imagePath = getImagePath(image, width, height);

		this.eventService.publishAsynch(new ImageResizeRequestEvent(this, image.getId(), width, height));		
		
		return imagePath;
	}
	

	@Override
	public void onApplicationEvent(final ImageResizeRequestEvent event) {
		logger.debug("Received " + event);
		
		final Image image = this.getById(event.getImageId());
		
		if(!imageResizeRequests.contains(event)){
			logger.debug("image resize event queue does not contain " + event);
			imageResizeRequests.add(event);
			
			imageResizingExecutor.execute(new Runnable(){
	
				@Override
				public void run() {
					
					Session session = null;
					Transaction tx = null;
					
					try{
						session = hibernateDao.getSessionFactory().openSession();
						tx = session.beginTransaction();
						
						resizeImage(image, event.getWidth(), event.getHeight());
						session.update(image);
//						tx.commit();
					}catch(Exception e){
						logger.info("failed to resize image asynchronously: " + e.getMessage());
						tx.rollback();
					}finally{	
						if(session != null){
							session.close();
						}
						
						logger.debug("removing image resize event from queue: " + event);
						imageResizeRequests.remove(event);
					}
				}
				
			});
		}
	}

	private void resizeImage(final Image image, final Integer width, final Integer height) {
		logger.debug("scaling image instance for image id " + image.getId() + " and dimensions " + width + " x " + height);
		
		try{
			boolean resizedCopyExists = false;
			Image resizedImage = null;
			
			for(Image resizedCopy : image.getResizedCopies()){
				if(StringUtils.equals(resizedCopy.getPath(), getImagePath(image, width, height))){
					logger.debug("scaled instance found");
					resizedCopyExists = true;
					resizedImage = resizedCopy;
					break;
				}
			}
			
			if(!resizedCopyExists){
				logger.debug("resized instance does not exist - creating scaled instance");
				image.readFrom(new File(imagesDir, getImagePath(image, null, null)));
				resizedImage = generateThumbnail(image, width, height);					
//				updateFieldsOnly(image);
			}
			
			if(resizedImage == null){
				logger.error("Resized Image is still null after attempting to find it or create it");
			}
		}catch(IOException e){
			logger.error("Failed to resize image: " + e.getMessage());
		}			
	}

	
	/**
	 * image path format:  {imagesDir}/images/{imageType}/{imageID}/{imageID} + _ + {width} + _ + {height} + {fileExt}
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	private String getImagePath(Image image, Integer width, Integer height){
		StringBuffer buffer = new StringBuffer()
				.append("/images/")
				.append(image.getType())
				.append("/")
				.append(image.getId())
				.append("/")
				.append(image.getId());
		
		if((width != null && height != null)  && (width != 0 && height != 0)){
			buffer.append("_")
				.append(width)
				.append("_")
				.append(height);
		}
		
		buffer.append(".")
			.append(image.getMimeType().getFileExts()[0]);
		
		String imagePath = buffer.toString();
		
		logger.debug("image path: " + imagePath);
		
		return imagePath;
	}

	/**
	 * Attempts to create a resized copy of the given Image object, using the given maxWidth
	 * and maxHeight to calculate a resize percentage (won't skew the image proportions).  Creates a new
	 * Image representing the resized copy, sets the original and the path of the resized copy, and adds
	 * itself to the originals collection of resized copies.  Finally returns the resized copy
	 * @param image the full size copy of the original image
	 * @param maxWidth
	 * @param maxHeight
	 * @return the resized copy of the original image
	 */
	private Image generateThumbnail(Image image, Integer maxWidth, Integer maxHeight){
		try {			
			byte[] thumbnail = scaleImage(image, maxWidth, maxHeight);
			String imagePath = getImagePath(image, maxWidth, maxHeight);
			File file = new File(imagesDir, imagePath);
			logger.debug("writing resized image to " + file.getAbsolutePath());
			FileUtils.writeByteArrayToFile(file, thumbnail);
			
			Image resizedImage = new Image();
			resizedImage.setOriginal(image);
			resizedImage.setPath(imagePath);
			
			image.getResizedCopies().add(resizedImage);
			
			return resizedImage;
		} catch (Exception e) {
			logger.error("An unexpected exception occurred when resizing an image: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void delete(Image image) {
		super.delete(image);
		
		deleteImageFromFilesystem(image);
		
		for(Image resized : image.getResizedCopies()){
			deleteImageFromFilesystem(resized);
		}
	}
	
	private void deleteImageFromFilesystem(Image image){
		try{
			String imagePath = image.getPath();			
			File imageFile = new File(imagesDir, imagePath);
			
			if(imageFile.exists()){
				imageFile.delete();
			}
		}catch(Exception e){
			logger.error("Failed to delete image " + image.getPath() + " from the file system", e);
		}
	}

	@Transactional(readOnly=true)
	public List<Image> findAllPaginated(Integer start, Integer max) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Image.class)
				.setFirstResult(start != null ? start : 0)
				.setMaxResults(max != null ? max : 60)
				.add(Restrictions.isNull("original"))
				.addOrder(Order.desc("id"));

		criteria.add(Restrictions.disjunction()
				.add(Restrictions.isNull("type"))
				.add(Restrictions.not(Restrictions.in("type", new String[]{"NEWS_ARTICLE", "AD", "AFFILIATE", "GAME", "BADGE", "contest"}))));
		
		criteria.setFetchMode("resizedCopies", FetchMode.LAZY);

		@SuppressWarnings("unchecked")
		List<Image> images = criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		return images;
	}
	
	
	
	@Transactional(readOnly = true)
	public Long getCount(){
		Criteria criteria = getCurrentSession()
				.createCriteria(Image.class)
				.add(Restrictions.isNull("original"));
				
		criteria.add(Restrictions.disjunction()
				.add(Restrictions.isNull("type"))
				.add(Restrictions.not(Restrictions.in("type", new String[]{"NEWS_ARTICLE", "AD", "AFFILIATE", "GAME", "BADGE", "contest"}))));
		
		criteria.setFetchMode("resizedCopies", FetchMode.LAZY);
				
		return (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
	}
	

	@Transactional(readOnly=true)
	public List<Image> findByAuthorAndType(final Long authorId, final String type) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Image.class)
				.add(Restrictions.eq("type", type))
				.add(Restrictions.isNull("original"))
				.setFetchMode("resizedCopies", FetchMode.LAZY)
				.addOrder(Order.desc("id"));

		criteria.createCriteria("createdBy").add(Restrictions.eq("id", authorId));
		criteria.setCacheable(true);
		
		@SuppressWarnings("unchecked")
		List<Image> images = criteria.list();
		return images;
	}
	
	@Transactional(readOnly=true)	
	public List<Image> findProfileImages(final Long profileId) {
		Session session = getCurrentSession();
		
		Criteria criteria = session.createCriteria(Image.class).add(Restrictions.isNull("original")).addOrder(Order.desc("id"));

		criteria.add(Restrictions.disjunction()
						.add(Restrictions.isNull("type"))
						.add(Restrictions.not(Restrictions.in("type", NON_PROFILE_IMAGE_TYPES))));

		criteria.createCriteria("createdBy").add(Restrictions.eq("id", profileId));
		criteria.setCacheable(true);
		criteria.setFetchMode("resizedCopies", FetchMode.LAZY);
		@SuppressWarnings("unchecked")
		List<Image> images = criteria.list();
		return images;
	}
	
	@Transactional(readOnly=true)
	public List<Image> findProfileImages(Long profileId, int start, int max) {
		Session session = getCurrentSession();
		
		Criteria criteria = session.createCriteria(Image.class).add(Restrictions.isNull("original")).addOrder(Order.desc("id"));

		criteria.add(Restrictions.disjunction()
						.add(Restrictions.isNull("type"))
						.add(Restrictions.not(Restrictions.in("type", NON_PROFILE_IMAGE_TYPES))));

		criteria.createCriteria("createdBy").add(Restrictions.eq("id", profileId));
		criteria.setCacheable(true);
		criteria.setFetchMode("resizedCopies", FetchMode.LAZY);
		@SuppressWarnings("unchecked")
		List<Image> images = (List<Image>)criteria.setMaxResults(max).setFirstResult(start).list();
		return images;
	}

	@Transactional(readOnly=true)
	public Long findByAuthorAndTypeCount(final Long authorId, final String type) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Image.class).add(Restrictions.isNull("original"));

		if ("PROFILE".equalsIgnoreCase(type)) {
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.isNull("type"))
					.add(Restrictions.not(Restrictions.in("type", NON_PROFILE_IMAGE_TYPES))));
		} else {
			criteria.add(Restrictions.eq("type", type));
		}

		criteria.createCriteria("createdBy").add(Restrictions.eq("id", authorId));
		criteria.setCacheable(true);

		return (Long)criteria.setProjection(Projections.count("id")).uniqueResult();
	}

	@Transactional(readOnly=true)
	public List<Image> findByAuthorAndType(final Long authorId, final String type, final Integer start, final Integer max) {
		Session session = getCurrentSession();
		
		Criteria criteria = session.createCriteria(Image.class).add(Restrictions.isNull("original"));

		if ("PROFILE".equalsIgnoreCase(type)) {
			criteria.add(Restrictions.disjunction()
							.add(Restrictions.isNull("type"))
							.add(Restrictions.not(Restrictions.in("type", new String[] {"NEWS_ARTICLE", "AD", "AFFILIATE", "GAME"}))));
		} else {
			criteria.add(Restrictions.eq("type", type));
		}

		criteria.createCriteria("createdBy").add(Restrictions.eq("id", authorId)).addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		if(start != null && max != null){
			criteria.setMaxResults(max).setFirstResult(start);
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<Image> images = criteria.list();
		return images;
	}

	public void comment(Long id, Comment comment) {
		Image image = getById(id);
		image.getComments().add(comment);
		hibernateDao.update(image);
//		flush();
//		
//		if(!image.getCreatedBy().equals(comment.getCreatedBy())){
//			logger.info("sending image comment email notification");
//			flush();
//			eventService.publishAsynch(new ImageCommentEvent(this, image, comment));					
//		}
		MemberFeedItem item = new MemberFeedItem();				
		item.setImageComment(image, comment);
		eventService.publish(new MemberFeedEvent(this, item));
	}

	public void like(Long imageId) {
		Image image = getById(imageId);
		ImageLike like = new ImageLike();
		like.setImage(image);
		image.getLikes().add(like);
		hibernateDao.update(image);
	}
	
	private byte[] scaleImage(Image image, int targetWidth, int targetHeight) throws IOException{
		if(image == null){
			throw new IllegalArgumentException("Image may not be null");
		}
		if(image.getMimeType() == null){
			throw new IllegalStateException("Image may not have a null MimeType");
		}
		
		return scaleImage(image.getBytes(), image.getMimeType(), targetWidth, targetHeight);		
	}
	
	byte[] scaleImage(byte[] bs, MimeType mimeType, int targetWidth, int targetHeight) throws IOException{
		if(bs == null || bs.length == 0){
			return null;
		}
		
		logger.debug("byte[] size: " + bs.length);
		logger.debug("mime type: " + mimeType.getContentTypes()[0]);
		
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bs));
		
		Dimension resizeDimensions = getResizeDimensions(originalImage.getWidth(), originalImage.getHeight(), targetWidth, targetHeight);
		
		ResampleOp resampleOp = new ResampleOp(resizeDimensions.width, resizeDimensions.height);
//		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
		
//		resampleOp.addProgressListener(new ProgressListener() {
//			public void notifyProgress(float fraction) {
//				logger.debug("Still working - " + fraction * 100);
//			}
//		});
		
		
		BufferedImage resizedImage = resampleOp.filter(originalImage, null);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();				
		
		if(!ImageIO.write(resizedImage, mimeType.getFileExts()[0], baos)){
			logger.warn("failed to write image of type " + mimeType.getFileExts()[0] + " to byte array output stream; Image thumbnal generation incomplete");
			return bs;
		}
		
		return baos.toByteArray();
	}	
	
	
	/**
	 * Calculates resize dimensions based on the following factors:  
	 * <li>Image width is greater than height
	 * <pre>
	 *  1.1 Resize dimensions are equal
	 *    width should be used to determine scale
	 *    dimension width == target width
	 *    dimension height <= target width
	 *  1.2 Resize width is greater than height
	 *    width should be used to determine scale
	 *    dimension width == target width
	 *    dimension height <= target width
	 *  1.3 Resize height is greater than width
	 *    height should be used to determine scale
	 *    dimension height == target height
	 *    dimension width <= original width
	 * </pre>
	 * 
	 * <li>Image height is greater than width
	 * <pre>
	 *  2.1 Resize dimensions are equal
	 *    height should be used to determine scale
	 *    dimension height == target height
	 *    dimension width <= target height
	 *  2.2 Resize width is greater than height
	 *    width should be used to determine scale
	 *    dimension width == target width
	 *    dimension height <= original height
	 *  2.3 Resize height is greater than width
	 *    height should be used to determine scale
	 *    dimension height == target height
	 *    dimension width <= target height
	 * </pre>
	 * <li>Image width is equal to height
	 * <pre>
	 *  3.1 Resize dimensions are equal
	 *    height can be used to determine scale
	 *    dimension height == target height
	 *    dimension width == target width
	 *  3.2 Resize width is greater than height
	 *    width should be used to determine scale
	 *    dimension width == target width
	 *    dimension height <= target width
	 *  3.3 Resize height is greater than width
	 *    height should be used to determine scale 
	 *    dimension height == target height
	 *    dimension width <= target height
	 * </pre>
	 */
	private Dimension getResizeDimensions(int originalWidth, int originalHeight, int targetWidth, int targetHeight){
		logger.debug("Target width: " + targetWidth);
		logger.debug("Target height: " + targetHeight);
		
		double scale = 1.0;
		int width = originalWidth;
		int height = originalHeight;		
		logger.debug("Image width: " + width);
		logger.debug("Image height: " + height);

		if(width <= targetWidth && height <= targetHeight){
			logger.debug("image dimensions are smaller than the resize dimensions - image dimensions will not be altered");
		}else if((width > height && (targetWidth == targetHeight || targetWidth > targetHeight))
		|| (width < height && targetWidth > targetHeight)
		|| (width == height && targetWidth > targetHeight)){
			logger.debug("Using the width to determine the scale");			
			scale = (double)targetWidth/width;
			logger.debug("Scale: " + scale);
			width = targetWidth;
			height = (int) (height * scale);
		}else if((height > width && (targetWidth == targetHeight || targetHeight > targetWidth))
		|| (height < width && targetHeight > targetWidth)
		|| (width == height && targetHeight > targetWidth)
		|| (width == height && targetWidth == targetHeight)){
			logger.debug("Using the height to determine the scale");
			
			scale = (double)targetHeight/height;
			logger.debug("Scale: " + scale);
			height = targetHeight;
			width = (int) (width * scale);
		}else{
			logger.warn("else statement reached when determining resize dimensions - case condition not accounted for");
			logger.warn("Image width: " + width);
			logger.warn("Image height: " + height);
			logger.warn("Target width: " + targetWidth);
			logger.warn("Target height: " + targetHeight);		
		}
		
		logger.debug("Calculated Width: " + width);
		logger.debug("Calculated Height: " + height);
		
		return new Dimension(width, height);
	}

	

	

	
}
