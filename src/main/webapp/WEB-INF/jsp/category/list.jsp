<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
	<a href="<c:url value="/admin.jsp" />">Admin Home</a>

	<h2>Categories</h2>
	<div class="memberCount">
		<mbs:paginate total="${categoryTotal}" />
	</div>
	<a href="<c:url value="/category/create/" />">Create new Category</a>
	<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-cat" >		
		<tr>
			<th></th>
			<th>Parent</th>
			<th>Name</th>
			<th>ID</th>						
		</tr>
	<c:forEach items="${categoryList}" var="category">	
		<tr>
			<td>
				<a href="<c:url value="/category/edit/${category.id}" />">Edit</a>
				<form action="delete" method="POST" class="deleteForm">
					<input type="hidden" name="id" value="${category.id}" />
					<a href="#" class="delete">Delete</a>
				</form>
			</td>			
			<td>
				<c:if test="${not empty category.parent}">${category.parent.name}</c:if>
			</td>
			<td>${category.name}</td>
			<td>${category.id}</td>						
		</tr>			    
	</c:forEach>
	</table>