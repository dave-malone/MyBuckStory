<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:if test="${event != null && not empty event.metaKeywords && not empty event.metaDescription}">
	<c:set var="keywords" value="${event.metaKeywords}" />
	<c:set var="description" value="${event.metaDescription}" />
</c:if>
<meta name="keywords" content="${not empty keywords ? keywords : 'Hunting Events, Fishing Events, Hunting Expos, Fishing Expos, Outdoor Expos'}" />
<meta name="description" content="${not empty description ? description : 'Hunting Events, Fishing Events and Outdoors Expos in your area'}" />		