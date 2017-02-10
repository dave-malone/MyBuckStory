<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div class="gallery-forms clearfix">
	<h2>${album.name}</h2>
	<p>
		${album.description}
	</p>
 	
   		<c:forEach items="${album.images}" var="image" varStatus="status">
   			<c:if test="${status.count % 5 == 1}">
			</c:if>
			<div class="gallery-images">
				<a href="<c:url value="/image.html?imageId=${image.id}" />">
					<mbs:imageUri id="imageUri" imageId="${image.id}" maxWidthAndHeight="180"/>
	   				<img src="${imageUri}" alt="${image.title}" title="${image.title}"/>
	   			</a>
			</div>	
			<c:if test="${status.last || status.count % 5 == 0}">
			</c:if>					
		</c:forEach>    	
    	<c:if test="${empty album.images}">
    		This Album does not contain any images
    	</c:if>
    </table>      
</div>