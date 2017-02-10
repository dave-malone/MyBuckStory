<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<mbs:getCurrentUser id="currentUser"/>
<c:set var="start" value="${not empty param.start ? param.start : 0}" />

<h1>${image.title}</h1>

<div id="single-image-top">
	<c:if test="${not empty param.previousPage}">
		<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">&laquo; Back to ${param.previousPageTitle}</a>
	</c:if>
	<c:if test="${param.album == 'all'}">
		<c:set var="start" value="${imageTotal - imageIndex > 1 ? start : (start != 0 ? start + 60 : 60)}" />
		<a href="<c:url value="/image/list?max=${param.max}&start=${start}" />" id="top-breadcrumb-left">&laquo; Back to All Pictures</a>
		<p id="top-breadcrumb-right">You are browsing All Pictures</p>
	</c:if>
	<c:if test="${param.album == 'album'}">
		<p id="top-breadcrumb-right">You are browsing ${param.album}</p>
	</c:if>
	<c:if test="${image.type == 'NEWS_ARTICLE' && not empty image.article.title}">
		<a href="<c:url value="${image.article.uri}" />" title="" id="top-breadcrumb-left">&laquo; Back To ${image.article.title}</a>
		<p id="top-breadcrumb-right">${image.article.title} Photos</p>
	</c:if>		
	<c:if test="${image.type == 'STORY' && not empty image.story.title}">
		<a href="<c:url value="${image.story.uri}" />" title="" id="top-breadcrumb-left">&laquo; Back To ${image.story.title}</a>
		<p id="top-breadcrumb-right">${image.story.title} Photos</p>
	</c:if>
</div>

<div id="single-image-contain" <c:if test="${image.type == 'ALBUM'}">class="from-gallery"</c:if>>
	<c:if test="${not empty prevImageId}">
		<div id="pre-image"><a href="<c:url value="/image.html?imageId=${prevImageId}&album=${param.album}&max=${param.max}&start=${start}" />">&laquo;</a></div>
	</c:if>
	<c:if test="${not empty nextImageId}">
		<div id="nex-image"><a href="<c:url value="/image.html?imageId=${nextImageId}&album=${param.album}&max=${param.max}&start=${start}" />">&raquo;</a></div>
	</c:if>

	<c:if test="${not empty nextImageId}">
		<c:set var="nextImageUrl"><c:url value="/image.html?imageId=${nextImageId}&album=${param.album}&max=${param.max}&start=${start}" /></c:set>
	</c:if>
	
	<c:if test="${not empty nextImageUrl}"><a href="${nextImageUrl}" title="Click to view the next image" style="margin: auto;"></c:if>	
		<mbs:imageUri id="imageUri" imageId="${image.id}" width="650" height="457" />
	 	<img alt="${image.title}" src="${imageUri}" />
	<c:if test="${not empty nextImageUrl}"></a></c:if>
	<div id="view-full">	
		<mbs:imageUri id="fullSizeImageUri" imageId="${image.id}" />	
		<a href="${fullSizeImageUri}" >View Full +</a>
	</div>
</div>
<div id="single-image-rotate">
	<c:if test="${not empty prevImageId}">
		<a href="<c:url value="/image.html?imageId=${prevImageId}&album=${param.album}&max=${param.max}&start=${start}" />" id="prev-image">&laquo; Previous Image</a>
	</c:if>
	<c:if test="${not empty nextImageId}">
		<a href="<c:url value="/image.html?imageId=${nextImageId}&album=${param.album}&max=${param.max}&start=${start}" />" id="next-image">Next Image &raquo;</a>
	</c:if>
</div>

<mbs:authorizeToViewProfile user="${image.createdBy}" displayIfAuthorized="${true}">
	<div id="single-admin-bar">
		<li><a class="delete-pic" id="${image.id}">Delete Photo</a></li>
		<li><a href="<c:url value="/image/edit/${image.id}" />" class="edit-pic-title" id="${image.id}">Edit Image Details</a></li>
		<li><a class="make-profile-pic" id="${image.id}">Make Profile Picture</a></li>
	</div>
