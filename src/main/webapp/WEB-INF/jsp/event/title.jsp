<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="pageTitle">
	<c:choose>
		<c:when test="${event != null && not empty event.title}">
			${event.title} <fmt:formatDate value="${event.date}" pattern="MMMM, d, yyyy"/>
		</c:when>
		<c:otherwise>	
			Hunting Events, Fishing Events and Outdoors Expos in your area <mbs:paginationTitle total="${totalEvents}"/> 			
		</c:otherwise>
	</c:choose>
</c:set>

<title>${pageTitle}</title>