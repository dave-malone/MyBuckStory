<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="pageTitle">
	<c:choose>
		<c:when test="${article != null && not empty article.title}">
			${article.title} 
		</c:when>
		<c:when test="${empty param.category}">
			Press Releases, Hunting News, Fishing News, and Outdoors News <mbs:paginationTitle total="${totalNewsArticles}"/>
		</c:when>	
		<c:when test="${not empty param.category}">
			News in the ${param.category} Category <mbs:paginationTitle total="${totalNewsArticles}"/> 
		</c:when>
	</c:choose>
</c:set>
<title>${pageTitle}</title>