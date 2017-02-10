<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<h2>Badge</h2>
<a href="<c:url value="/badge/create" />" style="float:right">Create new Badge</a>
<div>
	<mbs:paginate total="${badgeTotal}"/>
</div>
<table width="100%" cellpbadgeding="5" cellspacing="5" border="1" id="admin-badges">		
	<tr>
		<th></th>
		<th>Image</th>
		<th>Name</th>
		<th>Story Category</th>
		<th># Stories Needed</th>
		<th>Description</th>			
	</tr>		
	<c:forEach items="${badgeList}" var="badge">
		<tr>
			<td>
				<a href="<c:url value="/badge/edit/${badge.id}" />">Edit</a>
				<form action="delete" method="POST" class="deleteForm">
					<input type="hidden" name="id" value="${badge.id}" />
					<a href="#" class="delete">Delete</a>
				</form>
			</td>
			<td>
				<mbs:imageUri id="badgeImageUri" imageId="${badge.image.id}" maxWidthAndHeight="50"/>
				<img alt="" src="${badgeImageUri}"/>
			</td>
			<td>${badge.name}</td>
			<td>
				<c:if test="${not empty badge.storyCategory}">
					${badge.storyCategory.name}
				</c:if>
			</td>
			<td>${badge.numberOfStoriesInCategory}</td>			
			<td>${badge.description}</td>					
		</tr>			    
	</c:forEach>
</table>