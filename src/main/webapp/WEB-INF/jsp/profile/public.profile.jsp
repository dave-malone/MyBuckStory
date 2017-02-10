<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div class="profile-container">
	<div id="myprofile">
	    <div class="main-t2 clearfix">
	        <div class="profile-main">
	           	<div class="currentPicture">
	           		<h2>${member.profile.fullName}</h2>
	           		<center>
		           		<a href="<c:url value="/image.html?imageId=${member.profile.image.id}&album=profile" />" title="View Profile Picture" id="profile-thumb" >
		           			<mbs:imageUri id="profileImageUri" imageId="${member.profile.image.id}" maxWidthAndHeight="180"/>
		        			<img alt="${member.profile.fullName}" src="${profileImageUri}" />
		        		</a>
		        	</center>	        		        			
               		<mbs:ifNotFriends member="${member}">	               			
	               		<span style="margin-top: 1em" class="yui-button yui-push-button">
	               			<span class="first-child">	                				
	               				<button type="button" class="addConfirmation addFriendButton" name="addFriend" id="${member.id}">Add ${member.profile.firstName} as a friend</button>
	               			</span>	
	               		</span>		               		
               		</mbs:ifNotFriends>
               		<mbs:ifFriends member="${member}">
		               	<span style="margin-top: 1em;" class="yui-button yui-push-button">
	               			<span class="first-child">	
	               				<a style="text-decoration: none; color:black;" href="<c:url value="/profile/message/create?KeepThis=true&messageToId=${member.id}&mode=create&TB_iframe=true&height=450&width=750" />" class="thickbox addFriendButton" title="Send ${member.profile.firstName} a Message">
	               					Send ${member.profile.firstName} a Message</a>
	               			</span>               			
	               		</span>
               		</mbs:ifFriends>
					<div id="guest-profile-tabs">
					    <ul>
					        <li class="guest-side-hd">
					        	<a href="<c:url value="/profile/info/${member.profile.id}/" />" class="view-all"><span>Info</span></a>
					        </li>
					        <li class="guest-side-cont">
					        	<div class="profile-side-container">
				        			<h3><b>Location</b></h3>
									<p>${member.profile.location}</p>
									<c:if test="${member.profile.showBirthday}">
										<h3><b>Birthday</b></h3>
										<p><fmt:formatDate value="${member.profile.dob}" pattern="MMMM d, yyyy"/></p>
									</c:if>
									<h3><b>Fav. Species</b></h3>
									<p>${member.profile.favoriteSpecies}</p>
						        </div>
					        </li>
					        <li class="guest-side-hd">
					        	<a href="<c:url value="/profile/badges/${member.profile.id}/" />" class="view-all"><span>Recent Badges</span></a>
					        </li>
					        <li class="guest-side-cont">
					        	<div class="profile-side-container">					        		
						        	<ol>		
										<c:forEach items="${recentBadges}" var="badge" end="4">
											<mbs:imageUri id="recentBadgeImageUri" imageId="${badge.image.id}" maxWidthAndHeight="70"/>
											<li><img src="${recentBadgeImageUri}" alt="${badge.name}" /></li>															
										</c:forEach>
										<c:forEach items="${recentPrizes}" var="contestPrize"> 
											<mbs:imageUri id="recentContestPrizeImageUri" imageId="${contestPrize.prize.image.id}" maxWidthAndHeight="70"/>
											<li><img src="${recentContestPrizeImageUri}" alt="${contestPrize.prize.title}" /></li>
										</c:forEach>
									</ol>
						        	<a href="<c:url value="/profile/badges/${member.profile.id}/" />" class="view-all">View All >></a>
					        	</div>
					        </li>
					        <li class="guest-side-hd">
					        	<a href="<c:url value="/profile/stories/${member.profile.id}/" />" class="view-all"><span>Recent Stories</span></a>
					        </li>
					        <li class="guest-side-cont">
					        	<div class="profile-side-container">
						        	<ol>		
										<c:forEach items="${recentStories}" var="story">
										<li class="newStory clearfix" style="height: 40px; float: none;">
											<a href="<c:url value="${story.uri}" />" title=""><mbs:previewGenerator continueReadingUrl="" length="20" content="${story.title}"/></a>											
										</li>
										</c:forEach>
									</ol>
						        	<a href="<c:url value="/profile/stories/${member.profile.id}/" />" class="view-all">View All >></a>
					        	</div>
					        </li>
					        <li class="guest-side-hd">
					        	<a href="<c:url value="/profile/photos/${member.profile.id}/" />" class="view-all"><span>Recent Photos</span></a>
					        </li>
							<li class="guest-side-cont" style="height: 190px;">
								<div class="profile-side-container" style="margin-bottom:20px;">
									<c:forEach var="image" items="${recentProfilePics}">										
						               <p class="side-thumb">
						               	<mbs:imageUri id="recentProfilePicsImageUri" imageId="${image.id}" maxWidthAndHeight="70"/>
							        		<img alt="${image.title}" src="${recentProfilePicsImageUri}" />
							        	</p>
									</c:forEach>
									<a href="<c:url value="/profile/photos/${member.profile.id}/" />" class="view-all">View All >></a>
								</div>
							</li>
					        <li class="guest-side-hd">
					        	<a href="<c:url value="/profile/friends/${member.profile.id}/" />" class="view-all"><span>Friends</span></a>
					        </li>
					        <li class="guest-side-cont">
					        	<div class="profile-side-container">
									<c:forEach items="${randomFriends}" var="friend">
										<p class="side-thumb">
											<a href="<c:url value="/profile/show/${friend.id}" />" alt="View ${friend.profile.fullName}'s Profile">
												<mbs:imageUri id="friendsProfileImageUri" imageId="${friend.profile.image.id}" maxWidthAndHeight="50"/>
							        			<img alt="${friend.profile.fullName}" src="${friendsProfileImageUri}" />
							        		</a>
							        	</p>
									</c:forEach>
						        	<a href="<c:url value="/profile/friends/${member.profile.id}/" />" class="view-all">View All >></a>
					        	</div>
							</li>
					    </ul>
					</div>					              		               	
	        	</div> 	        		        	
	        </div>
	        <div id="current-status">
	        	<h2>${member.profile.firstName} is Currently...</h2>
	        	<p class="status" id="curr-status">
        			<fmt:formatDate value="${post.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
        			${post.message}
        		</p>
	        	<c:forEach items="${post.comments}" var="comment" begin="${fn:length(post.comments)}">
		        	<p class="status-comment" id="curr-status-comment">
		        		<mbs:imageUri id="memberFeedStatusCommentProfileImageUri" imageId="${comment.createdBy.profile.image.id}" maxWidthAndHeight="50"/>	        	
		        		<img class="user-thumb" alt="${comment.createdBy.profile.fullName}" src="${memberFeedStatusCommentProfileImageUri}" />
			        	<b><a href="<c:url value="/profile/show/${comment.createdBy.id}/" />" />${comment.createdBy.profile.fullName}</a></b>
			        	<fmt:formatDate value="${comment.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
			        	${comment.text}
		        	</p>
	        	</c:forEach>		        
	        	<p style="float: right;"><a id="view-wall" href="<c:url value="/profile/wall/${member.profile.id}/" />">View Wall</a></p>
	        </div>
	        
			<form id="friend-status">
		        <input type="text" id="write-wall" name="write_wall" value="Write on ${member.profile.fullName}'s Wall" style="margin-left: 30px; width: 300px;" />
		        <input type="hidden" id="tpi" name="tpi"  value="${member.id}" />
		        <button type="submit" style="float: right; margin-right: 5px;" />Update</button>
	        </form>
	        
	        <div id="profile-content">
	        	<tiles:insertAttribute name="profile.content" />
	        </div>
		</div>
	</div><!-- End #myprofile -->
</div><!-- End .main-content -->