</mbs:authorizeToViewProfile>
<div id="image-comments">
	<div style="margin-top:10px;">
		<div style="border: 1px solid black; padding: 3px; height: 40px; width: 40px; margin-right: 3px; float: left;" align="center">
		    <a href="<c:url value="/profile/show/${image.createdBy.id}" />" title="Visit ${image.createdBy.profile.firstName}'s profile">
		    	<mbs:imageUri id="commenterProfilePictureUri" imageId="${image.createdBy.profile.image.id}" maxWidthAndHeight="40"/>
		        <img alt="${image.createdBy.profile.firstName}'s Profile Picture" src="${commenterProfilePictureUri}" class="comment-image" />
		    </a>
		</div>		
		<div>
			Uploaded by 
		    <a href="<c:url value="/profile/show/${image.createdBy.id}" />" title="Visit ${image.createdBy.profile.firstName}'s profile">
		        ${image.createdBy.profile.fullName}
		    </a>
		    on <fmt:formatDate value="${image.dateCreated}" pattern="MMM dd yyyy 'at' h:mm a"/>
		    <c:if test="${image.type == 'ALBUM' && not empty image.album.name}">
				<p>In the album <a href="<c:url value="/image/album/show/${image.album.id}" />">${image.album.name}</a></p>
			</c:if>
			<c:if test="${image.type == 'PROFILE' || image.type == 'null' || image.type == null}">
				<p>In <a href="<c:url value="/profile/photos/${image.createdBy.id}" />">${image.createdBy.profile.firstName}${fn:endsWith(image.createdBy.profile.firstName, 's') ? '\'' : '\'s'} Profile Pictures</a></p>
			</c:if>
			<c:if test="${image.type == 'STORY' && not empty image.story.title}">
				<p>Story Photo for <a href="<c:url value="${image.story.uri}" />">${image.story.title}</a></p>
			</c:if>
		</div>
	</div>
	<div style="clear:both; margin-top:40px;margin-bottom:20px;">
		<p>Tags: ${not empty image.tags ? image.tags : 'Not tagged'}</p>
		<p>${image.description}</p>		
	</div>
	<security:authorize access="isAuthenticated()">
		<div id="like-image">
			<c:if test="${fn:length(image.likes) > 0}">
				<a href=""><span id="image-like-text">${fn:length(image.likes)} People Like this!</span></a>
			</c:if>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="" class="like-this-image" id="${image.id}">Like This</a>
		</div>
	</security:authorize>
	<div class="add-comment">
		<security:authorize access="isAuthenticated()">
			<form id="image-comment-form" action="<c:url value="/image/comment/${image.id}" />" method="POST">
				<input type="text" id="comment-text" name="text"/>
				<input type="submit" value="Comment" id="comment-submit" />
			</form>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			You must be a member to post comments.  Please <a href="<c:url value="/register/signup?login_required=1" />">sign in</a>, or <a href="/register/signup">sign up</a>.
		</security:authorize>
	</div>
	<span id="image-comments">
		<c:forEach items="${image.comments}" var="comment">
			<div class="single-comment">
				<mbs:imageUri id="commenterProfilePictureUri" imageId="${comment.createdBy.profile.image.id}" maxWidthAndHeight="180"/>
				<img src="${commenterProfilePictureUri}" />
				<div class="single-comment-info">
					<a href="<c:url value="/profile/show/${comment.createdBy.id}" />">${comment.createdBy.profile.fullName}</a> 
					on <fmt:formatDate value="${comment.dateCreated}" pattern="MMMM d, yyyy"/>
				</div>
				<p>${comment.text}</p>
				<div class="clear"></div>
			</div>
		</c:forEach>
	</span>
</div>	
