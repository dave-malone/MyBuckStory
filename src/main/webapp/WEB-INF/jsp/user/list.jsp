<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<br /><br />
<h2>Users</h2>
<br />
<mbs:paginate total="${userTotal}" params="memberName"/>	
<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-users">		
	<tr>
		<th></th>
		<th>Image</th>
		<th>Name</th>
		<th>Email</th>
		<th>Enabled</th>			
		<th>Signup Date</th>			
	</tr>		
	<c:forEach items="${userList}" var="member">
	<tr>
		<td>
			<a href="<c:url value="/user/edit/${member.id}" />">Edit</a>
			<a href="<c:url value="/user/delete/${member.id}" />">Delete</a>
		</td>
		<td>
			<mbs:imageUri id="userProfilePictureUri" imageId="${member.profile.image.id}" maxWidthAndHeight="50"/>
			<img src="${userProfilePictureUri}" />
		</td>
		<td>${member.profile.firstName} ${member.profile.lastName}</td>
		<td>${member.username}</td>
		<td>${member.enabled}</td>
		<td><fmt:formatDate value="${member.signupDate}" pattern="M/d/yyyy"/></td>								
	</tr>			    
	</c:forEach>
</table>