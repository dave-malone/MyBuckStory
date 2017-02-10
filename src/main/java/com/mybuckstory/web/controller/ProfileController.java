package com.mybuckstory.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mybuckstory.core.service.AlbumService;
import com.mybuckstory.core.service.FriendRequestService;
import com.mybuckstory.core.service.ImageService;
import com.mybuckstory.core.service.MemberFeedService;
import com.mybuckstory.core.service.MessageService;
import com.mybuckstory.core.service.ProfileService;
import com.mybuckstory.core.service.StoryService;
import com.mybuckstory.core.service.UserService;
import com.mybuckstory.core.service.WallPostService;
import com.mybuckstory.model.Badge;
import com.mybuckstory.model.ContestPrize;
import com.mybuckstory.model.Image;
import com.mybuckstory.model.Message;
import com.mybuckstory.model.Profile;
import com.mybuckstory.model.User;
import com.mybuckstory.util.UserUtil;
import com.mybuckstory.web.command.LoginInfoCommand;
import com.mybuckstory.web.command.MemberSearch;
import com.mybuckstory.web.command.ProfileInfo;
import com.mybuckstory.web.command.ProfileSettings;
import com.mybuckstory.web.editor.UserEditor;
import com.mybuckstory.web.validator.LoginInfoCommandValidator;
import com.mybuckstory.web.validator.MessageValidator;
import com.mybuckstory.web.validator.ProfileInfoValidator;

@Controller
public class ProfileController {
	
	private static final Logger logger = Logger.getLogger(ProfileController.class);
	
	@Autowired
	private LoginInfoCommandValidator loginInfoCommandValidator;	
	@Autowired
	private MessageValidator messageValidator;	
	@Autowired
	private ProfileInfoValidator profileInfoValidator;
	

