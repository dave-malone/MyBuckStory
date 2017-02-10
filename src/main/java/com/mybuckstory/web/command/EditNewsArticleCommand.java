package com.mybuckstory.web.command;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.web.multipart.MultipartFile;

import com.mybuckstory.model.Category;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.NewsArticle;

public class EditNewsArticleCommand {

	private Long id;
	private Integer version;
	private String title;
	private String content;
	private SortedSet<Category> categories = new TreeSet<Category>();
	private MultipartFile file;
	private Image image;
	
	private String metaDescription;
	private String metaKeywords;
	
	
	public EditNewsArticleCommand(){}
	
	public EditNewsArticleCommand(NewsArticle article){
		this.id = article.getId();
		this.version = article.getVersion();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.categories = article.getCategories();
		this.metaDescription = article.getMetaDescription();
		this.metaKeywords = article.getMetaKeywords();
		this.image = article.getImage();
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}
