<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<jsp:useBean id="story" scope="request" class="com.mybuckstory.model.Story"/>
	<c:choose>
		<c:when test="${not empty story}">			
			<span style="float: left; height: 30px; width: 670px;">
				<c:if test="${not empty param.previousPage}">
					<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">Back to ${param.previousPageTitle}</a>
				</c:if>
				&nbsp;&nbsp;&nbsp; 
				<a href="<c:url value="/profile/show/${story.createdBy.id}" />" title="">${story.createdBy.profile.firstName}'s Profile</a>
			</span>
			<div style="width: 720px; border-bottom: 1px solid #ccc; border-top: 1px solid #ccc; padding: 4px 0; height: 20px; float: left; margin-bottom: 10px;">
				<span style="float: left; margin-right:5px;">			  			
					<security:authorize access="isAnonymous()">
						<a href="<c:url value="/register/signup?login_required=1" />">Login</a> to vote
						<c:set var="ratingEnabled"> 
							disabled="disabled"
						</c:set>
					</security:authorize>				
					<security:authorize access="isAuthenticated()">Rate this Story				
						<c:forEach begin="1" end="5" varStatus="status">
							<c:set var="checked">
								<c:if test="${(status.count - 1) < story.averageRating && story.averageRating <= status.count}">
									checked="checked"
								</c:if>
							</c:set>
							<input <security:authorize access="isAuthenticated()">value="${story.id}_${status.count}"</security:authorize> name="storyRating" type="radio" class="star" ${ratingEnabled} ${checked}/>
						</c:forEach>
					</security:authorize>				
					&nbsp;&nbsp;
					<span id="averageRating">
						<c:if test="${not empty story.averageRating}"><fmt:formatNumber value="${story.averageRating}" pattern="#.###"/></c:if>
						<c:if test="${empty story.averageRating}">(Not Yet Rated)</c:if>
					</span>																	
				</span>
				<security:authorize access="isAuthenticated()">
					<span style="float:left; margin-right:5px; cursor:pointer" class="vote" id="${story.id}">
						<p id="story-vote">Vote for this Story!</p>	
					</span>
				</security:authorize>
					<span style="margin: 2px 0 0 10px">
						Story Votes: <c:if test="${not empty story.voteCount}">${story.voteCount}</c:if><c:if test="${empty story.voteCount}">(No Votes Yet)</c:if> 
					</span>
				<script type="text/javascript" src="http://w.sharethis.com/button/sharethis.js#publisher=e1921c98-90aa-430b-abd9-d5c5ea612ca9&amp;type=website&amp;style=rotate">
				</script>
			</div>
			<div style="margin: 1em 0; clear:both">
				<c:url value="/image.html?imageId=${story.image.id}" var="imageUrl">
					<c:param name="previousPage" value="${story.uri}" />
	        		<c:param name="previousPageTitle" value="${story.title}" />
				</c:url>
				<a href="${imageUrl}" title="View full size image">
					<mbs:imageUri id="storyImageUri" imageId="${story.image.id}" maxWidthAndHeight="300"/>
					<img alt="${story.title} thumbnail" src="${storyImageUri}" class="imgLeft" />
				</a>
				<h1>${story.title}</h1>
				<span style="margin-bottom: 0.75em; background: ##34383E;" class="gray">
					Written by ${story.createdBy.profile.fullName}				
					on <fmt:formatDate value="${story.dateCreated}" pattern="MMMM d, yyyy 'at' hh:mma"/> | Categories
                     <c:forEach items="${story.categories}" var="category">
                     	<a href="<c:url value="/stories.html?category=${category.name}" />" title="category">${category.name}</a>
                     </c:forEach> 
				</span>
				<p class="smallText"></p>
				<div>				
					${story.text}
				</div>
			</div>
			
			<script type="text/javascript"><!--
			google_ad_client = "ca-pub-7415535279069157";
			/* &quot;smaller&quot; leader banner */
			google_ad_slot = "8880065299";
			google_ad_width = 468;
			google_ad_height = 60;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
			
			<a name="comments"></a>
			<span style="float:right">
				<script type="text/javascript" src="http://w.sharethis.com/button/sharethis.js#publisher=e1921c98-90aa-430b-abd9-d5c5ea612ca9&amp;type=website&amp;style=rotate"></script>
			</span>
			<p style="clear:both">
				<security:authorize access="isAuthenticated()">
					<h4>Post Comment</h4>
					<form action="<c:url value="/story/comment/${story.id}" />" method="POST">
						<fieldset>
							<textarea class="max" rel="150" name="text"></textarea>
						</fieldset>						
						<span class="yui-button yui-push-button">
                			<span class="first-child">
								<button type="submit">Post Comment</button>
							</span>
						</span>
						<!-- button type="button" id="photoComment2">Post Comment</button-->
					</form>
				</security:authorize>
				<security:authorize access="isAnonymous()">
					You must be a member to post comments.  Please <a href="<c:url value="/register/signup?login_required=1" />">sign in</a>, or <a href="/register/signup">sign up</a>.
				</security:authorize>
			</p>			
			<c:if test="${not empty story.comments}">
				<ul class="comments">
					<c:forEach items="${story.comments}" var="comment">
						<li>
					       <div style="border: 1px solid black; padding: 3px; height: 40px; width: 40px; margin-right: 3px; float: left;" align="center">
					           <a href="<c:url value="/profile/show/${comment.createdBy.id}" />" title="Visit ${comment.createdBy.profile.firstName}'s profile">
					           		<mbs:imageUri id="commenterProfileImageUri" imageId="${comment.createdBy.profile.image.id}" maxWidthAndHeight="40"/>
					               <img alt="${comment.createdBy.profile.firstName}'s Profile Picture" src="${commenterProfileImageUri}" class="comment-image" />
					           </a>
					       </div>
					       <div>
					           <a href="<c:url value="/profile/show/${comment.createdBy.id}" />" title="Visit ${comment.createdBy.profile.firstName}'s profile">
					               ${comment.createdBy.profile.fullName}
					           </a>
					           on <fmt:formatDate value="${comment.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
					           <p>
					              ${comment.text}
					           </p>
				           </div>
					   </li>
				   </c:forEach>
				</ul>
			</c:if>	
		</c:when>
		<c:otherwise>
			The story you were looking for does not exist.
		</c:otherwise>
	</c:choose>		