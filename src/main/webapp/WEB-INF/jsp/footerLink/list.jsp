<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
	<a href="<c:url value="/admin.jsp" />">Admin Home</a>
	<h2>Footer Links</h2>
	<a href="<c:url value="/footerLink/create" />">Create new Footer Link</a>
	<table width="100%" cellpadding="5" cellspacing="1" id="admin-ft-links">
		<thead>
		<tr>
			<th></th>			
			<th>Name</th>
			<th>Category</th>
			<th>URL</th>						
		</tr>
		</thead>
	<mbs:footerLinkIterator id="link">
		<tr>
			<td>
				<a href="<c:url value="/footerLink/edit/${link.id}" />">Edit</a>
				<form action="delete" method="POST" class="deleteForm">
					<input type="hidden" name="id" value="${link.id}" />
					<a href="#" class="delete">Delete</a>
				</form>
			</td>
			<td>${link.name}</td>
			<td>${link.category}</td>
			<td>${link.url}</td>						
		</tr>		    
	</mbs:footerLinkIterator>
	</table>