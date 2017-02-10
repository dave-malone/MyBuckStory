<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<c:if test="${article != null && not empty article.metaKeywords && not empty article.metaDescription}">
	<c:set var="keywords" value="${article.metaKeywords}" />
	<c:set var="description" value="${article.metaDescription}" />
</c:if>
<c:if test="${contest != null && not empty contest.metaKeywords && not empty contest.metaDescription}">
	<c:set var="keywords" value="${contest.metaKeywords}" />
	<c:set var="description" value="${contest.metaDescription}" />
</c:if>
<c:if test="${event != null && not empty event.metaKeywords && not empty event.metaDescription}">
	<c:set var="keywords" value="${event.metaKeywords}" />
	<c:set var="description" value="${event.metaDescription}" />
</c:if>
<c:if test="${story != null && not empty story.metaKeywords && not empty story.metaDescription}">
	<c:set var="keywords" value="${story.metaKeywords}" />
	<c:set var="description" value="${story.metaDescription}" />
</c:if>

<meta name="keywords" content="${not empty keywords ? keywords : 'Hunting Stories - Fishing Stories - Brag Board'}" />
<meta name="description" content="${not empty description ? description : 'Your online bragging board to post photos, share hunting stories and fishing stories, network and connect with outdoor enthusiasts, share tips, and stay up to date on the latest outdoor news'}" />


		