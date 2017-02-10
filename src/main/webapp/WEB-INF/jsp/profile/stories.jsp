<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>Stories by ${member.profile.firstName}</h2>
<div id="myStories" style="padding: 2px;">
	<mbs:paginate total="${totalStories}" cssClass="view-all"/>	                        		
	<ol>		
		<c:forEach items="${stories}" var="story">
			<li class="newStory clearfix">
			    <a href="<c:url value="${story.uri}" />" title="${story.title}">
			    	<mbs:imageUri id="storyImageUri" imageId="${story.image.id}" maxWidthAndHeight="80"/>
					<img alt="${story.title}" src="${storyImageUri}" />
			    </a>
				<span style="float: left;">
				    <a href="<c:url value="${story.uri}" />" title="">${story.title}</a>						            
					<span class="gray">Posted on						            	
						<fmt:formatDate value="${story.dateCreated}" pattern="M/d/yyyy 'at' h:mma"/> 
					</span><br />
				</span>
			    <mbs:authorizeToViewProfile user="${member}" displayIfAuthorized="true">
				    <a href="<c:url value="/story/edit/${story.id}" />" title="Edit This Story">
						Edit this story
					</a>				
			    </mbs:authorizeToViewProfile>
			</li>
		</c:forEach>
	</ol>
	<mbs:paginate total="${totalStories}" cssClass="view-all"/>                                   
</div>