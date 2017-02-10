package com.mybuckstory.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mybuckstory.util.AlphanumComparator;

public class Category extends AbstractAuditable {

	private Category parent;
	private String name;
	private SortedSet<NewsArticle> newsArticles = Collections.synchronizedSortedSet(new TreeSet<NewsArticle>());
	private SortedSet<Story> stories = Collections.synchronizedSortedSet(new TreeSet<Story>());
	private SortedSet<Image> images = Collections.synchronizedSortedSet(new TreeSet<Image>());
	private SortedSet<Category> children = Collections.synchronizedSortedSet(new TreeSet<Category>());
	
	public Category(){}
	
	public Category(String name) {
		this.name = name;
	}

	public Category(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public int compareTo(Object obj) {
		if(!(obj instanceof Category)){
			return -1;
		}
		
		Category category = (Category)obj;		
		
		if(this.name == null && category.name == null){
			return 0;
		}else if(this.name != null && category.name == null){
			return 1;
		}else if(this.name == null && category.name != null){
			return -1;
		}else{
			return new AlphanumComparator().compare(this.name, category.name);
			
//			return this.name.compareTo(category.name);
		}
	}

	public SortedSet<NewsArticle> getNewsArticles() {
		return newsArticles;
	}

	public void setNewsArticles(SortedSet<NewsArticle> newsArticles) {
		this.newsArticles = newsArticles;
	}

	public SortedSet<Story> getStories() {
		return stories;
	}

	public void setStories(SortedSet<Story> stories) {
		this.stories = stories;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public SortedSet<Category> getChildren() {
		return children;
	}

	public void setChildren(SortedSet<Category> children) {
		this.children = children;
	}

	public SortedSet<Image> getImages() {
		return images;
	}

	public void setImages(SortedSet<Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
	

}
