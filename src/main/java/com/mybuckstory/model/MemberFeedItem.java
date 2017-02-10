package com.mybuckstory.model;


public class MemberFeedItem extends AbstractAuditable {

	public enum Type{
		AFFILIATE,
		EVENT,
		FRIEND_REQUEST,
		STORY,
		STORY_COMMENT,
		PROFILE_IMAGE,
		ALBUM_IMAGE,
		ALBUM,
		WALL_POST,
		WALL_POST_COMMENT,
		LIKES,
		CONTEST,
		BADGE_AWARD,
		CONTEST_PRIZE, 
		IMAGE_COMMENT,
		VIDEO,
		VIDEO_COMMENT
	}
	
	private String type;
	
	private Affiliate affiliate;
	private Album album;
	private Event event;
	private FriendRequest friendRequest;
	private Story story;
	private Comment storyComment;
	private Image profileImage;
	private Image albumImage;
	private WallPost wallPost;
	private Like like;
	private Comment wallPostComment;
	private Badge badge;
	private ContestPrize contestPrize;
	private Contest contest;
	private Profile profile;
	private Comment imageComment;
	private Image image;
	private Video video;
	private Comment videoComment;

	public MemberFeedItem(){}
		
	public MemberFeedItem(Story story) {
		setStory(story);
	}

	public MemberFeedItem(WallPost post) {
		setWallPost(post);
	}
	
	public MemberFeedItem(Affiliate affiliate) {
		setAffiliate(affiliate);
	}

	public MemberFeedItem(FriendRequest friendRequest) {
		setFriendRequest(friendRequest);
	}

	public MemberFeedItem(Event event) {
		setEvent(event);
	}
	
	public MemberFeedItem(ContestPrize award, Profile profile){
		setContestPrize(award);
		setProfile(profile);
	}
	
	public MemberFeedItem(Contest contest){
		setContest(contest);
	}
	
	public MemberFeedItem(Badge badge, Profile profile){
		setBadge(badge);
		setProfile(profile);
	}

	public Affiliate getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(Affiliate affiliate) {		
		this.affiliate = affiliate;
		if(affiliate != null) setType(Type.AFFILIATE);
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
		if(event != null) setType(Type.EVENT);
	}

	public FriendRequest getFriendRequest() {
		return friendRequest;
	}

	public void setFriendRequest(FriendRequest friendRequest) {
		this.friendRequest = friendRequest;
		if(friendRequest != null) setType(Type.FRIEND_REQUEST);
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
		if(story != null) setType(Type.STORY);
	}

	public Comment getStoryComment() {
		return storyComment;
	}

	public void setStoryComment(Story story, Comment comment){
		setStory(story);
		setStoryComment(comment);
	}
	
	public void setStoryComment(Comment comment) {
		this.storyComment = comment;
		if(comment != null) setType(Type.STORY_COMMENT);
	}

	public void setImageComment(Image image, Comment comment) {
		this.image = image;
		this.imageComment = comment;
		if(comment != null) setType(Type.IMAGE_COMMENT);
	}
	
	public Image getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Image image) {
		this.profileImage = image;
		if(profileImage != null) setType(Type.PROFILE_IMAGE);
	}

	public Image getAlbumImage() {
		return albumImage;
	}

	public void setAlbumImage(Image albumImage) {
		this.albumImage = albumImage;
		if(albumImage != null) setType(Type.ALBUM_IMAGE);
	}

	public String getType() {
		return this.type;
	}

	public void setType(Type type){
		if(type != null){
			this.type = type.name();
		}
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public WallPost getWallPost() {
		return wallPost;
	}

	public void setWallPost(WallPost wallPost) {
		this.wallPost = wallPost;		
	}

	public Like getLike() {
		return like;
	}

	public void setLike(Like like) {
		this.like = like;
		if(like != null) setType(Type.LIKES);
	}

	public Comment getWallPostComment() {
		return wallPostComment;
	}

	public void setWallPostComment(WallPost post, Comment comment){
		setWallPost(post);
		setWallPostComment(comment);
		if(comment != null) setType(Type.WALL_POST_COMMENT);
	}
	
	public void setWallPostComment(Comment comment) {
		this.wallPostComment = comment;		
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
		if(badge != null) setType(Type.BADGE_AWARD);
	}	

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
		if(contest != null) setType(Type.CONTEST);
	}

	public ContestPrize getContestPrize() {
		return contestPrize;
	}

	public void setContestPrize(ContestPrize competitionAward) {
		this.contestPrize = competitionAward;
		if(competitionAward != null) setType(Type.CONTEST_PRIZE);
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Comment getImageComment() {
		return imageComment;
	}

	public void setImageComment(Comment imageComment) {
		this.imageComment = imageComment;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Comment getVideoComment() {
		return videoComment;
	}

	public void setVideoComment(Comment videoComment) {
		this.videoComment = videoComment;
	}
	
	
}
