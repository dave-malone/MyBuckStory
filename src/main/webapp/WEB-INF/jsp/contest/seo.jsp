<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:if test="${contest != null && not empty contest.metaKeywords && not empty contest.metaDescription}">
	<c:set var="keywords" value="${contest.metaKeywords}" />
	<c:set var="description" value="${contest.metaDescription}" />
</c:if>
<meta name="keywords" content="${not empty keywords ? keywords : 'Free Hunting Contests, Free Fishing Contests, Free Turkey Hunting Contests, Free Deer Hunting Contests, Free Buck Hunting Contests'}" />
<meta name="description" content="${not empty description ? description : 'Free Hunting and Fishing Contests on MyBuckStory.com.  Membership and participation are completely free!  Win cash and prizes for sharing your stories and pictures'}" />		