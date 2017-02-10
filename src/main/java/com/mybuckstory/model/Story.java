package com.mybuckstory.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Story extends AbstractSingleImageContainer implements CategorizedContent, Resource {

	private String title;
	private String text;
	private String uri;
	private Long categoryId;
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());
	private SortedSet<Category> categories = Collections.synchronizedSortedSet(new TreeSet<Category>());
	private Set<StoryRating> storyRatings = Collections.synchronizedSet(new HashSet<StoryRating>());
	private Set<MemberFeedItem> memberFeedItems = Collections.synchronizedSet(new HashSet<MemberFeedItem>());
	private Set<StoryVote> votes = Collections.synchronizedSet(new HashSet<StoryVote>());
	private Set<ContestPrize> prizes = Collections.synchronizedSet(new HashSet<ContestPrize>());
	
	private Integer voteCount;
	private Double averageRating;
	
	private String metaKeywords;
	private String metaDescription;
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	/* (non-Javadoc)
	 * @see com.mybuckstory.model.CategorizedContent#getCategories()
	 */
	public SortedSet<Category> getCategories() {
		return categories;
	}

	/* (non-Javadoc)
	 * @see com.mybuckstory.model.CategorizedContent#setCategories(java.util.SortedSet)
	 */
	public void setCategories(SortedSet<Category> categories) {
		this.categories = categories;
	}
	
	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Double getAverageRating(){
		return averageRating;
	}

	public void setAverageRating(Double averageRating){
		this.averageRating = averageRating;
	}	

	public Set<StoryRating> getStoryRatings(){
		return storyRatings;
	}

	public void setStoryRatings(Set<StoryRating> storyRatings){
		this.storyRatings = storyRatings;
	}

	public Set<MemberFeedItem> getMemberFeedItems(){
		return memberFeedItems;
	}

	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems){
		this.memberFeedItems = memberFeedItems;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Set<StoryVote> getVotes() {
		return votes;
	}

	public void setVotes(Set<StoryVote> votes) {
		this.votes = votes;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
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

	public Set<ContestPrize> getPrizes() {
		return prizes;
	}

	public void setPrizes(Set<ContestPrize> prizes) {
		this.prizes = prizes;
	}

}
