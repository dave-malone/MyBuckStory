<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="pageTitle">
	<c:choose>
		<c:when test="${contest != null && not empty contest.title}">
			${contest.title}
		</c:when>
		<c:otherwise>	
			Free Hunting and Fishing Contests <mbs:paginationTitle total="${totalEvents}"/>			
		</c:otherwise>
	</c:choose>
</c:set>
<title>${pageTitle}</title>