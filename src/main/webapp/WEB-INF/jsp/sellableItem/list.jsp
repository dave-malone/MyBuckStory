<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>MBS Store</h2>

<security:authorize access="hasRole('ROLE_ADMIN')">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a>
	<a href="<c:url value="/item/create" />">Add Item</a>
</security:authorize>

<div class="memberCount">
	<mbs:paginate total="${totalItems}"/>
</div>
<ol id="mostRecentStories">    	
	<c:forEach items="${items}" var="item">
		<c:url value="/store" var="prevUrl">
			<c:param name="start" value="${param.start}"/>
			<c:param name="max" value="${param.max}"/>			
		</c:url>
		<c:url value="/item/show/${item.id}" var="itemUrl">
        	<c:param name="previousPage" value="${prevUrl}" />
        	<c:param name="previousPageTitle" value="MBS Store" />
        </c:url> 
 		<li class="clearfix">
 			<div class="recent-story-image-page">
				<div class="recent-story-image-mid">
					<div class="recent-story-image-inner">
						<a href="${itemUrl}" title="View ${item.name}">
							<mbs:imageUri id="itemImageUri" imageId="${item.image.id}" maxWidthAndHeight="100"/>
							<img align="middle" alt="${item.name}" src="${itemImageUri}" />
						</a>
					</div>
				</div>
			</div>
			<div style="float:left; width:600px;">
				<h2><a href="${itemUrl}" title="Click here to view ${item.name}">${item.name}</a></h2>            
				<span class="gray">${item.salePrice}</span>	
				<div>
					<a href="<c:url value="/store/addToCart/${item.id}?KeepThis=true&TB_iframe=true&height=100&width=350" />" class="thickbox" title="Add ${item.name} to Cart">Add to Cart</a>
				</div>
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<div>
						<a href="<c:url value="/item/edit/${item.id}" />">Edit ${item.name}</a>
					</div>
				</security:authorize>							
			</div>
     	</li>
	</c:forEach>        
</ol>
<div class="itemCount">
	<mbs:paginate total="${totalItems}" />
</div>                
	