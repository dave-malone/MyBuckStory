<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<h2>News Articles</h2>
<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-news">		
	<tr>
		<th></th>
		<th>Image</th>
		<th>Title</th>
		<th>Author</th>
		<th>Date Created</th>			
	</tr>		
	<c:forEach items="${newsArticles}" var="news">
	<tr>
		<td>
			<a href="<c:url value="/newsArticle/edit/${news.id}" />">Edit</a>
			<a href="<c:url value="/newsArticle/delete/${news.id}" />">Delete</a>
		</td>
		<td>
			<mbs:imageUri id="newsArticleImageUri" imageId="${news.image.id}" maxWidthAndHeight="50"/>
			<img src="${newsArticleImageUri}" />
		</td>
		<td>${news.title}</td>
		<td>${news.createdBy.profile.firstName} ${news.createdBy.profile.lastName}</td>
		<td><fmt:formatDate value="${news.dateCreated}" pattern="M/d/yyyy 'at' h:mma"/></td>				
	</tr>			    
	</c:forEach>
</table>
	
<mbs:paginate total="${totalNewsArticles}" />