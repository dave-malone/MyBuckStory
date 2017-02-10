<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>Members</h2>
<form id="memberSearch" action="<c:url value="/members.html" />" method="GET">
	<input type="text" id="memberName" name="memberName" class="italic" value="${not empty param.memberName ? param.memberName : 'Member Name'}" />
	<input type="text" id="location" name="location" class="italic" value="${not empty param.location ? param.location : 'Location'}" />
	<select name="gender">
		<option value="" <c:if test="${empty param.gender}">selected="selected"</c:if>>-Gender-</option>
		<option value="Male" <c:if test="${param.gender == 'Male'}">selected="selected"</c:if>>Male</option>
		<option value="Female" <c:if test="${param.gender == 'Female'}">selected="selected"</c:if>>Female</option>
	</select>
	<input type="hidden" name="newSearch" value="true" />
	<input type="submit" id="searchButton" value="Search" />
</form>
	
<c:if test="${empty members}">
	<div>There weren't any members which matched your search criteria.</div>
</c:if>
<c:if test="${not empty members}">
  	<div class="memberCount" style="margin-top:15px;">
  		<mbs:paginate total="${totalMembers}" params="gender, location, memberName"/>
	</div>   		
    <ul class="members">	    	 	
    	<c:forEach items="${members}" var="member">
    	<li>
            <div class="recent-story-image-page"">
			  <div class="recent-story-image-mid">
			    <div class="recent-story-image-inner">
            	<c:if test="${member.profile.makePublic}">
	                <a href="<c:url value="/profile/show/${member.id}" />" title="">
	                	<mbs:imageUri id="memberProfileImageUri" imageId="${member.profile.image.id}" maxWidthAndHeight="100"/>
                    	<img align="middle" alt="${member.profile.fullName}'s picture" src="${memberProfileImageUri}" />	                    
	                </a>
                </c:if>
                <c:if test="${!member.profile.makePublic}">
                	<mbs:imageUri id="memberProfileImageUri" imageId="${member.profile.image.id}" maxWidthAndHeight="100"/>
	                <img alt="${member.profile.fullName}'s picture" src="${memberProfileImageUri}" />
                </c:if>
                </div>
              </div>
            </div>
            <div class="left">
            	<h4 class="members-grey">            	
	                <a href="<c:url value="/profile/show/${member.id}" />" title="" class="signedOut" style="margin-left: 5px;">
	                    ${member.profile.fullName}
	                </a>                
                </h4>
                <p style="float: left; margin-top: 5px; width: 600px;">
                	<mbs:getRecentStories id="latestStories" storyCount="1" authorId="${member.id}" />
                	<c:forEach items="${latestStories}" var="latestStory">  
	                	<c:if test="${not empty latestStory}">		                		
	                		<c:url value="/members.html" var="prevPageUrl">
                    			<c:param name="start" value="${param.start}"/>
                    			<c:param name="max" value="${param.max}"/>
								<c:param name="gender" value="${param.gender}" />
								<c:param name="location" value="${param.location}" />
								<c:param name="memberName" value="${param.memberName}" />								
                    		</c:url>	                    		
	                		<c:url value="${latestStory.uri}" var="storyUrl">
		                    	<c:param name="previousPage" value="${prevPageUrl}"/>
		                    	<c:param name="previousPageTitle" value="Members" />
		                    </c:url>	                	
	                		
                			Latest story: <a href="${storyUrl}" title="Read ${latestStory.title}">${latestStory.title}</a>
                		</c:if>
               		</c:forEach>
               	</p>                	
                <p class="clearfix" style="margin-top: 5px">	                	
                	<mbs:ifNotFriends member="${member}">
                		<span class="yui-button yui-push-button">
                			<span class="first-child">
                				<button type="button" class="addConfirmation addFriendButton" name="addFriend" id="${member.id}">Add ${member.profile.firstName} as a friend</button>
               				</span>
               			</span>
                	</mbs:ifNotFriends>
                </p>	                
            </div>
        </li>
    	</c:forEach>
    </ul>
    <div class="memberCount" style="margin-top:5px;">
		<mbs:paginate total="${totalMembers}" params="gender, location, memberName"/>
	</div>  
</c:if>        