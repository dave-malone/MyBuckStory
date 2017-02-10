<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="title">
	<c:choose>
		<c:when test="${story != null && not empty story.title}">
			${story.title}
		</c:when>
		<c:otherwise>
			<c:if test="${param.filter == 'mostRecent'}">
				Most Recent			
			</c:if>
			<c:if test="${param.filter == 'mostPopular'}">
				Most Popular 
			</c:if>
			<c:if test="${param.filter == 'topVoted'}">
				Top Voted 
			</c:if>
			<c:if test="${empty param.category}">
				Hunting Stories, Fishing Stories, Trail Cam Photos
			</c:if>		
			<c:if test="${not empty param.category}">
				<c:out value="${param.category} ${!fn:endsWith(param.category, 'Stories') ? 'Stories' : ''}" /> 
			</c:if>
			<c:if test="${not empty param.generalSearch}">
				matching the search term &quot;<c:out value="${param.generalSearch}" />&quot;
			</c:if>
			<mbs:paginationTitle total="${totalStories}"/>
		</c:otherwise>
	</c:choose>
</c:set>
<title>${title}</title>