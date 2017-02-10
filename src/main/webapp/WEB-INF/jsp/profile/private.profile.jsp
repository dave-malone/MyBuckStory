<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div class="profile-container">
	<div id="myprofile">
	    <div class="main-t2 clearfix">
	        <div class="profile-main">
	           	<div class="currentPicture">
	           		<h2>${member.profile.fullName}</h2>
	           		<center>
		           		<a href="<c:url value="/image.html?imageId=${member.profile.image.id}&album=profile" />" title="View Profile Picture" id="profile-thumb" >
		           			<mbs:imageUri id="memberProfileImageUri" imageId="${member.profile.image.id}" maxWidthAndHeight="100"/>
		        			<img alt="${member.profile.fullName}" src="${memberProfileImageUri}" />
		        		</a>
		        	</center>										              		               	
	        	</div> 	        		        	
	        </div>
	        <div id="current-status">
	        	<h2>${member.profile.firstName} is Currently...</h2>
	        	<p class="status" id="curr-status">
        			<fmt:formatDate value="${post.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
        			${post.message}
        		</p>
	        </div>
	        
			<form id="friend-status">
		        <input type="text" id="write-wall" name="write_wall" value="Write on ${member.profile.fullName}'s Wall" style="margin-left: 30px; width: 300px;" />
		        <input type="hidden" id="tpi" name="tpi"  value="${member.id}" />
		        <button type="submit" style="float: right; margin-right: 5px;" />Update</button>
	        </form>
	        
	        <div id="profile-content">
	        	<p style="padding:10px;font-size:x-large;">
	        		${member.profile.firstName} only shares some profile info with everyone. Only friends of ${member.profile.firstName} can view their full profile page.
	        	</p>
	        	<p style="padding:10px;">
		        	<security:authorize access="isAnonymous()">
		        		<a href="<c:url value="/register/signup?login_required=1" />">Sign in</a>, or <a href="/register/signup">Sign up</a> to add ${member.profile.firstName} as a friend	
		        	</security:authorize>
		        	<security:authorize access="isAuthenticated()">
		        		<mbs:ifNotFriends member="${member}">	               			
		               		<span style="margin-top: 1em" class="yui-button yui-push-button">
		               			<span class="first-child">	                				
		               				<button type="button" class="addConfirmation addFriendButton" name="addFriend" id="${member.id}">Add ${member.profile.firstName} as a friend</button>
		               			</span>	
		               		</span>		               		
	               		</mbs:ifNotFriends>
		        	</security:authorize>
	        	</p>
	        </div>
		</div>
	</div><!-- End #myprofile -->
</div><!-- End .main-content -->