<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:if test="${article != null && not empty article.metaKeywords && not empty article.metaDescription}">
	<c:set var="keywords" value="${article.metaKeywords}" />
	<c:set var="description" value="${article.metaDescription}" />
</c:if>
<meta name="keywords" content="${not empty keywords ? keywords : 'Hunting News, Fishing News, Outdoors News'}" />
<meta name="description" content="${not empty description ? description : 'Hunting News, Fishing News, and Outdoors News'}" />
		