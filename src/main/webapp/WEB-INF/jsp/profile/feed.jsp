<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<%@ page import="org.apache.log4j.Logger" %>
<h2>Activity Feed</h2>
<div id="myFeed">
	<c:catch var="exception">
		<ol id="profileFeed">
			<c:forEach items="${feed}" var="feedItem">
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
					
					<%--c:when test="${feedItem.type == 'ALBUM_IMAGE'}">
						<c:set var="liClass" value="newPhoto" />
						<c:set var="liContent">
							<a href="" title="">Nick Borgwardt</a> added a new photo to <a href="" title="">My Favorite Football Team</a> photo gallery.<br/>
		            		<a href="" title=""><img alt="" src="/images/vikings.png" /></a>
						</c:set>
					</c:when --%>
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
							commented on the picture  
							<a href="<c:url value="/image.html?imageId=${feedItem.image.id}" />" title="${feedItem.image.title}">
								${feedItem.image.title}
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
							wrote a new story 
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
							<div style="border:1px solid black; padding:3px; height:75px; width:75px; margin-right:15px; float:left;" align="center">
								<a href="<c:url value="/image.html?imageId=${createdBy.image.id}" />" title="View ${createdBy.firstName}'s Profile" id="profile-thumb">
									<mbs:imageUri id="createdByImageUri" imageId="${createdBy.image.id}" maxWidthAndHeight="70"/>
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
		<div style="clear:both; margin-top:10px; text-align: right">			
			<mbs:paginate total="${totalFeed}" />
		</div>
	</c:catch>
	<c:if test="${not empty exception}">
		<% 
			Exception e = (Exception)pageContext.findAttribute("exception");
			Logger logger = Logger.getLogger(getClass());
			logger.warn(e, e);
		%>
		Your feed is temporarily unavailable.  We will work on fixing this issue as soon as we can.
	</c:if>
</div>	