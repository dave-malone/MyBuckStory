<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:if test="${story != null && not empty story.metaKeywords && not empty story.metaDescription}">
	<c:set var="keywords" value="${story.metaKeywords}" />
	<c:set var="description" value="${story.metaDescription}" />
</c:if>
<meta name="keywords" content="${not empty keywords ? keywords : 'Hunting Stories - Fishing Stories - Brag Board'}" />
<meta name="description" content="${not empty description ? description : 'Your online bragging board to post photos, share hunting stories and fishing stories, network and connect with outdoor enthusiasts, share tips, and stay up to date on the latest outdoor news'}" />		