package com.mybuckstory.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.event.MemberFeedEvent;
import com.mybuckstory.core.service.AlbumService;
import com.mybuckstory.core.service.ApplicationEventService;
import com.mybuckstory.core.service.CategoryService;
import com.mybuckstory.core.service.ImageService;
import com.mybuckstory.model.Album;
import com.mybuckstory.model.Category;
import com.mybuckstory.model.Comment;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.MemberFeedItem;
import com.mybuckstory.model.Story;
import com.mybuckstory.model.Image.Type;
import com.mybuckstory.model.MimeType;
import com.mybuckstory.web.command.EditStoryCommand;
import com.mybuckstory.web.editor.CategorySortedSetEditor;

@Controller
public class ImageController {

	public static final String LAST_MOD_HEADER = "Last-Modified";
    public static final String EXPIRES_HEADER = "Expires";
    public static final String CACHE_CONTROL_HEADER = "Cache-Control";
    public static final Integer DEFAULT_MAX_WIDTH = new Integer(100);
	public static final Integer DEFAULT_MAX_HEIGHT = new Integer(100);
    
    private static final Logger logger = Logger.getLogger(ImageController.class);
	
	private final ImageService imageService;
	private final AlbumService albumService;
	private final CategoryService categoryService;
	private final ApplicationEventService appEventService;
	
