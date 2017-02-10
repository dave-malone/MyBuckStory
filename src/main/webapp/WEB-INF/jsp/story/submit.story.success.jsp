<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>Your Story was Submitted</h2>
<p>
	<a href="<c:url value="/story/create" />">Post another story</a><br /><br />
	<c:if test="${story.uri != null && story.id != null}">
		<a href="<c:url value="${story.uri}" />" >View your story</a><br /><br />
	</c:if>
	<c:if test="${story.uri == null || story.id == null}">
		Your Story has been submitted, and will be ready to view soon
	</c:if>
</p>