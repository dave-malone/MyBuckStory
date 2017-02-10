package com.mybuckstory.model;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.web.multipart.MultipartFile;

public class Video extends Binary{

	private String title;
	private String description;
	
	/**
	 * The file name from the uploaded file
	 */
	private String originalFileName;
	
	/**
	 * The MIME Type of the uploaded file
	 */
	private String originalFileMimeType;
		
	/**
	 * Videos are encoded by Encoding.com.  When the job
	 * to encode the video is first submitted to Encoding.com,
	 * a MediaID is returned that associates the given file
	 * with an ID on the encoding.com server
	 */
	private String encodingComMediaID;
	
	/**
	 * This either represents the file path on the MyBuckStory.com VM,
	 * or a CDN location
	 */
	private String rawFileName;
	
	/**
	* This either represents the file path on the MyBuckStory.com VM,
	* or a CDN location
	*/
	private String encodedFileName;
	
	/**
	* This either represents the file path on the MyBuckStory.com VM,
	* or a CDN location
	*/
	private String previewThumbnailFileName;
	
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());

	
	public Video(){}
	
	public Video(MultipartFile file) throws IOException {
		this.setContent(file.getBytes());
		this.originalFileName = file.getOriginalFilename();
		this.originalFileMimeType = file.getContentType();
	}
	
	public Video(String originalFilename, String contentType, byte[] bytes) {
		this.originalFileName = originalFilename;
		this.originalFileMimeType = contentType;
		super.setContent(bytes);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getOriginalFileMimeType() {
		return originalFileMimeType;
	}

	public void setOriginalFileMimeType(String originalFileMimeType) {
		this.originalFileMimeType = originalFileMimeType;
	}

	public String getEncodingComMediaID() {
		return encodingComMediaID;
	}

	public void setEncodingComMediaID(String encodingComMediaID) {
		this.encodingComMediaID = encodingComMediaID;
	}

	public String getRawFileName() {
		return rawFileName;
	}

	public void setRawFileName(String rawFileName) {
		this.rawFileName = rawFileName;
	}

	public String getEncodedFileName() {
		return encodedFileName;
	}

	public void setEncodedFileName(String encodedFileName) {
		this.encodedFileName = encodedFileName;
	}

	public String getPreviewThumbnailFileName() {
		return previewThumbnailFileName;
	}

	public void setPreviewThumbnailFileName(String previewThumbnailFileName) {
		this.previewThumbnailFileName = previewThumbnailFileName;
	}

	public SortedSet<Comment> getComments() {
		return comments;
	}

	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}
	
}
