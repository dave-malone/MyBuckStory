<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:if test="${not empty messages}">
	<div style="margin-top:25px">	
		<h3>Received Messages (${messagesCount})</h3>
		<div><mbs:paginate cssClass="received-messages-pagination-links" total="${messagesCount}" /></div>
		<ol>
			<c:forEach items="${messages}" var="message">
				<li class="newStory clearfix" id="${message.id}" style="border-bottom: 1px solid black; width:480px;">
					<div style="float:left; width: 110px;">
						<a href="<c:url value="/profile/show/${message.createdBy.id}"/>" title="View ${message.createdBy.profile.firstName}'s Profile">
							<mbs:imageUri id="messageCreatedByProfileImageUri" imageId="${message.createdBy.profile.image.id}" maxWidthAndHeight="50"/>
							<img alt="${message.createdBy.profile.fullName}" src="${messageCreatedByProfileImageUri}" />
					    </a>
				    </div>
				    <div style="float:left">
				    	From <a href="<c:url value="/profile/show/${message.createdBy.id}" />" title="View ${message.createdBy.profile.firstName}'s Profile">
							${message.createdBy.profile.fullName}</a>
						<br />
						<fmt:formatDate value="${message.dateCreated}" pattern="MMMM dd 'at' hh:mma"/> 
						<br />		 
						Subject: <a href="<c:url value="/profile/message/${message.id}?KeepThis=true&TB_iframe=true&height=450&width=750#mostRecent" />" class="thickbox" title="Read Message">
							${message.subject}</a>
						<br />
						<br />
						<a href="<c:url value="/profile/message/${message.id}?KeepThis=true&TB_iframe=true&height=450&width=750#mostRecent" />" class="thickbox" title="Read Message">Read Message</a>
						&nbsp;&nbsp;
						<a href="" title="" onclick="return deleteMessage('${message.id}');">Delete Message</a>
					</div>
					<div style="clear:both"></div>					
				</li>
			</c:forEach>
		</ol>
		<div><mbs:paginate cssClass="received-messages-pagination-links" total="${messagesCount}" /></div>
	</div>
</c:if>