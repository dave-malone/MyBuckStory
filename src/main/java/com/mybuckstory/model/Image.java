package com.mybuckstory.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.web.multipart.MultipartFile;

public class Image extends Binary {
	
	public enum Type {AD, AFFILIATE, ALBUM, BADGE, COMPETITION, GALLERY, GAME, NEWS_ARTICLE, PRIZE, PROFILE, STORY, PICS}
	
	private String title;
	private String description;
	private String tags;
	private byte[] thumbnail;	
	private String originalFileName;
	private String type;
	private String path;
	private Image original;
	
	private Affiliate affiliate;
	private NewsArticle article;
	private Badge badge;
	private Contest contest;
	private Gallery gallery;
	private Album album;
	private Profile profile;
	private Story story;
	private Prize prize;
	
	private Set<Image> resizedCopies = Collections.synchronizedSet(new HashSet<Image>());
	private SortedSet<ImageLike> likes = Collections.synchronizedSortedSet(new TreeSet<ImageLike>());
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());
	private Set<ContestPrize> prizes = Collections.synchronizedSet(new HashSet<ContestPrize>());
	private Set<MemberFeedItem> memberFeedItems = Collections.synchronizedSet(new HashSet<MemberFeedItem>());
	private SortedSet<Category> categories = Collections.synchronizedSortedSet(new TreeSet<Category>());

	public Image(){}
	
	public Image(MultipartFile file) throws IOException {
		setFile(file);
		this.title = file.getName();
	}
	
	private void attemptToSetMimeType(){
		MimeType mimeType = getMimeType();
		
		if(MimeType.DEFAULT_BINARY.equals(mimeType) || mimeType == null){			
			String originalFileName = getOriginalFileName();			
			String fileExt = originalFileName.substring(originalFileName.lastIndexOf('.'));			
			mimeType = MimeType.getByFileExt(fileExt);
		}
		
		if(mimeType == null){
			throw new RuntimeException("MimeType was still null; originalFileName: " + getOriginalFileName());
		}
		
		setMimeType(mimeType);		
	}
	
	
	public byte[] getThumbnail() {
		return thumbnail;
	}
	
	public byte[] getThumbnailBytes() throws SQLException{
		return this.thumbnail;
	}
	
	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	
	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	public SortedSet<Comment> getComments() {
		return comments;
	}

	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == this){
			return true;
		}
		
		if(!(obj instanceof Image)){
			return false;
		}
		
		final Image image = (Image)obj;
		
		if(image.getPath() != null){
			return image.getPath().equals(this.getPath());
		}else if(image.getOriginalFileName()!= null){
			return image.getOriginalFileName().equals(this.getOriginalFileName());
		}else{
			return image.getId() == null ? this.getId() == null : image.getId().equals(this.getId());
		}
	}

	@Override
	public int hashCode(){
		int result = 23;
		result = 17 * result + (this.getId() == null ? 0 : this.getId().hashCode());
		result = 17 * result + (this.getPath() == null ? 0 : this.getPath().hashCode());
		result = 17 * result + (this.getOriginalFileName() == null ? 0 : this.getOriginalFileName().hashCode());
		return result;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Image getOriginal() {
		return original;
	}

	public void setOriginal(Image original) {
		this.original = original;
	}

	public Set<Image> getResizedCopies() {
		return resizedCopies;
	}

	public void setResizedCopies(Set<Image> resizedCopies) {
		this.resizedCopies = resizedCopies;
	}

	@Override
	public String toString() {
		return String.format("Image [id=%s, type=%s, path=%s]", id, type, path);
	}

	public Set<ContestPrize> getPrizes() {
		return prizes;
	}

	public void setPrizes(Set<ContestPrize> prizes) {
		this.prizes = prizes;
	}

	public SortedSet<ImageLike> getLikes() {
		return likes;
	}

	public void setLikes(SortedSet<ImageLike> likes) {
		this.likes = likes;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public NewsArticle getArticle() {
		return article;
	}

	public void setArticle(NewsArticle article) {
		this.article = article;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	public Affiliate getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(Affiliate affiliate) {
		this.affiliate = affiliate;
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	public Prize getPrize() {
		return prize;
	}

	public void setPrize(Prize prize) {
		this.prize = prize;
	}

	public Set<MemberFeedItem> getMemberFeedItems() {
		return memberFeedItems;
	}

	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems) {
		this.memberFeedItems = memberFeedItems;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SortedSet<Category> getCategories() {
		return categories;
	}

	public void setCategories(SortedSet<Category> categories) {
		this.categories = categories;
	}	
	
	public void setFile(MultipartFile file) {
		super.setFile(file);
		try {
			if(containsNewlyUploadedImage()){
				super.mimeType = MimeType.getByContentType(file.getContentType());
				super.setContent(file.getBytes());
				this.originalFileName = file.getOriginalFilename();
				attemptToSetMimeType();			
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
}
