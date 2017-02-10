<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<h2>Contests</h2>
<a href="<c:url value="/contest/create/" />">Create new Contest</a>
<table width="100%" cellpadding="5" cellspacing="1" id="admin-affil">
	<thead>
		<tr>
			<th></th>
			<th>Image</th>
			<th>Title</th>
			<th>Start Date</th>
			<th>End Date</th>						
		</tr>
	</thead>
	<c:forEach items="${contests}" var="contest">
		<tr>
			<td>
				<a href="<c:url value="/contest/edit/${contest.id}" />">Edit</a>
				<a href="<c:url value="/contest/delete/${contest.id}" />">Delete</a>
			</td>
			<td>
				<mbs:imageUri id="contestImageUri" imageId="${contest.image.id}" maxWidthAndHeight="50"/>
				<img alt="" src="${contestImageUri}"/>
			</td>
			<td>${contest.title}</td>
			<td><fmt:formatDate value="${contest.startDate}" pattern="MMM, d, yyyy"/></td>
			<td><fmt:formatDate value="${contest.endDate}" pattern="MMM, d, yyyy"/></td>						
		</tr>		    
	</c:forEach>
</table>