<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div class="profile-container">
	<div id="myprofile">
	    <div class="main-t2 clearfix">
	        <div class="profile-main">
	           	<div class="currentPicture">
	           		<h2>${member.profile.fullName}</h2>
	           		<center>
		           		<a href="<c:url value="/image.html?imageId=${member.profile.image.id}&album=profile" />" title="View Profile Picture" id="profile-thumb" >
		           			<mbs:imageUri id="memberProfileImageUri" imageId="${member.profile.image.id}" maxWidthAndHeight="180"/>
		        			<img alt="${member.profile.fullName}" src="${memberProfileImageUri}" />
		        		</a>
		        	</center>	        		
	        		<ul>
	        			<li class="user-side"><a href="<c:url value="/profile/edit/picture" />">Change Profile Pic</a>
               			<li class="user-side"><a href="<c:url value="/image/album/create" />">Create Album</a></li>
               			<li class="user-side"><a href="<c:url value="/profile/edit/" />"><span>Edit Profile</span></a></li>
               		</ul>               		
               		<div id="profile-tabs">
					    <ul>					 			
					    	<li class="user-side"><a href="<c:url value="/profile/feed" />"><span>Feed</span></a></li>
					    	<li class="user-side"><a href="<c:url value="/profile/inbox" />"><span>Messages (${inboxTotal})</span></a></li>		
					    	<li class="user-side"><a href="<c:url value="/profile/badges/mine/" />"><span>Badges & Prizes</span></a></li>				        
					        <li class="user-side"><a href="<c:url value="/profile/wall/mine/" />"><span>Wall</span></a></li>
					        <li class="user-side"><a href="<c:url value="/profile/info/mine/" />"><span>Info</span></a></li>
					        <li class="user-side"><a href="<c:url value="/profile/stories/mine/" />"><span>Stories</span></a></li>
					        <li class="user-side"><a href="<c:url value="/profile/friends/mine/" />"><span>Friends</span></a></li>
					        <li class="user-side"><a href="<c:url value="/profile/photos/mine/" />"><span>Photos</span></a></li>
					    </ul>
					</div>	               		               	
	        	</div> 	        		        	
	        </div>
	        <c:set var="resource"><%= request.getAttribute("javax.servlet.forward.request_uri") %></c:set>
	        <!-- ${resource} -->
	        <c:if test="${!fn:startsWith(resource,'/profile/edit') && !fn:startsWith(resource,'/profile/update')}">
	        	<form id="user-status">
			        <input type="text" id="status" name="user_status" value="What's going on out there?" />
			        <input type="hidden" id="tpi" name="tpi"  value="${member.id}" />
			        <button type="submit" style="float: right; margin-right: 0px;" />Update</button>
		        </form>
		        <div id="current-status">
		        	<h2>${member.profile.firstName} is Currently...</h2>
		        	<p class="status" id="curr-status">
	        			<fmt:formatDate value="${post.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
	        			${post.message}
	        		</p>
	        		<%-- 
		        	<c:forEach items="${post.comments}" var="comment" begin="${fn:length(post.comments)}">
			        	<p class="status-comment" id="curr-status-comment">	        	
			        		<img class="user-thumb" alt="${comment.createdBy.profile.fullName}" src="<c:url value="/streamimage.do?imageId=${comment.createdBy.profile.image.id}&width=50height=50" />" />
				        	<b><a href="<c:url value="/profile/show/${comment.createdBy.id}" />" />${comment.createdBy.profile.fullName}</a></b>
				        	<fmt:formatDate value="${comment.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
				        	${comment.text}
			        	</p>
		        	</c:forEach>
		        	--%>		        
		        	<p style="float: right;"><a id="view-wall" href="<c:url value="/profile/wall/mine/" />">View Wall</a></p>
		        </div>
	        </c:if>
			<div id="profile-content">
	        	<tiles:insertAttribute name="profile.content" />
	        </div>
		</div>
	</div><!-- End #myprofile -->
</div><!-- End .main-content -->