<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>Videos</h2>
<%-- 
<div class="message">
  We are bringing video capabilities to all of our users, but for now, we are only in Beta.  A select group of people will be given video upload access during the beta release, 
  but everyone will be able to view and comment on the videos!  If you are interested in participating in our beta release, feel free to email us at admin@mybuckstory.com
</div>
--%>
<div class="memberCount">
	<mbs:paginate total="${totalVideos}"/>
</div>
<ol id="mostRecentStories">    	
	<c:forEach items="${videos}" var="video">
		<c:url value="/videos" var="prevUrl">
			<c:param name="start" value="${param.start}"/>
			<c:param name="max" value="${param.max}"/>			
		</c:url>
		<c:url value="/video/show/${video.id}" var="videoUrl">
        	<c:param name="previousPage" value="${prevUrl}" />
        	<c:param name="previousPageTitle" value="Videos" />
        </c:url> 
 		<li class="clearfix">
 			<div class="recent-story-image-page">
				<div class="recent-story-image-mid">
					<div class="recent-story-image-inner">
						<a href="${videoUrl}" title="Click here to view ${video.title}">
							<c:if test="${not empty video.previewThumbnailFileName}">
								<img align="middle" width="100px" height="100px" src="<c:url value="/static/video/thumbnail/${video.previewThumbnailFileName}" />" alt="View ${video.title}" />
							</c:if>
							<c:if test="${empty video.previewThumbnailFileName}">
								<img align="middle" src="<c:url value="/images/noStoryImage.png" />" alt="View ${video.title}" />
							</c:if>
						</a>
					</div>
				</div>
			</div>
			<div style="float:left; width:600px;">
				<h2><a href="${videoUrl}" title="Click here to view ${video.title}">${video.title}</a></h2>            
				<span class="gray">Posted by 
					<a href="<c:url value="/profile/show/${video.createdBy.id}" />" title="Visit ${video.createdBy.profile.firstName}'s profile">
						${video.createdBy.profile.fullName}
       				</a> 
					on <fmt:formatDate value="${video.dateCreated}" pattern="MMM d yyyy 'at' h:mm a"/> 
				</span>								
			</div>
     	</li>
	</c:forEach>        
</ol>
<div class="videoCount">
	<mbs:paginate total="${totalVideos}" />
</div>                                       