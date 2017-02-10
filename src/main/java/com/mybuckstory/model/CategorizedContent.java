package com.mybuckstory.model;

import java.util.SortedSet;

public interface CategorizedContent {

	public abstract SortedSet<Category> getCategories();

	public abstract void setCategories(SortedSet<Category> categories);

}