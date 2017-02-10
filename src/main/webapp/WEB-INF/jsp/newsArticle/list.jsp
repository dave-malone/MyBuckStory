<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>
	<c:if test="${empty param.category}">
		News &amp; Press Releases
	</c:if>	
	<c:if test="${not empty param.category}">
		News in the ${param.category} Category
	</c:if>
</h2>	
<div id="featured-news" class="yui-navset">    	
	<c:forEach items="${newsArticles}" var="news" varStatus="status">
		<div id="news${status.count}" class="featured-container">
			<div class="recent-story-image-page"">
				<div class="recent-story-image-mid">
					<div class="recent-story-image-inner">
						<mbs:imageUri id="newsArticleImageUri" imageId="${news.image.id}" maxWidthAndHeight="100"/>
						<img alt="${news.title} thumbnail" src="${newsArticleImageUri}" />
					</div>
				</div>
			</div>
			<div style="width: 550px; float: none; margin-left: 120px;">
				<h2>  
	             	<c:url value="${news.uri}" var="newsUrl">
	           			<c:param name="previousPage" value="/news.html" />
	           			<c:param name="previousPageTitle" value="News" />
	           			<c:param name="start" value="${params.start}" />
	           			<c:param name="max" value="${params.max}" />
	           		</c:url> 
					<a href="${newsUrl}" title="${news.title}">${news.title}</a>                                                       
				</h2>
				<span class="gray" style="background: #dedede;" >
					Posted on <fmt:formatDate value="${news.dateCreated}" pattern="MM/dd/yyyy 'at' hh:mma"/> | Categories                
					<c:forEach items="${news.categories}" var="category"> 
						<a href="<c:url value="/news.html?category=${category.name}" />" title="category">${category.name}</a> 
					</c:forEach>
				</span>
				<p><mbs:previewGenerator content="${news.content}" length="250" continueReadingUrl="${newsUrl}"/></p>
			</div>
		</div>
	</c:forEach>
</div>

<mbs:paginate total="${totalNewsArticles}" params="category"/>