<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>Messages (${inboxTotal})</h2>
<div id="myInbox">	
	<c:if test="${inboxTotal == 0}">
		Your inbox is empty.
	</c:if>
	<c:if test="${not empty friendRequests}">
		<div style="margin-top:25px">
			<h3>Pending Friend Requests (${fn:length(friendRequests)})</h3>
			<ol>
				<c:forEach items="${friendRequests}" var="friendReq">
					<li class="newStory clearfix" id="${friendReq.id}">
						<a href="<c:url value="/profile/show/${friendReq.createdBy.id}" />" title="View ${friendReq.createdBy.profile.firstName}'s Profile">
							<mbs:imageUri id="friendRequestCreatedByProfileImageUri" imageId="${friendReq.createdBy.profile.image.id}" maxWidthAndHeight="50"/>
							<img alt="" src="${friendRequestCreatedByProfileImageUri}" />
					    </a>
						<a href="<c:url value="/profile/show/${friendReq.createdBy.id}" />" title="View ${friendReq.createdBy.profile.firstName}'s Profile">
							${friendReq.createdBy.profile.fullName}</a> 
						has added you as a friend. 
						<br />
						Would you like to add 
						<a href="<c:url value="/profile/show/${friendReq.createdBy.id}" />" title="View ${friendReq.createdBy.profile.firstName}'s Profile">
							${friendReq.createdBy.profile.firstName}</a>
						as a friend?
						<a href="" title="" onclick="return respond('${friendReq.id}', 'true');">Yes</a> 
						<a href="" title="" onclick="return respond('${friendReq.id}', 'false');">No</a>
					</li>
				</c:forEach>
			</ol>
		</div>
	</c:if>
	<div id="messages-received">
		<jsp:include page="/WEB-INF/jsp/profile/messages.received.jsp" />
	</div>
	<div id="messages-sent">
		<jsp:include page="/WEB-INF/jsp/profile/messages.sent.jsp" />
	</div>
</div>