	@Autowired
	public ImageController(ImageService imageService, AlbumService albumService, CategoryService categoryService, ApplicationEventService appEventService) {
		this.imageService = imageService;
		this.albumService = albumService;
		this.categoryService = categoryService;
		this.appEventService = appEventService;		
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Set.class, "categories", new CategorySortedSetEditor(this.categoryService));
	}
	
	@ModelAttribute("categories")
	public List<Category> populateCategories(){
		return categoryService.getParentCategories();
	}
	
	@RequestMapping(value="/image/create", method = RequestMethod.GET)
	public ModelAndView create() throws Exception{
		ModelAndView mav = new ModelAndView("formImage");
		mav.addObject("command", new Image());
		return mav;
	}
	
	@RequestMapping(value="/image/save", method = RequestMethod.POST)
	public String save(Image command, final BindingResult result, HttpServletResponse response) throws Exception{
//		//TODO - implement image validation
		//imageValidator.validate(image, result);
//		if(result.hasErrors()){
//			return new ModelAndView("formImage");
//		}
		
		Image image = new Image(command.getFile());
		image.setTitle(command.getTitle());
		image.setDescription(command.getDescription());
		image.setTags(command.getTags());
		image.setType(command.getType());
		
		Long imageId = imageService.create(image);

		response.sendRedirect("/image.html?imageId=" + imageId);
		
		return null;
	}
	
	@RequestMapping(value="/image/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id){
		ModelAndView mav = new ModelAndView("formImage");
		mav.addObject("command", imageService.getById(id));
		return mav;
	}
	
	@RequestMapping(value="/image/update", method = RequestMethod.POST)
	public ModelAndView update(Image command, final BindingResult result, HttpServletResponse response) throws Exception{
//		TODO - implement validation
//		editStoryCommandValidator.validate(command, result);
//		if(result.hasErrors()){
//			//TODO - change this so that the command gets added back into the view
//			return "formStory";
//		}
		
		Image image = imageService.getById(command.getId());
		
		image.setTitle(command.getTitle());		
		image.setDescription(command.getDescription());
		image.setTags(command.getTags());
		image.setType(command.getType());
		
		imageService.updateFieldsOnly(image);
		response.sendRedirect("/image.html?imageId=" + image.getId());
		
		return null;
	}
	
	/**
	 * Adds an attribute to the ModelMap with the name <code>WordUtils.uncapitalize(this.typeName) + "List"</code>,
	 * calling the {@link #list()} method, using the returned values as the attribute value, and will return
	 * the "list view," which is created in the constructor using  <code>String.format("list%s", this.typeName)</code>
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/image/list", method = RequestMethod.GET)
	public String list(ModelMap model, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, @RequestParam(value="sort", required=false) String sort, @RequestParam(value="order", required=false) String order){
		model.addAttribute("imageList", imageService.findAllPaginated(start, max));
		model.addAttribute("imageTotal", imageService.getCount());
		return "listImage";
	}
	
	@RequestMapping(value="/image/comment/{id}", method = RequestMethod.POST)
	public void comment(@PathVariable("id") Long id, Comment comment, HttpServletResponse response) throws Exception{
		if(StringUtils.isNotBlank(comment.getText())){
			imageService.comment(id, comment);
		}
		response.sendRedirect("/image.html?imageId=" + id);	
	}
	
	@RequestMapping(value="/image/album/create", method = RequestMethod.GET)
	public ModelAndView createAlbum() throws Exception{
		ModelAndView mav = new ModelAndView("create.album");		
		mav.addObject("command", new Album());		
		return mav;
	}
	
	@RequestMapping(value="/image.html", method=RequestMethod.GET)
	public ModelAndView show(@RequestParam(value="imageId", required=false) Long imageId, @RequestParam(value="album", required=false) String album, @RequestParam(value="width", required=false) Integer width, @RequestParam(value="height", required=false) Integer height, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max, HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("Showing image with id " + imageId);
		
		ModelAndView mav = new ModelAndView("showImage");
		
		Image image = null;
		
		if(imageId != null){
			image = imageService.getById(imageId);
			
			if(image != null){
				//TODO - if this is a profile image, then it's easy to get all profile images
				//if this is a story image, there could be other "story" images created by this author
				//if this is an album image, then we will need to get the album, and then pass in album.getImages()
				if("profile".equalsIgnoreCase(album)){
					mav.addAllObjects(albumService.getGalleryDisplayModel(image, imageService.findProfileImages(image.getCreatedBy().getId())));
				}else if("all".equalsIgnoreCase(album)){
					mav.addAllObjects(albumService.getGalleryDisplayModel(image, imageService.findAllPaginated((start != null && start != 0 ? start - 1 : 0), (max != null && max != 60 ? max + 1 : 61))));
				}else if(image.getAlbum() != null){
					mav.addAllObjects(albumService.getGalleryDisplayModel(image, image.getAlbum().getImages()));
				}else{
					mav.addObject("image", image);
				}
			}else{
				mav.setViewName("404");
			}
		}else{
			mav.setViewName("404");
		}
		
		if(image == null){
			mav.setViewName("404");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/image/album/show/{albumId}", method=RequestMethod.GET)
	public ModelAndView showAlbum(@PathVariable(value="albumId") Long albumId) throws Exception{
		ModelAndView mav = new ModelAndView("show.album");
		//TODO - if album doesn't exist, sent to DNE page
		Album album = albumService.getById(albumId);
		mav.addObject("album", album);
		
		return mav;
	}
	
	@RequestMapping(value="/streamimage.do", method=RequestMethod.GET)
	public void sendToShow(@RequestParam(value="imageId", required=false) Long imageId, @RequestParam(value="width", required=false) Integer width, @RequestParam(value="height", required=false) Integer height, HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("Streaming image with id " + imageId);
		
		if(imageId != null){
			Image image = imageService.getById(imageId);
			if(image != null){				
				try{
					logger.debug("Image type: " + image.getType());
					
					String redirect = imageService.getImagePathGenerateIfNecessary(image, width, height);
					String imageUri = "/static" + redirect;
					logger.debug("redirecting to image uri: " + imageUri);
					
					response.sendRedirect(imageUri);
					return;
				}catch(Exception e){
					logger.warn("An error occurred while attempting to stream image with id " + imageId, e);
				}				
			}				
		}
		
		logger.info("Image was either empty or not found - redirecting to /images/noStoryImage.png");
		response.sendRedirect("/images/noStoryImage.png");
	}
	
	@RequestMapping(value="/upload.images.do", method=RequestMethod.POST)
	public void uploadProfileImages(MultipartHttpServletRequest multipartRequest) throws Exception {
		logger.debug("We've got ourselves a multipart request!!");
		List<Image> images = getImages(multipartRequest);
		for(Image image : images){
			image.setType(Type.GALLERY.toString());
			imageService.create(image);			
		}	
	}
	
	@RequestMapping(value="/image/album/save", method=RequestMethod.POST)
	public void saveAlbum(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		//TODO - validate!!!
		
		logger.debug("We've got ourselves a multipart request!!");
		
		final Album album = new Album();
		album.setName(multipartRequest.getParameter("name"));
		album.setDescription(multipartRequest.getParameter("description"));
//		albumService.create(album);		
		
		List<Image> images = getImages(multipartRequest);
		for(Image image : images){
			image.setAlbum(album);
			image.setType(Type.ALBUM.toString());
			imageService.create(image);			
		}	
		
		try{
			MemberFeedItem feedItem = new MemberFeedItem();
			feedItem.setAlbum(album);
			feedItem.setType(MemberFeedItem.Type.ALBUM);
			appEventService.publish(new MemberFeedEvent(this, feedItem));
		}catch(Exception e){
			logger.error("Failed to published member feed item event for new album event: " + e.getMessage());
		}
		
		response.sendRedirect("/image/album/show/" + album.getId());
		
	}

	/**
	 * Pulls Images from the MultipartHttpServletRequest, persists them (which triggers Image resizing logic for thumbnails), and returns the
	 * persisted Images
	 * @param multipartRequest
	 * @return
	 * @throws IOException
	 */
	private List<Image> getImages(MultipartHttpServletRequest multipartRequest) throws IOException {
		logger.info("there are " + multipartRequest.getFileMap().size() + " files in this request");		
		
		List<Image> images = new ArrayList<Image>();
		
		for(Iterator<String> fileNames = multipartRequest.getFileNames(); fileNames.hasNext();){			
			String name = fileNames.next();
			logger.debug("File name: " + name);
			MultipartFile file = multipartRequest.getFile(name);
			
			if(file != null && file.getSize() > 0L){
				logger.debug("Content type: " + file.getContentType());
				logger.debug("Original File name: "  + file.getOriginalFilename());
				logger.debug("Size: " + file.getSize());
				
				MimeType mimeType = MimeType.getByContentType(file.getContentType());
				
				logger.debug("MimeType: " + mimeType);
				
				String attrNamePrefix = mimeType.isImage() ? "image" : "file";
				
				String number = name.replace(attrNamePrefix, "");
				logger.debug("File Number: " + number);
				
				String title = multipartRequest.getParameter("title" + number);
				if(StringUtils.isBlank(title)){
					title = file.getOriginalFilename();
				}
				
				logger.debug("Title: " + title);	
				
				/*
				 * We only care about the differences between images and documents
				 */
				if(mimeType.isImage()){
					Image image = new Image(file);
					image.setTitle(title);			
					images.add(image);
				}
			}				
		}
		
		return images;
	}

}
