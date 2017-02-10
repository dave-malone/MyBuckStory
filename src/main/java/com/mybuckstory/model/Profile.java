package com.mybuckstory.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.mybuckstory.common.Constants;

public class Profile extends AbstractSingleImageContainer implements Resource{

	private Long id;
	private String firstName;
	private String lastName;
	private Date dob;
	private String dobDay;
	private String dobMonth;
	private String dobYear;
	private boolean showBirthday = true;
	private boolean under18 = false;
	private boolean makePublic = false;
	private String gender;
	private User user;
	private String about;
	private String interests;
	private String favoriteSpecies;
	private String favoriteGear;
	private String favoriteMusic;
	private String favoriteMovies;
	private String location;
	private String uri;
	private boolean disableAllEmailNotifications = false;
	
	private Set<User> friends = Collections.synchronizedSet(new HashSet<User>());
	private Set<ContestPrize> contestPrizes = Collections.synchronizedSet(new HashSet<ContestPrize>());
	private Set<Badge> badges = Collections.synchronizedSet(new HashSet<Badge>());
	private Set<Gallery> galleries = Collections.synchronizedSet(new HashSet<Gallery>());
	private SortedSet<Comment> comments = Collections.synchronizedSortedSet(new TreeSet<Comment>());
	private SortedSet<Story> stories = Collections.synchronizedSortedSet(new TreeSet<Story>());
	private SortedSet<WallPost> wallPosts = Collections.synchronizedSortedSet(new TreeSet<WallPost>());
		
	private String metaKeywords;
	private String metaDescription;
	
	public SortedSet<Comment> getComments() {
		return comments;
	}

	public void setComments(SortedSet<Comment> comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
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
	
	public String getFullName(){
		return firstName + " " + lastName;
	}

	public Date getDob() throws Exception{
		if(dob != null){
			return this.dob;
		}
		
		if(StringUtils.isNotBlank(dobDay) && StringUtils.isNotBlank(dobMonth) && StringUtils.isNotBlank(dobYear)){
			return Constants.BASIC_DATE_FORMATTER.parse(dobMonth + "/"  + dobDay + "/"  + dobYear);
		}
		
		throw new IllegalStateException("one of the dob fields was empty");
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isShowBirthday() {
		return showBirthday;
	}

	public void setShowBirthday(boolean showBirthday) {
		this.showBirthday = showBirthday;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getFavoriteMusic() {
		return favoriteMusic;
	}

	public void setFavoriteMusic(String favoriteMusic) {
		this.favoriteMusic = favoriteMusic;
	}

	public String getFavoriteMovies() {
		return favoriteMovies;
	}

	public void setFavoriteMovies(String favoriteMovies) {
		this.favoriteMovies = favoriteMovies;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public SortedSet<Story> getStories() {
		return stories;
	}

	public void setStories(SortedSet<Story> stories) {
		this.stories = stories;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isUnder18() {
		return under18;
	}

	public void setUnder18(boolean under18) {
		this.under18 = under18;
	}

	public boolean isMakePublic() {
		return makePublic;
	}

	public void setMakePublic(boolean makePublic) {
		this.makePublic = makePublic;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isDisableAllEmailNotifications() {
		return disableAllEmailNotifications;
	}

	public void setDisableAllEmailNotifications(boolean disableAllEmailNotifications) {
		this.disableAllEmailNotifications = disableAllEmailNotifications;
	}

	public String getFavoriteSpecies(){
		return favoriteSpecies;
	}

	public void setFavoriteSpecies(String favoriteSpecies){
		this.favoriteSpecies = favoriteSpecies;
	}

	public String getFavoriteGear(){
		return favoriteGear;
	}

	public void setFavoriteGear(String favoriteGear){
		this.favoriteGear = favoriteGear;
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

	public SortedSet<WallPost> getWallPosts() {
		return wallPosts;
	}

	public void setWallPosts(SortedSet<WallPost> wallPosts) {
		this.wallPosts = wallPosts;
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

	public Set<Badge> getBadges() {
		return badges;
	}

	public void setBadges(Set<Badge> badges) {
		this.badges = badges;
	}

	public Set<ContestPrize> getContestPrizes() {
		return contestPrizes;
	}

	public void setContestPrizes(Set<ContestPrize> prizes) {
		this.contestPrizes = prizes;
	}

	public Set<Gallery> getGalleries() {
		return galleries;
	}

	public void setGalleries(Set<Gallery> galleries) {
		this.galleries = galleries;
	}

}
