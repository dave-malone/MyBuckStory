package com.mybuckstory.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.common.Constants;

public class Contest extends AbstractSingleImageContainer implements Resource{

	private String title;
	private String description;
	private String rules;
	private Date startDate;
	private Date endDate;
	private Category storyCategory;
	private Affiliate sponsor;
	private SortedSet<ContestPrize> contestPrizes = Collections.synchronizedSortedSet(new TreeSet<ContestPrize>());
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());
	private Set<MemberFeedItem> memberFeedItems = Collections.synchronizedSet(new HashSet<MemberFeedItem>());
	private String uri;
	
	private String metaKeywords;
	private String metaDescription;
	
	private String startDateDay;
	private String startDateMonth;
	private String startDateYear;
	
	private String endDateDay;
	private String endDateMonth;
	private String endDateYear;
	
	
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
	public Date getStartDate() throws Exception{
		if(StringUtils.isNotBlank(startDateDay) && StringUtils.isNotBlank(startDateMonth) && StringUtils.isNotBlank(startDateYear)){
			return Constants.BASIC_DATE_FORMATTER.parse(startDateMonth + "/"  + startDateDay + "/"  + startDateYear);
		}
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() throws Exception{
		if(StringUtils.isNotBlank(endDateDay) && StringUtils.isNotBlank(endDateMonth) && StringUtils.isNotBlank(endDateYear)){
			return Constants.BASIC_DATE_FORMATTER.parse(endDateMonth + "/"  + endDateDay + "/"  + endDateYear);
		}
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Affiliate getSponsor() {
		return sponsor;
	}
	public void setSponsor(Affiliate sponsor) {
		this.sponsor = sponsor;
	}	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
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
	public String getStartDateDay() {
		return startDateDay;
	}
	public void setStartDateDay(String startDateDay) {
		this.startDateDay = startDateDay;
	}
	public String getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(String startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public String getStartDateYear() {
		return startDateYear;
	}
	public void setStartDateYear(String startDateYear) {
		this.startDateYear = startDateYear;
	}
	public String getEndDateDay() {
		return endDateDay;
	}
	public void setEndDateDay(String endDateDay) {
		this.endDateDay = endDateDay;
	}
	public String getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(String endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public String getEndDateYear() {
		return endDateYear;
	}
	public void setEndDateYear(String endDateYear) {
		this.endDateYear = endDateYear;
	}
	public SortedSet<Comment> getComments() {
		return comments;
	}
	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}
	public Set<MemberFeedItem> getMemberFeedItems() {
		return memberFeedItems;
	}
	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems) {
		this.memberFeedItems = memberFeedItems;
	}
	public SortedSet<ContestPrize> getContestPrizes() {
		return contestPrizes;
	}
	public void setContestPrizes(SortedSet<ContestPrize> contestPrizes) {
		this.contestPrizes = contestPrizes;
	}
	public Category getStoryCategory() {
		return storyCategory;
	}
	public void setStoryCategory(Category storyCategory) {
		this.storyCategory = storyCategory;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}	
}
