package com.mybuckstory.web.command;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.web.multipart.MultipartFile;

import com.mybuckstory.model.Category;
import com.mybuckstory.model.Story;

public class EditStoryCommand {

	private Long id;
	private Integer version;
	private String title;
	private String text;
	private SortedSet<Category> categories = new TreeSet<Category>();
	private MultipartFile file;
	
	private String metaDescription;
	private String metaKeywords;
	
	public EditStoryCommand(){};
	
	public EditStoryCommand(Story story){
		this.id = story.getId();
		this.version = story.getVersion();
		this.title = story.getTitle();
		this.text = story.getText();
		this.categories = story.getCategories();
		this.metaDescription = story.getMetaDescription();
		this.metaKeywords = story.getMetaKeywords();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SortedSet<Category> getCategories() {
		return categories;
	}

	public void setCategories(SortedSet<Category> categories) {
		this.categories = categories;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	
	
}
