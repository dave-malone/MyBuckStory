package com.mybuckstory.web.command;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.common.Constants;
import com.mybuckstory.model.Profile;


public class ProfileInfo {	
	
	private String firstName;
	private String lastName;
	private String gender;
	private String location;
	private Date dob;
	private String dobDay;
	private String dobMonth;
	private String dobYear;
	
	private String aboutMe;
	private String interests;
	private String favSpecies;
	private String favGear;
	private String favMusic;
	private String favMovies;
	
	public ProfileInfo(){}
	
	public ProfileInfo(Profile profile) {
		this.firstName = profile.getFirstName();
		this.lastName = profile.getLastName();
		this.gender = profile.getGender();
		this.location = profile.getLocation();
		this.aboutMe = profile.getAbout();
		this.interests = profile.getInterests();
		this.favSpecies = profile.getFavoriteSpecies();
		this.favGear = profile.getFavoriteGear();
		this.favMusic = profile.getFavoriteMusic();
		this.favMovies = profile.getFavoriteMovies();
		try {
			this.dob = profile.getDob();
		} catch (Exception e) {}
	}

	public Date getDob() throws Exception{
		if(this.dob != null){
			return dob;
		}
		
		if(StringUtils.isNotBlank(dobDay) && StringUtils.isNotBlank(dobMonth) && StringUtils.isNotBlank(dobYear)){
			return Constants.BASIC_DATE_FORMATTER.parse(dobMonth + "/" + dobDay + "/"  + dobYear);
		}
		
		throw new IllegalStateException("one of the dob fields was empty");
	}
	
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	public String getFavMusic() {
		return favMusic;
	}
	public void setFavMusic(String favMusic) {
		this.favMusic = favMusic;
	}
	public String getFavMovies() {
		return favMovies;
	}
	public void setFavMovies(String favMovies) {
		this.favMovies = favMovies;
	}
	public String getFavSpecies(){
		return favSpecies;
	}
	public void setFavSpecies(String favSpecies){
		this.favSpecies = favSpecies;
	}
	public String getFavGear(){
		return favGear;
	}
	public void setFavGear(String favGear){
		this.favGear = favGear;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDobDay() {
		return dobDay;
	}
	public void setDobDay(String dobDay) {
		this.dobDay = dobDay;
	}
	public String getDobMonth() {
		return dobMonth;
	}
	public void setDobMonth(String dobMonth) {
		this.dobMonth = dobMonth;
	}
	public String getDobYear() {
		return dobYear;
	}
	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
	}	
	
}
