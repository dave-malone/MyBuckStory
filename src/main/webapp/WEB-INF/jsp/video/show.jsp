<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<jsp:useBean id="video" scope="request" class="com.mybuckstory.model.Video"/>
	<c:choose>
		<c:when test="${not empty video}">			
			<span style="float: left; height: 30px; width: 670px;">
				<c:if test="${not empty param.previousPage}">
					<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">Back to ${param.previousPageTitle}</a>
				</c:if>
				&nbsp;&nbsp;&nbsp; 
				<a href="<c:url value="/profile/show/${video.createdBy.id}" />" title="">${video.createdBy.profile.firstName}'s Profile</a>
			</span>
			<div style="width: 720px; border-bottom: 1px solid #ccc; border-top: 1px solid #ccc; padding: 4px 0; height: 20px; float: left; margin-bottom: 10px;">
				<span style="float: left; margin-right:5px;">					
					<script type="text/javascript" src="http://w.sharethis.com/button/sharethis.js#publisher=e1921c98-90aa-430b-abd9-d5c5ea612ca9&amp;type=website&amp;style=rotate">
				</script>
			</div>
			<div style="margin: 1em 0; clear:both">
				<h1>${video.title}</h1>
				<span style="margin-bottom: 0.75em; background: ##34383E;" class="gray">
					Uploaded by ${video.createdBy.profile.fullName}				
					on <fmt:formatDate value="${video.dateCreated}" pattern="MMMM d, yyyy 'at' hh:mma"/>
				</span>
				<p class="smallText"></p>
				<div>
					<c:if test="${empty video.encodedFileName}">
		            	<div class="message">
			        		This video is still being processed.  Please check back in a few minutes.  Please note that larger videos can take longer to process.  If you feel that you 
			        		are still seeing this message and it has been more than a few hours since this video was uploaded, please contact us.
			        	</div>
			        </c:if>   
			        <c:if test="${not empty video.encodedFileName}">
			        	<a href="<c:url value="/static/video/encoded/${video.encodedFileName}" />"
						   style="display:block;width:650px;height:488px"  
						   id="videoPlayer">
						   <img src="<c:url value="/static/video/thumbnail/${video.previewThumbnailFileName}" />" alt="Preview image for the video titled ${video.title}" />
						</a> 
						<script type="text/javascript" src="<c:url value="/flowplayer/flowplayer-3.2.6.min.js" />"></script>
						<script>
							flowplayer("videoPlayer", "<c:url value="/flowplayer/flowplayer-3.2.7.swf" />", {
								//clip: {autoPlay: false} 
							});
						</script>
			        </c:if>
					<p>${video.description}</p>
				</div>
			</div>
			<a name="comments"></a>
			<span style="float:right">
				<script type="text/javascript" src="http://w.sharethis.com/button/sharethis.js#publisher=e1921c98-90aa-430b-abd9-d5c5ea612ca9&amp;type=website&amp;style=rotate"></script>
			</span>
			<p style="clear:both">
				<security:authorize access="isAuthenticated()">
					<h4>Post Comment</h4>
					<form action="<c:url value="/video/comment/${video.id}" />" method="POST">
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
			<c:if test="${not empty video.comments}">
				<ul class="comments">
					<c:forEach items="${video.comments}" var="comment">
						<li>
					       <div style="border: 1px solid black; padding: 3px; height: 40px; width: 40px; margin-right: 3px; float: left;" align="center">
					           <a href="<c:url value="/profile/show/${comment.createdBy.id}" />" title="Visit ${comment.createdBy.profile.firstName}'s profile">
					           		<mbs:imageUri id="commentCreatorProfileImageUri" imageId="${comment.createdBy.profile.image.id}" maxWidthAndHeight="40"/>
					               <img alt="${comment.createdBy.profile.firstName}'s Profile Picture" src="${commentCreatorProfileImageUri}" class="comment-image" />
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
			The video you were looking for does not exist.
		</c:otherwise>
	</c:choose>		