	@Autowired
	private AlbumService albumService;
	@Autowired
	private FriendRequestService friendRequestService;	
	@Autowired
	private ImageService imageService;
	@Autowired
	private MemberFeedService memberFeedService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private StoryService storyService;	
	@Autowired
	private UserService userService;	
	@Autowired
	private WallPostService wallPostService;
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), false));
		binder.registerCustomEditor(User.class, new UserEditor(userService));
	}
	
	private void addMyProfileObjects(ModelAndView mav, User currentUser){
		addMyProfileObjects(mav, currentUser, false, null, null);
	}
	
	private void addMyProfileObjects(ModelAndView mav, User currentUser, boolean includeMemberFeed, Integer feedStart, Integer feedMax) {
		mav.addObject("member", currentUser);
		mav.addObject("isMyProfile", true);
		mav.addObject("post", wallPostService.getMostRecentStatus(currentUser.getId()));
		
		int messageTotal = messageService.getReceivedMessagesCount(currentUser).intValue();
		int friendRequestTotal = friendRequestService.getUnAnsweredRequests(currentUser).size();
		int inboxTotal = messageTotal + friendRequestTotal;
		mav.addObject("inboxTotal", inboxTotal);
		
		if(includeMemberFeed){
			mav.addObject("feed", memberFeedService.getMemberFeed(currentUser.getId(), feedStart, feedMax));
			mav.addObject("totalFeed", memberFeedService.getMemberFeedCount(currentUser.getId()));
		}		
	}
	
	@RequestMapping(value="/profile/badges/{memberId}", method=RequestMethod.GET)
	public ModelAndView badges(@PathVariable("memberId") String memberId){
		ModelAndView mav = show(memberId);
		mav.setViewName(mav.getViewName() + ".badges");
		
		User member = (User)mav.getModel().get("member");

		Set<Badge> badges = profileService.getBadges(member.getProfile().getId());
		Set<ContestPrize> contestPrizes = profileService.getContestPrizes(member.getProfile().getId());
		mav.addObject("badges", badges);
		mav.addObject("contestPrizes", contestPrizes);		
		
		return mav;
	}
	
	@RequestMapping(value="/profile/message/create", method=RequestMethod.GET)
	public ModelAndView createMessage(@RequestParam(value="messageToId") Long messageToId) throws Exception {
		//TODO - ensure that we're authorized
		ModelAndView mav = new ModelAndView("send.message.dialog");
		
		Message message = new Message();
		User messageTo = userService.getById(messageToId);
		message.setTo(messageTo);	
		mav.addObject("command", message);
		
		return mav;
	}
	
	@RequestMapping(value="/profile/message/delete", method=RequestMethod.POST)
	public String deleteMessage(@RequestParam(value="messageId") Long messageId) throws Exception {
		//TODO - ensure that we're authorized
		Message message = messageService.getById(messageId);
		messageService.delete(message);
		//TODO - where to go?
		return "success";
	}
	
	@RequestMapping(value="/profile/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView mav = new ModelAndView("profile.edit");
		
		User currentUser = UserUtil.getCurrentUser();		
		addMyProfileObjects(mav, currentUser);		
		mav.addObject("loginInfo", new LoginInfoCommand(currentUser));
		mav.addObject("profileInfo", new ProfileInfo(currentUser.getProfile()));
		
		return mav;
	}
	
	@RequestMapping(value="/profile/edit/picture", method=RequestMethod.GET)
	public ModelAndView editPicture() throws Exception {
		ModelAndView mav = new ModelAndView("profile.pic");
		
		User currentUser = UserUtil.getCurrentUser();
		addMyProfileObjects(mav, currentUser);
		
		return mav;
	}
	
	@RequestMapping(value="/profile/feed", method=RequestMethod.GET)
	public ModelAndView feed(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max){
		ModelAndView mav = new ModelAndView("profile.feed");
		
		User currentUser = UserUtil.getCurrentUser();		
		addMyProfileObjects(mav, currentUser, true, start, max);
		
		return mav;
	}
	
	@RequestMapping(value="/profile/friends/{memberId}", method=RequestMethod.GET)
	public ModelAndView friends(@PathVariable("memberId") String memberId, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max){
		ModelAndView mav = show(memberId);
		mav.setViewName(mav.getViewName() + ".friends");
		
		User member = (User)mav.getModel().get("member");
		mav.addObject("friends", userService.findAllFriendsPaginated(member.getId(), start, max));
		mav.addObject("totalFriends", userService.getFriendsCount(member.getId()));		
		
		return mav;
	}
	
	@RequestMapping(value="/profile/inbox", method=RequestMethod.GET)
	public ModelAndView inbox(){
		ModelAndView mav = new ModelAndView("profile.inbox");
		
		User currentUser = UserUtil.getCurrentUser();	
		addMyProfileObjects(mav, currentUser);		
		
		mav.addObject("friendRequests", friendRequestService.getUnAnsweredRequests(currentUser));
		mav.addObject("messages", messageService.getMessages(currentUser, 0, 15));
		mav.addObject("messagesCount", messageService.getReceivedMessagesCount(currentUser));
		mav.addObject("sentMessages", messageService.getSentMessages(currentUser, 0, 15));
		mav.addObject("sentMessagesCount", messageService.getSentMessagesCount(currentUser));
		
		return mav;
	}
	
	@RequestMapping(value="/profile/messages/received", method=RequestMethod.GET)
	public ModelAndView receivedMessages(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max){
		ModelAndView mav = new ModelAndView("profile.messages.received");
		
		User currentUser = UserUtil.getCurrentUser();
		mav.addObject("messages", messageService.getMessages(currentUser, start, max));	
		mav.addObject("messagesCount", messageService.getReceivedMessagesCount(currentUser));
		
		return mav;
	}
	
	@RequestMapping(value="/profile/messages/sent", method=RequestMethod.GET)
	public ModelAndView friends(@RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max){
		ModelAndView mav = new ModelAndView("profile.messages.sent");
		
		User currentUser = UserUtil.getCurrentUser();
		mav.addObject("sentMessages", messageService.getSentMessages(currentUser, start, max));
		mav.addObject("sentMessagesCount", messageService.getSentMessagesCount(currentUser));
		
		return mav;
	}
	
	@RequestMapping(value="/profile/info/{memberId}", method=RequestMethod.GET)
	public ModelAndView info(@PathVariable("memberId") String memberId){
		ModelAndView mav = show(memberId);
		mav.setViewName(mav.getViewName() + ".info");		
		return mav;
	}
	
	@RequestMapping(value="/profile/index.html", method=RequestMethod.GET)
	public void legacyProfileView(@RequestParam(value="uid", required=false) Long uid, HttpServletResponse response){
		String targetUrl = "/profile/show/%s";
		
		if(uid == null){
			targetUrl = String.format(targetUrl, "mine");			
		}else{
			targetUrl = String.format(targetUrl, uid);
		}
		
		logger.info("legacy profile view hit - 301 redirecting to " + targetUrl);
		
		response.setStatus(301);
		response.setHeader("Location", response.encodeRedirectURL(targetUrl));
	}
	
	@RequestMapping(value="/profile/photos/{memberId}", method=RequestMethod.GET)
	public ModelAndView photos(@PathVariable("memberId") String memberId){
		ModelAndView mav = show(memberId);		
		mav.setViewName(mav.getViewName() + ".photos");
		
		User member = (User)mav.getModel().get("member");
		mav.addObject("profileImages", imageService.findProfileImages(member.getId()));
		mav.addObject("albums", albumService.findByAuthor(member.getId()));
		mav.addObject("storyImages", imageService.findByAuthorAndType(member.getId(), Image.Type.STORY.toString()));
		
		return mav;
	}
	
	@RequestMapping(value="/profile/stories/{memberId}", method=RequestMethod.GET)
	public ModelAndView stories(@PathVariable("memberId") String memberId, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max){
		ModelAndView mav = show(memberId);
		mav.setViewName(mav.getViewName() + ".stories");
		
		User member = (User)mav.getModel().get("member");
		mav.addObject("stories", storyService.getStoriesPaginated(member.getId(), start, max));
		mav.addObject("totalStories", storyService.getStoryCount(member.getId()));
		
		return mav;
	}
	
	
	
	@RequestMapping(value="/profile/message/{id}", method=RequestMethod.GET)
	public ModelAndView readMessage(@PathVariable("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("read.message.dialog");		
		//TODO - ensure that we're authorized
		Message message = messageService.getById(id);
		mav.addObject("message", message);
		mav.addObject("messageChain", messageService.getMessageChain(message));
		return mav;
	}
	
	@RequestMapping(value="/profile/message/reply", method=RequestMethod.POST)
	public ModelAndView replyToMessage(@RequestParam(value="messageToId", required=false) Long messageToId, @RequestParam(value="inResponseToId", required=false) Long inResponseToId, Message message, BindingResult result) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(messageToId != null){
			User messageTo = userService.getById(messageToId);
			message.setTo(messageTo);
		}
		
		if(inResponseToId != null){
			messageValidator.validate(message, result);
			if(result.hasErrors()){
				mav.addObject("command", message);
				mav.setViewName("send.message.dialog");
			}else{
				messageService.reply(inResponseToId, message);
				mav.setViewName("message.sent.dialog");
			}
		}
		
		return mav;
	}
	
	@RequestMapping(value="/profile/makeProfilePic/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String makeProfilePic(@PathVariable(value="id") Long imageId){
		if(imageId == null){
			throw new IllegalArgumentException("imageId may not be null");
		}
		
		
		try {
			User currentUser = UserUtil.getCurrentUser();			
			if(currentUser == null){
				return "You must be logged in to change your profile picture";
			}
			
			userService.refresh(currentUser);					
			Profile profile = currentUser.getProfile();			
			
			Image image = imageService.getById(imageId);			
			if(image == null){
				return imageId + "is not a valid image id";
			}
			
			currentUser.getProfile().setImage(image);			
			profileService.changeProfilePicture(profile);
			
			return "success";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@RequestMapping(value="/members.html", method=RequestMethod.GET)
	public ModelAndView search(MemberSearch search) {
		ModelAndView mav = new ModelAndView("members");
		
		List<User> members = userService.search(search);
		Long memberCount = userService.getSearchCount(search);
		
		mav.addObject("members", members);
		mav.addObject("totalMembers", memberCount);
		
		return mav;
	}
	
	private ModelAndView show(String memberId){
		return show(memberId, null, null);
	}
	
	@RequestMapping(value="/profile/show/{memberId}", method=RequestMethod.GET)
	public ModelAndView show(@PathVariable("memberId") String memberId, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max){
		ModelAndView mav = new ModelAndView();
		
		User currentUser = UserUtil.getCurrentUser();
		mav.addObject("currentUser", currentUser);
		
		if(StringUtils.equalsIgnoreCase("mine", memberId)){
			mav.setViewName("my.profile");
			addMyProfileObjects(mav, currentUser, true, start, max);
			
			return mav;
		}else{
			try{
				User member = userService.getById(Long.valueOf(memberId));
				
				if(member == null){
					mav.setViewName("profile.not.found");
					return mav;
				}
				
				//if we are viewing our own profile...
				if(currentUser != null && currentUser.equals(member)){
					mav.setViewName("my.profile");
					addMyProfileObjects(mav, currentUser, true, start, max);
					
					return mav;
				}else{		
					boolean isAFriend = member.getProfile().getFriends().contains(currentUser);
					//if we are this persons friend, or if we aren't their friend, and their profile is public
					if(isAFriend || (!isAFriend && member.getProfile().isMakePublic())){
						mav.setViewName("public.profile");					
						mav.addObject("member", member);
						mav.addObject("isMyProfile", false);
						mav.addObject("isAFriend", true);
						mav.addObject("recentStories", storyService.getRecentStories(2, member.getId(), null));
						mav.addObject("recentProfilePics", imageService.findProfileImages(member.getId(), 0, 4));
						mav.addObject("randomFriends", userService.findAllFriendsPaginated(member.getId(), 0, 4));
						mav.addObject("post", wallPostService.getMostRecentStatus(member.getId()));
						//default view is the 'wall' view
						mav.addObject("wallPosts", wallPostService.getWallPosts(member.getId(), null, null));
						mav.addObject("totalWallPosts", wallPostService.getWallPostsCount(member.getId()));
						//TODO - add recentPrizes
						mav.addObject("recentBadges", profileService.getBadges(member.getId()));
					}else{
						mav.setViewName("private.profile");					
						mav.addObject("member", member);
						mav.addObject("isMyProfile", false);
						mav.addObject("isAFriend", false);
					}
				}
			}catch(NumberFormatException e){
				mav.setViewName("profile.not.found");		
			}
		}
		
		return mav;
	}	
	
	@RequestMapping(value="/profile/message/submit", method=RequestMethod.POST)
	public ModelAndView submitMessage(Message message, BindingResult result) throws Exception {
		//TODO - ensure that we're authorized
		ModelAndView mav = new ModelAndView();
		messageValidator.validate(message, result);
		if(result.hasErrors()){
			mav.addObject("command", message);
			mav.setViewName("send.message.dialog");
		}else{
			messageService.create(message);
			mav.setViewName("message.sent.dialog");
		}
		
		return mav;
	}

	
	@RequestMapping(value="/profile/updateLoginInfo", method=RequestMethod.POST)
	public ModelAndView updateLoginInfo(LoginInfoCommand loginInfo, BindingResult result) throws Exception {
		ModelAndView mav = new ModelAndView();		
		User currentUser = UserUtil.getCurrentUser();		
		
		loginInfoCommandValidator.validate(loginInfo, result);
		if(result.hasErrors()){
			mav.setViewName("profile.edit");
			addMyProfileObjects(mav, currentUser);
			mav.addObject("loginInfo", loginInfo);
			mav.addObject("profileInfo", new ProfileInfo(currentUser.getProfile()));
			mav.addObject("errorMessage", "Failed to update your Account Info");
		}else{
			if(StringUtils.isNotBlank(loginInfo.getUsername())){
				currentUser.setUsername(loginInfo.getUsername());
			}			
			if(StringUtils.isNotBlank(loginInfo.getNewPass()) && StringUtils.isNotBlank(loginInfo.getNewPassConf()) && StringUtils.isNotBlank(loginInfo.getCurrPass())){
				currentUser.setPassword(loginInfo.getNewPass());
			}			
			
			userService.update(currentUser);
			mav.addObject("submitSuccess", "true");
			mav.addObject("successMessage", "Successfully updated your Account Info");
			mav.setViewName("redirect:edit");
		}		
		
		return mav;	
	}
	
	@RequestMapping(value="/profile/edit/picture", method=RequestMethod.POST)
	public ModelAndView updatePicture(@RequestParam("profilePicture") MultipartFile file, @RequestParam("title") String title, @RequestParam("tags") String tags, @RequestParam("description") String description) {
		ModelAndView mav = new ModelAndView("profile.pic");
		
		User currentUser = UserUtil.getCurrentUser();
		userService.refresh(currentUser);
		addMyProfileObjects(mav, currentUser);
		
		Profile profile = currentUser.getProfile();
		try {			
			profile.setFile(file);
			if(profile.containsNewlyUploadedImage()){
				profile.getImage().setType(Image.Type.PROFILE.toString());
				profile.getImage().setTitle(title);
				profile.getImage().setTags(tags);
				profile.getImage().setDescription(description);
				profileService.changeProfilePicture(profile);
				mav.addObject("successMessage", "Your Profile Pic has been updated");
			}else{
				mav.addObject("errorMessage", "You did not select a photo.  Please select a photo and try again");
			}			
		} catch (IOException e) {
			logger.error("Failed to upload profile picture", e);
			mav.addObject("errorMessage", "Failed to upload profile picture.  If the problem persists, please contact admin@mybuckstory.com");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/profile/updateProfileInfo", method=RequestMethod.POST)
	public ModelAndView updateProfileInfo(ProfileInfo profileInfo, BindingResult result) throws Exception {
		ModelAndView mav = new ModelAndView();		
		User currentUser = UserUtil.getCurrentUser();
		
		profileInfoValidator.validate(profileInfo, result);
		
		if(result.hasErrors()){
			mav.setViewName("profile.edit");
			addMyProfileObjects(mav, currentUser);		
			mav.addObject("loginInfo", new LoginInfoCommand(currentUser));
			mav.addObject("profileInfo", profileInfo);			
			mav.addObject("errorMessage", "There are some issues with the data you entered under Personal Info");
		}else{		
			Profile profile = currentUser.getProfile();
			
			profile.setAbout(profileInfo.getAboutMe());
			profile.setFavoriteGear(profileInfo.getFavGear());
			profile.setFavoriteSpecies(profileInfo.getFavSpecies());
			profile.setFavoriteMovies(profileInfo.getFavMovies());
			profile.setFavoriteMusic(profileInfo.getFavMusic());
			profile.setInterests(profileInfo.getInterests());	
			
			profile.setDob(profileInfo.getDob());
			profile.setLocation(profileInfo.getLocation());
			profile.setGender(profileInfo.getGender());
			profile.setFirstName(profileInfo.getFirstName());
			profile.setLastName(profileInfo.getLastName());
			
			profileService.update(profile);		
			mav.addObject("successMessage", "Succesfully updated your Personal Info");
			mav.setViewName("redirect:edit");
		}
		
		return mav;	
	}	
	
	
	@RequestMapping(value="/profile/updateSettings", method=RequestMethod.POST)
	public ModelAndView updateSettings(ProfileSettings settings) throws Exception {
		Profile profile = UserUtil.getCurrentUser().getProfile();
		
		profile.setMakePublic(settings.isMakePublic());
		profile.setShowBirthday(settings.isShowBirthday());
		profile.setDisableAllEmailNotifications(settings.isDisableAllEmailNotifications());
		
		profileService.update(profile);
		
		ModelAndView mav = new ModelAndView("redirect:edit");
		mav.addObject("successMessage", "Succesfully updated your Settings");
		
		return mav;	
	}
	
	@RequestMapping(value="/profile/wall/{memberId}", method=RequestMethod.GET)
	public ModelAndView wall(@PathVariable("memberId") String memberId, @RequestParam(value="start", required=false) Integer start, @RequestParam(value="max", required=false) Integer max){
		ModelAndView mav = show(memberId);
		mav.setViewName(mav.getViewName() + ".wall");
		
		User member = (User)mav.getModel().get("member");		
		mav.addObject("wallPosts", wallPostService.getWallPosts(member.getId(), start, max));
		mav.addObject("totalWallPosts", wallPostService.getWallPostsCount(member.getId()));
		
		return mav;
	}
	
}
