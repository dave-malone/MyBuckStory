<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<br /><br />
<h2>Stories</h2>
<br />
<mbs:paginate total="${totalStories}"/>	
<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-stories">		
	<tr>
		<th></th>
		<th>Image</th>
		<th>Title</th>
		<th>Author</th>
		<th>Date Created</th>			
	</tr>		
	<c:forEach items="${stories}" var="story">
	<tr>
		<td>
			<a href="<c:url value="/story/edit/${story.id}" />">Edit</a>
			<form action="/story/delete" method="POST" class="deleteForm">
				<input type="hidden" name="id" value="${story.id}" />
				<a href="#" class="delete">Delete</a>
			</form>
		</td>
		<td>
			<mbs:imageUri id="storyImageUri" imageId="${story.image.id}" maxWidthAndHeight="50"/>
			<img src="${storyImageUri}" />
		</td>
		<td>${story.title}</td>
		<td>${story.createdBy.profile.firstName} ${story.createdBy.profile.lastName}</td>
		<td><fmt:formatDate value="${story.dateCreated}" pattern="M/d/yyyy 'at' h:mma"/></td>				
	</tr>			    
	</c:forEach>
</table>