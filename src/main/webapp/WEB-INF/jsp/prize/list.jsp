<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<h2>Prizes</h2>
<a href="<c:url value="/prize/create" />" style="float:right">Create new Prize</a>
<div>
	<mbs:paginate total="${prizeTotal}"/>
</div>
<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-prizes">		
	<tr>
		<th></th>
		<th>Image</th>
		<th>Title</th>			
	</tr>		
	<c:forEach items="${prizeList}" var="prize">
		<tr>
			<td>
				<a href="<c:url value="/prize/edit/${prize.id}" />">Edit</a>
				<form action="delete" method="POST" class="deleteForm">
					<input type="hidden" name="id" value="${prize.id}" />
					<a href="#" class="delete">Delete</a>
				</form>
			</td>
			<td>
				<mbs:imageUri id="prizeImageUri" imageId="${prize.image.id}" maxWidthAndHeight="50"/>
				<img alt="${prize.title}" src="${prizeImageUri}" />
			</td>
			<td>${prize.title}</td>					
		</tr>			    
	</c:forEach>
</table>