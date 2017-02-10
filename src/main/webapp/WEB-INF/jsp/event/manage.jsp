<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<br /><br />
<h2>Events</h2>
<br />
<mbs:paginate total="${totalEvents}"/>	
<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-events">		
	<tr>
		<th></th>
		<th>Title</th>
		<th>Event Date</th>
		<th>Author</th>						
	</tr>		
	<c:forEach items="${events}" var="event">
	<tr>
		<td>
			<a href="<c:url value="/event/edit/${event.id}" />">Edit</a>
			<a href="<c:url value="/event/delete/${event.id}" />">Delete</a>
		</td>
		<td>${event.title}</td>
		<td><fmt:formatDate value="${event.date}" pattern="M/d/yyyy 'at' h:mma"/></td>
		<td>${event.createdBy.profile.firstName} ${event.createdBy.profile.lastName}</td>
		<td>${member.username}</td>											
	</tr>			    
	</c:forEach>
</table>