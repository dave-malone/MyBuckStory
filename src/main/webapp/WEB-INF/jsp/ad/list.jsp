<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
	<a href="<c:url value="/admin.jsp" />">Admin Home</a>
	<br /><br />
	<h2>Ads</h2>
	<br />
	<a href="<c:url value="/ad/create" />" style="float:right">Create new Advertisement</a>
	<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-ads">		
		<tr>
			<th></th>
			<th>Image</th>
			<th>Name</th>
			<th>Website</th>
			<th>Rank</th>
			<th>Enabled</th>			
		</tr>		
	<mbs:adIterator id="ad">
		<tr>
			<td>
				<a href="<c:url value="/ad/edit/${ad.id}" />">Edit</a>
				<form action="delete" method="POST" class="deleteForm">
					<input type="hidden" name="id" value="${ad.id}" />
					<a href="#" class="delete">Delete</a>
				</form>
				<a href="<c:url value="/ad/clicks/${ad.id}" />">View Clicks</a>
			</td>
			<td>
				<mbs:imageUri id="adImageUri" imageId="${ad.image.id}" maxWidthAndHeight="50" />
				<img alt="" src="${adImageUri}" />
			</td>
			<td>${ad.name}</td>
			<td>${ad.website}</td>
			<td>${ad.rank}</td>
			<td>${ad.enabled}</td>			
		</tr>			    
	</mbs:adIterator>
	</table>