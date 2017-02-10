<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>${member.profile.firstName}${fn:endsWith(member.profile.firstName, 's') ? '\'' : '\'s'} Wall</h2>

<div id="profile-wall" style="clear:both">
	<c:forEach items="${wallPosts}" var="post">
		<div class="log" id="log${post.id}">		
			<p class="wall-thumb">
				<a href="<c:url value="/image.html?imageId=${post.createdBy.profile.image.id}" />" title="View Picture" id="profile-thumb" >
					<mbs:imageUri id="wallPostCreatedByProfileImageUri" imageId="${post.createdBy.profile.image.id}" maxWidthAndHeight="90"/>
		        	<img alt="${post.createdBy.profile.fullName}" src="${wallPostCreatedByProfileImageUri}" />
				</a>
			</p>
			<p class="wall-message">
				<b><a href="<c:url value="/profile/show/${post.createdBy.id}" />" name="View ${post.createdBy.profile.firstName}'s Profile" class="wall-user">${post.createdBy.profile.fullName}</a></b>
				<fmt:formatDate value="${post.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
				${post.message}
			</p>
			<p>
				<a href="" class="comment-wall-post" id="wpc${post.id}">Comment</a>
				&nbsp;&nbsp;&nbsp;<a href="" class="like-wall-post" id="wpl${post.id}">Like</a>
			</p>				
		</div>		
		<div class="post-comments" id="post-comments${post.id}">
			<span id="${post.id}likes">
				<c:forEach var="like" items="${post.likes}">
					<p class="post-like" id="wpls${post.id}">
						<a href="<c:url value="/profile/show/${like.PK.createdBy.id}" />">${like.PK.createdBy.profile.fullName}</a> Likes This
						<fmt:formatDate value="${like.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/>
					</p>
				</c:forEach>
			</span>
			<span id="${post.id}comments">
				<c:forEach var="comment" items="${post.comments}">
					<div class="post-single-comment">
						<p><a href="<c:url value="/profile/show/${comment.createdBy.id}" />">${comment.createdBy.profile.fullName}</a> - Posted <fmt:formatDate value="${comment.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/></p>
						<p>${comment.text}</p>
					</div>
				</c:forEach>
			</span>
			<div class="post-single-comment-form" id="wpcfdiv${post.id}" style="visibility: hidden">
				<form class="wall-post-comment-form" id="wpcf${post.id}">
					<input type="text" name="comment-text" id="comment-text${post.id}" style="width: 350px;" />						                        
	                <button>Comment</button>
                </form>                    
			</div>
		</div>
	</c:forEach>
	<mbs:paginate total="${totalWallPosts}"/>
</div>