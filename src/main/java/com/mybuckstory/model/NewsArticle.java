package com.mybuckstory.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class NewsArticle extends AbstractSingleImageContainer implements CategorizedContent, Resource{

	private String title;
	private String website;
	private String content;
	private String uri;
	private Long categoryId;
	private SortedSet<Category> categories = Collections.synchronizedSortedSet(new TreeSet<Category>());
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());

	private String metaKeywords;
	private String metaDescription;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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


	public SortedSet<Comment> getComments() {
		return comments;
	}


	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}
	
	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

}
