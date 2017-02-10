<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:if test="${not empty sentMessages}">
	<div style="margin-top:25px">	
		<h3>Sent Messages (${sentMessagesCount})</h3>
		<div><mbs:paginate cssClass="sent-messages-pagination-links" total="${sentMessagesCount}" /></div>
		<ol>
			<c:forEach items="${sentMessages}" var="message">
				<li class="newStory clearfix" id="${message.id}" style="border-bottom: 1px solid black; width:480px;">
					<div style="float:left; width: 110px;">
						<a href="<c:url value="/profile/show/${message.to.id}"/>" title="View ${message.to.profile.firstName}'s Profile">
							<mbs:imageUri id="messageToProfileImageUri" imageId="${message.to.profile.image.id}" maxWidthAndHeight="50"/>
							<img alt="${message.to.profile.fullName}" src="${messageToProfileImageUri}" />
					    </a>
				    </div>
				    <div style="float:left">
				    	To <a href="<c:url value="/profile/show/${message.to.id}" />" title="View ${message.to.profile.firstName}'s Profile">
							${message.to.profile.fullName}</a>
						<br />
						<fmt:formatDate value="${message.dateCreated}" pattern="MMMM dd 'at' hh:mma"/> 
						<br />		 
						Subject: <a href="<c:url value="/profile/message/${message.id}?KeepThis=true&TB_iframe=true&height=450&width=750#mostRecent" />" class="thickbox" title="Read Message">
							${message.subject}</a>
						<br />
						<br />
						<a href="<c:url value="/profile/message/${message.id}?KeepThis=true&TB_iframe=true&height=450&width=750#mostRecent" />" class="thickbox" title="Read Message">Read Message</a>
						&nbsp;&nbsp;
						<a href="" title="" onclick="return deleteSentMessage('${message.id}');">Delete Message</a>							
					</div>
					<div style="clear:both"></div>					
				</li>
			</c:forEach>
		</ol>
		<div><mbs:paginate cssClass="sent-messages-pagination-links" total="${sentMessagesCount}" /></div>
	</div>
</c:if>