<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<jsp:useBean id="event" scope="request" class="com.mybuckstory.model.Event"/>
	<c:choose>
		<c:when test="${not empty event}">	
			<c:if test="${not empty param.previousPage}">	
				<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">Back to ${param.previousPageTitle}</a>
			</c:if>
			&nbsp;&nbsp;&nbsp;  
			<a href="<c:url value="/profile/show/${event.createdBy.id}" />" title="">${event.createdBy.profile.firstName}'s Profile</a>
			<h2>${event.title}</h2>
			<span class="gray" style="background: #E3E3E3; margin-bottom: 15px;">
				Written by ${event.createdBy.profile.fullName} 
				on <fmt:formatDate value="${event.date}" pattern="MMMM, d, yyyy"/>
			</span>
			<p>
				${event.description}
			</p>
			<p style="margin-top: 1em">
				<security:authorize access="isAuthenticated()">
					<h4>Post Comment</h4>
					<form action="<c:url value="/event/comment/${event.id}" />" method="POST">
						<fieldset>
							<textarea class="max" rel="150" name="text"></textarea>
						</fieldset>
						<input type="submit" value="Post Comment" />						
					</form>
				</security:authorize>
				<security:authorize access="isAnonymous()">
					You must be a member to post comments.  Please <a href="<c:url value="/register/signup?login_required=1" />">sign in</a>, or <a href="/register/signup">sign up</a>.
				</security:authorize>
			</p>
			<c:if test="${not empty event.comments}">
				<ul class="comments">
					<c:forEach items="${event.comments}" var="comment">
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
			The event you were looking for does not exist.
		</c:otherwise>
	</c:choose>