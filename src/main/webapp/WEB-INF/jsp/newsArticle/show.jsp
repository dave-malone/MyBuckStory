<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<jsp:useBean id="article" scope="request" class="com.mybuckstory.model.NewsArticle"/>
	<c:choose>
		<c:when test="${not empty article}">	
			<c:if test="${not empty param.previousPage}">	
				<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">Back to ${param.previousPageTitle}</a>
			</c:if>
			<span style="float:right">
				<script type="text/javascript" src="http://w.sharethis.com/button/sharethis.js#publisher=e1921c98-90aa-430b-abd9-d5c5ea612ca9&amp;type=website&amp;style=rotate"></script>
			</span> 
			<h2>${article.title}</h2>
			<span class="gray">
				Submitted by ${article.createdBy.profile.fullName} 
				on <fmt:formatDate value="${article.dateCreated}" pattern="MMMM d, yyyy 'at' hh:mma"/>
			</span>
			<p class="smallText">
				<span class="gray">Categories: <c:forEach items="${article.categories}" var="category"> 
					<a href="<c:url value="/news.html?category=${category.name}" />" title="category">
						${category.name}
					</a> 
				</c:forEach></span>
					                    
			</p>
				<Div id="newswrap" style="float: left;">
				<a href="<c:url value="/image.html?imageId=${article.image.id}" />" title="View full size imamge">
					<mbs:imageUri id="newsArticleImageUri" imageId="${article.image.id}" width="160" height="175"/>
					<img alt="${article.title} thumbnail" src="${newsArticleImageUri}" class="imgLeft" />
				</a>
				${article.content}
				</Div>
				
			<a name="comments"></a>
			<span style="float:right">
				<script type="text/javascript" src="http://w.sharethis.com/button/sharethis.js#publisher=e1921c98-90aa-430b-abd9-d5c5ea612ca9&amp;type=website&amp;style=rotate"></script>
			</span>		
			<p style="margin-top:1em;">
				<security:authorize access="isAuthenticated()">
					<h4>Post Comment</h4>
					<form action="<c:url value="/newsArticle/comment/${article.id}" />" method="POST">
						<fieldset>
							<textarea class="max" rel="150" name="text"></textarea>
						</fieldset>												
						<span class="yui-button yui-push-button">
                			<span class="first-child">
								<button type="submit">Post Comment</button>
							</span>
						</span>
					</form>
				</security:authorize>
				<security:authorize access="isAnonymous()">
					You must be a member to post comments.  Please <a href="<c:url value="/register/signup?login_required=1" />">sign in</a>, or <a href="/register/signup">sign up</a>.
				</security:authorize>
				<c:if test="${not empty article.comments}">
					<ul class="comments">
						<c:forEach items="${article.comments}" var="comment">
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
			</p>				
		</c:when>
		<c:otherwise>
			The news article you were looking for does not exist.
		</c:otherwise>
	</c:choose>