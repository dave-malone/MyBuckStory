<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<h2>Partners</h2>
<a href="<c:url value="/affiliate/create/" />">Create new Partner</a>
<table width="100%" cellpadding="5" cellspacing="1" id="admin-affil">
	<thead>
		<tr>
			<th></th>
			<th>Image</th>
			<th>Name</th>
			<th>Website</th>					
		</tr>
	</thead>
	<c:forEach items="${affiliates}" var="affiliate">
		<tr>
			<td>
				<a href="<c:url value="/affiliate/edit/${affiliate.id}" />">Edit</a>
				<a href="<c:url value="/affiliate/delete/${affiliate.id}" />">Delete</a>
			</td>
			<td>
				<mbs:imageUri id="affiliateImageUri" imageId="${affiliate.image.id}" maxWidthAndHeight="50"/>
				<img alt="" src="${affiliateImageUri}"/>
			</td>
			<td>${affiliate.name}</td>
			<td>${affiliate.website}</td>						
		</tr>		    
	</c:forEach>
</table>