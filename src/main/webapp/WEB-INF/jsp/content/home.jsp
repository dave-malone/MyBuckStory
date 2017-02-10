<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<jsp:include page="/WEB-INF/jsp/common/cache.control.jsp" />
<div class="inner-content clearfix">	
	<div id="dialog2" style="width: 670px; color: #393939; padding: 8px 5px 8px 0; margin: 0.5em 0 1.5em 0; border-top: #e0e0e0 solid 1px; border-bottom: #e0e0e0 solid 1px">
	    <div class="hd">
	        <h2>Welcome to MyBuckStory.com</h2>
	    </div>
	    <div class="bd">
	        <p style="margin:3px;font-size:90%">
	        	Your online bragging board to post photos, share hunting stories and fishing stories, network 
	         	and connect with outdoor enthusiasts, share tips, and stay up to date on the latest outdoor news. 
	         	<a href="<c:url value="/register/signup" />">Join</a> today, membership is free!
	        </p>
	    </div>
	</div> 
    <h1 style="color: black;">
        Hunting Stories - Fishing Stories - Brag Board
    </h1>
   
	<script type="text/javascript"><!--
	google_ad_client = "ca-pub-7415535279069157";
	/* top of the page leaderboard */
	google_ad_slot = "2030458504";
	google_ad_width = 728;
	google_ad_height = 90;
	//-->
	</script>
	<script type="text/javascript"
	src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
	</script>

    
    <div id="featured-stories" class="navset">
    	<h2>Recent Stories</h2>
    	    	
       	<c:forEach items="${recentStories}" var="story" varStatus="status">
       		<div id="main${status.count}" class="featured-container">
				<c:url value="${story.uri}" var="storyUrl">
                    	<c:param name="previousPage" value="/" />
                    	<c:param name="previousPageTitle" value="Home" />
                    </c:url>  
				<div class="recent-story-image">
				  <div class="recent-story-image-mid">
				    <div class="recent-story-image-inner">
						<a href="${storyUrl}" title="${story.title}">
							<mbs:imageUri id="storyImageUri" imageId="${story.image.id}" maxWidthAndHeight="95"/>
							<img alt="${story.title}" src="${storyImageUri}" />
						</a>
					</div>
				  </div>
				</div>
				<div style="width: 555px; float: none; margin-left: 110px; height: 100px;">
                   <h3> 
                       <a href="${storyUrl}" title="${story.title}">
                           ${story.title}</a>                                                       
                   </h3>
                   <p class="smallText">
                       <span class="gray">Posted by 
                       	<a href="<c:url value="/profile/show/${story.createdBy.id}" />" title="Author Name">
                       		${story.createdBy.profile.fullName}</a> 
                       	on <fmt:formatDate value="${story.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
                        | 
                       <span class="gray" style="display: inline;">Categories</span>
                       <c:forEach items="${story.categories}" var="category">
						<a href="<c:url value="/stories.html?category=${category.name}" />" title="category">${category.name}</a>
                       </c:forEach>
                       </span>                                                                                                       
                   </p>
                   <p style="margin-top:3px;">
                   	<mbs:previewGenerator content="${story.text}" length="250" continueReadingUrl="${storyUrl}"/>                  	                                                                             
                   </p>
                 </div>
           	</div><!-- End #main${status.count} -->
       	</c:forEach>                
    </div><!-- End #featured-stories -->
    
    <div style="margin-top:10px;margin-bottom:10px">
    	<a href="<c:url value="/stories.html?start=3" />" style="font-size:120%; color: #1A3E67; background: white; padding: 2px;">Read More Stories</a>
    </div>  
    
    
    <div id="featured-video" class="navset">
    	<h2>Featured Video</h2>
    	
    	<h3>${featuredVideo.title}</h3>
    	
    	<span style="margin-bottom: 0.75em; background: ##34383E;" class="gray">
			Uploaded by ${featuredVideo.createdBy.profile.fullName}				
			on <fmt:formatDate value="${featuredVideo.dateCreated}" pattern="MMMM d, yyyy 'at' hh:mma"/>
		</span>
    	
    	<a href="<c:url value="/static/video/encoded/${featuredVideo.encodedFileName}" />"
		   style="display:block;width:650px;height:488px"  
		   id="videoPlayer">
		   <img src="<c:url value="/static/video/thumbnail/${featuredVideo.previewThumbnailFileName}" />" alt="Preview image for the video titled ${featuredVideo.title}" />
		</a> 
		
		<a href="<c:url value="/video/show/${featuredVideo.id}#comments" />">Comment on this Video</a>
		<script type="text/javascript" src="<c:url value="/flowplayer/flowplayer-3.2.6.min.js" />"></script>
		<script>
			flowplayer("videoPlayer", "<c:url value="/flowplayer/flowplayer-3.2.7.swf" />", {
				//clip: {autoPlay: false} 
			});
		</script>
    </div>
    
    <div id="member-feed" class="navset">
		<h2>Member Activity</h2>
		<div id="myFeed">
			<c:catch var="exception">
				<ol id="profileFeed">
					<c:forEach items="${memberFeed}" var="feedItem">
						<c:set var="createdBy" value="${feedItem.createdBy.profile}" />
						<!-- id: ${feedItem.id} type: ${feedItem.type} -->
						<c:choose>
							<c:when test="${feedItem.type == 'AFFILIATE'}">
								<c:set var="liClass" value="newsPost" />
								<c:set var="liContent">
									MyBuckStory.com has teamed up with <a href="<c:url value="/affiliates.html" />" title="${feedItem.affiliate.name}">${feedItem.affiliate.name}</a>!
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'ALBUM'}">
								<c:set var="liClass" value="newPhoto" />
								<c:set var="liContent">
									added a new album titled
									<a href="<c:url value="/image/album/show/${feedItem.album.id}" />" title="View ${feedItem.album.name}">
										${feedItem.album.name}
									</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'BADGE_AWARD'}">
								<c:set var="liClass" value="newsPost" />
								<c:set var="liContent">
									has earned the ${feedItem.badge.name} ${fn:endsWith(feedItem.badge.name, 'Badge') ? '' : 'Badge'}
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'CONTEST'}">
								<c:set var="liClass" value="newsPost" />
								<c:set var="liContent">
									MyBuckStory.com has announced a new contest! <a href="<c:url value="${feedItem.contest.uri}" />" title="${feedItem.contest.title}">${feedItem.contest.title}</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'CONTEST_PRIZE'}">
								<c:set var="liClass" value="newsPost" />
								<c:set var="liContent">
									has won the Prize ${feedItem.contestPrize.prize.title} from the Contest <a href="<c:url value="${feedItem.contestPrize.contest.uri}" />" title="${feedItem.contest.title}">${feedItem.contestPrize.contest.title}</a>							
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'EVENT'}">
								<c:set var="liClass" value="newEvent" />
								<c:set var="liContent">
									added a new event: <a href="<c:url value="${feedItem.event.uri}" />" title="${feedItem.event.title}">${feedItem.event.title}</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'FRIEND_REQUEST'}">
								<c:set var="liClass" value="addFriend" />
								<c:set var="liContent">
									and 
									<a href="<c:url value="/profile/show/${feedItem.friendRequest.createdBy.id}" />" title="View ${feedItem.friendRequest.createdBy.profile.firstName}'s Profile">${feedItem.friendRequest.createdBy.profile.fullName}</a> 
									are now friends
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'IMAGE_COMMENT'}">
								<c:set var="liClass" value="storyComment" />
								<c:set var="liContent">
									commented on  
									<a href="<c:url value="/image.html?imageId=${feedItem.image.id}" />" title="${feedItem.image.title}">
										an image
									</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'LIKES'}">
								<c:set var="liClass" value="storyComment" />
								<c:set var="liContent">							 
									likes <a href="<c:url value="/profile/show/${feedItem.wallPost.target.id}" />" title="View ${feedItem.wallPost.target.firstName}'s Profile" title="">${feedItem.wallPost.target.fullName}</a>'s status
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'PROFILE_IMAGE'}">
								<c:set var="liClass" value="newPhoto" />
								<c:set var="liContent">
									updated ${feedItem.profile.gender eq 'Male' ? 'his' : 'her'}
									<a href="<c:url value="/image.html?imageId=${feedItem.image.id}" />" title="${feedItem.image.title}">
										profile picture
									</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'STORY'}">
								<c:set var="liClass" value="newStory" />
								<c:set var="createdBy" value="${feedItem.story.createdBy.profile}" />
								<c:set var="liContent">
									shared a new story 
				            		<a href="<c:url value="${feedItem.story.uri}" />" title="${feedItem.story.title}">${feedItem.story.title}</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'STORY_COMMENT'}">
								<c:set var="liClass" value="storyComment" />
								<c:set var="liContent">
									added a comment on 
									<a href="<c:url value="${feedItem.story.uri}" />" title="${feedItem.story.title}">${feedItem.story.title}</a>
									 by <a href="<c:url value="/profile/show/${feedItem.story.createdBy.id}" />" title="View ${feedItem.story.createdBy.profile.firstName}'s Profile" title="">${feedItem.story.createdBy.profile.fullName}</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'VIDEO'}">
								<c:set var="liClass" value="newStory" />
								<c:set var="createdBy" value="${feedItem.video.createdBy.profile}" />
								<c:set var="liContent">
									shared a new video 
				            		<a href="<c:url value="/video/show/${feedItem.video.id}" />" title="${feedItem.video.title}">${feedItem.video.title}</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'VIDEO_COMMENT'}">
								<c:set var="liClass" value="storyComment" />
								<c:set var="liContent">
									commented on the video  
									<a href="<c:url value="/video/show/${feedItem.video.id}" />" title="${feedItem.video.title}">${feedItem.video.title}</a>
								</c:set>
							</c:when>
							<c:when test="${feedItem.type == 'WALL_POST'}">
		                		<c:set var="liClass" value="storyComment" />
		                		<c:set var="liContent">
									<!-- this user has written on my own wall (status update) -->
		                  			<c:if test="${feedItem.wallPost.target.id == feedItem.createdBy.id}">
		                  				updated ${feedItem.wallPost.target.gender eq 'Male' ? 'his' : 'her'} status
		                  		 	</c:if>
									<!-- this user has written on someone else's wall -->
		                   		 	<c:if test="${feedItem.wallPost.target.id != feedItem.createdBy.id}">
		                   		 		wrote on
		                   		 		<a href="<c:url value="/profile/show/${feedItem.wallPost.target.id}" />" title="View ${feedItem.wallPost.target.firstName}'s Profile">${feedItem.wallPost.target.firstName}'s Wall</a>
		                   			</c:if>
		                		</c:set>
		            		</c:when>
							<c:when test="${feedItem.type == 'WALL_POST_COMMENT'}">
								<c:set var="liClass" value="storyComment" />
								<c:set var="liContent">
				                  	added a comment on 
									<a href="<c:url value="/profile/show/${feedItem.wallPost.target.id}" />" title="View ${feedItem.wallPost.target.firstName}'s Profile" title="">${feedItem.wallPost.target.fullName}</a>'s status
								</c:set>
							</c:when>
									
						</c:choose>	
						<c:if test="${not empty liContent}">
							<li>	
								<div style="clear:both; padding-top:15px;">					 
									<div style="border:1px solid black; padding:3px; height:55px; width:55px; margin-right:15px; float:left;" align="center">
										<a href="<c:url value="/image.html?imageId=${createdBy.image.id}" />" title="View ${createdBy.firstName}'s Profile" id="profile-thumb">
											<mbs:imageUri id="createdByImageUri" imageId="${createdBy.image.id}" maxWidthAndHeight="50"/>
								        	<img alt="${createdBy.fullName}" src="${createdByImageUri}" />
										</a>
									</div>						
									<div>
										<h2><a href="<c:url value="/profile/show/${createdBy.id}" />" title="View ${createdBy.firstName}'s Profile">${createdBy.fullName}</a></h2>
										<p>${liContent}</p>
										<p><fmt:formatDate value="${feedItem.dateCreated}" pattern="MMMM dd 'at' hh:mma"/></p> 
									</div>
								</div>
							</li>	
						</c:if>
					</c:forEach>
				</ol>
			</c:catch>
			<c:if test="${not empty exception}">
				The activity feed is temporarily unavailable.  We will work on fixing this issue as soon as we can.
			</c:if>
		</div>	
	</div>
    

    <div id="proStaff">
		<h2>Our Pro Staff</h2>
		<ul class="mostActiveMembers">
	     	<mbs:proStaffIterator id="user">
			<li>
	          	<div class="recent-story-image">
				  <div class="recent-story-image-mid">
				    <div class="recent-story-image-inner">
			          	<a href="<c:url value="/profile/show/${user.profile.id}" />" title="View ${user.profile.firstName}'s Profile">
			          		<mbs:imageUri id="proStaffProfilePictureUri" imageId="${user.profile.image.id}" maxWidthAndHeight="80"/>
							<img alt="${user.profile.firstName}'s Profile Picture" src="${proStaffProfilePictureUri}" />
						</a>
				    </div>
				  </div>
				</div>
			</li>
			</mbs:proStaffIterator>
		</ul>
	</div>
	<br /><br />
	<div id="recentlyLoggedIn">
		<h2>Recently Logged On</h2>
		<ul class="recentlyLoggedIn">
	     	<mbs:recentLoginsIterator id="user">
			<li>
	          	<div class="recent-story-image">
				  <div class="recent-story-image-mid">
				    <div class="recent-story-image-inner">
			          	<a href="<c:url value="/profile/show/${user.profile.id}" />" title="View ${user.profile.firstName}'s Profile">
			          		<mbs:imageUri id="recentLoginProfilePictureUri" imageId="${user.profile.image.id}" maxWidthAndHeight="80"/>
							<img alt="${user.profile.firstName}'s Profile Picture" src="${recentLoginProfilePictureUri}" />
						</a>
				    </div>
				  </div>
				</div>
			</li>
			</mbs:recentLoginsIterator>
		</ul>
	</div>
       <br /><br />
	<div id="activeMembers">
		<h2>Most active members</h2>
		<ul class="mostActiveMembers">
	     	<mbs:mostActiveUserIterator id="user" max="12">
			<li>
	          	<div class="recent-story-image">
				  <div class="recent-story-image-mid">
				    <div class="recent-story-image-inner">
			          	<a href="<c:url value="/profile/show/${user.profile.id}" />" title="View ${user.profile.firstName}'s Profile">
			          		<mbs:imageUri id="activeMemberProfilePictureUri" imageId="${user.profile.image.id}" maxWidthAndHeight="80"/>
							<img alt="${user.profile.firstName}'s Profile Picture" src="${activeMemberProfilePictureUri}" />
						</a>
				    </div>
				  </div>
				</div>
			</li>
			</mbs:mostActiveUserIterator>
		</ul>
	</div>   
</div><!-- End #main-content -->
        