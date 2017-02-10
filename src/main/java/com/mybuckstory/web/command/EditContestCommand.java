package com.mybuckstory.web.command;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mybuckstory.common.Constants;
import com.mybuckstory.model.Affiliate;
import com.mybuckstory.model.Category;
import com.mybuckstory.model.Contest;

public class EditContestCommand {

	private Long id;
	private Integer version;
	private String title;
	private String description;
	private String rules;
	private Date startDate;
	private Date endDate;
	private Affiliate sponsor;
	private Category storyCategory;
	private MultipartFile file;	
	
	private String metaDescription;
	private String metaKeywords;
	
	private String startDateDay;
	private String startDateMonth;
	private String startDateYear;
	
	private String endDateDay;
	private String endDateMonth;
	private String endDateYear;
	
	
	public EditContestCommand(){}
	
	public EditContestCommand(Contest contest){
		this.id = contest.getId();
		this.version = contest.getVersion();
		this.title = contest.getTitle();
		this.description = contest.getDescription();
		this.sponsor = contest.getSponsor();
		this.storyCategory = contest.getStoryCategory();
		
		try { this.startDate = contest.getStartDate(); } catch (Exception e) {}
		try { this.endDate = contest.getEndDate(); } catch (Exception e) {}
		
		this.metaDescription = contest.getMetaDescription();
		this.metaKeywords = contest.getMetaKeywords();
	}

	public Date getStartDate() throws Exception{
		if(StringUtils.isNotBlank(startDateDay) && StringUtils.isNotBlank(startDateMonth) && StringUtils.isNotBlank(startDateYear)){
			return Constants.BASIC_DATE_FORMATTER.parse(startDateMonth + "/"  + startDateDay + "/"  + startDateYear);
		}
		return startDate;
	}	
	public Date getEndDate() throws Exception{
		if(StringUtils.isNotBlank(endDateDay) && StringUtils.isNotBlank(endDateMonth) && StringUtils.isNotBlank(endDateYear)){
			return Constants.BASIC_DATE_FORMATTER.parse(endDateMonth + "/"  + endDateDay + "/"  + endDateYear);
		}
		return endDate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public Category getStoryCategory() {
		return storyCategory;
	}

	public void setStoryCategory(Category storyCategory) {
		this.storyCategory = storyCategory;
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

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	
	
	
	
}
