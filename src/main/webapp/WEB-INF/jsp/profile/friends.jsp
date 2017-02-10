<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>${member.profile.firstName}${fn:endsWith(member.profile.firstName, 's') ? '\'' : '\'s'} Friends</h2>
<div id="myFriends">
	<!-- form id="searchFriends" action="" method="post">
		<input type="text" class="gray italic" value="Search friends" />
		<span class="yui-button yui-push-button">
   			<span class="first-child">
   				<button type="button" name="searchFriends" id="">Search</button>
   			</span>
   		</span>
	</form -->	
	<c:if test="${not empty friends}">
		<mbs:paginate total="${totalFriends}" />
		<ul class="affiliates">
			<c:forEach items="${friends}" var="friend">
				<li id="friend${friend.id}">
		            <div class="imgLeft">
		                <a href="<c:url value="/profile/show/${friend.profile.id}" />" title="Visit ${friend.profile.firstName}'s profile">
		                	<mbs:imageUri id="friendProfileImageUri" imageId="${friend.profile.image.id}" maxWidthAndHeight="80"/>
		                    <img alt="" src="${friendProfileImageUri}" />
		                </a>
		            </div>
		            <div class="left">
		            	<p>
			                <a href="<c:url value="/profile/show/${friend.profile.id}" />" title="Visit ${friend.profile.firstName}'s profile" class="signedOut">
			                    ${friend.profile.fullName}
			                </a>
		                </p>
		                <p>
		                	<mbs:getRecentStories id="latestStories" storyCount="1" authorId="${friend.id}" />
		                    <c:forEach items="${latestStories}" var="latestStory">  
			                	<c:if test="${not empty latestStory}">
		                			Latest story: <a href="<c:url value="${latestStory.uri}" />" title="Read ${latestStory.title}">${latestStory.title}</a>
		                		</c:if>
	                		</c:forEach>	                		
		                </p>
		            </div>
		        </li>
			</c:forEach>
		</ul>
		<mbs:paginate total="${totalFriends}"/>
	</c:if>    
</div>