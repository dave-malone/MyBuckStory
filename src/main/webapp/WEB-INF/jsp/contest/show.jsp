<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<c:if test="${empty contest}">
	The contest you were looking for does not exist.
</c:if>

<c:if test="${not empty contest}">	
	<c:if test="${not empty param.previousPage}">	
		<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">Back to ${param.previousPageTitle}</a>
	</c:if>

	<div style="margin: 1em 0; clear:both">
		<mbs:imageUri id="contestImageUri" imageId="${contest.image.id}" maxWidthAndHeight="180"/>
		<img alt="${contest.title}" src="${contestImageUri}" class="imgLeft" />
		<h1>${contest.title}</h1>
		<span style="margin-bottom: 0.75em; background: ##34383E;" class="gray">
			Sponsored By: 
			<c:if test="${not empty contest.sponsor}">
				${contest.sponsor.name} 
			</c:if>
			<c:if test="${empty contest.sponsor}">
				MyBuckStory.com
			</c:if>
			| Contest Period: <fmt:formatDate value="${contest.startDate}" pattern="MMMM, d, yyyy"/>
			 - <fmt:formatDate value="${contest.endDate}" pattern="MMMM, d, yyyy"/>
		</span>
		<p class="smallText"></p>
		<div>						
			${contest.description}
		</div>
		<div>
			<h2>Prizes</h2>
			<c:forEach items="${contest.contestPrizes}" var="contestPrize">
				<a name="prize${contestPrize.prize.id}"></a>								
				<div>
					<c:if test="${not empty contestPrize.prize.image}">
						<mbs:imageUri id="contestPrizePrizeImageUri" imageId="${contestPrize.prize.image.id}" />
						<img src="${contestPrizePrizeImageUri}" alt="${contestPrize.prize.title}" class="imgLeft" />
					</c:if>
					<h3>
						<c:if test="${contestPrize.rank == 1}">1st Place</c:if>
						<c:if test="${contestPrize.rank == 2}">2nd Place</c:if>
						<c:if test="${contestPrize.rank == 3}">3rd Place</c:if>
						<c:if test="${contestPrize.rank > 3}">${contestPrize.rank}th Place</c:if>				
						 - ${contestPrize.prize.title}
					</h3>
					<p>
					${contestPrize.prize.description}
					</p>
				</div>
				<c:if test="${not empty contestPrize.winningStory}">
					<div>
						Winning Story: <a href="<c:url value="${contestPrize.winningStory.uri}" />">${contestPrize.winningStory.title}</a>
					</div>
				</c:if>
				<c:if test="${not empty contestPrize.winningImage}">
					<div>
						Winning Image:
						<br />
						<a href="<c:url value="/image.html?imageId=${contestPrize.winningImage.id}" />">
							<mbs:imageUri id="contestPrizeWinningImageUri" imageId="${contestPrize.winningImage.id}" maxWidthAndHeight="50"/>
							<img src="${contestPrizeWinningImageUri}" alt="${contestPrize.prize.title}" />
						</a>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div>
			<h2>Rules</h2>					
			${contest.rules}
		</div>		
	</div>
	<p style="margin-top: 1em">
		<security:authorize access="isAuthenticated()">
			<h4>Post Comment</h4>
			<form action="<c:url value="/contest/comment/${contest.id}" />" method="POST">
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
	<c:if test="${not empty contest.comments}">
		<ul class="comments">
			<c:forEach items="${contest.comments}" var="comment">
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
</c:if>