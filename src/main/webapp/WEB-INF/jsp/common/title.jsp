<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="resource" value="${requestScope.requestedResource}" />
<c:set var="actualResource"><%= request.getAttribute("javax.servlet.forward.request_uri") %></c:set>
<!-- requesteted resource: ${resource} -->
<c:set var="pageTitle">
	<c:choose>
		<c:when test="${resource == 'signup'}">
			Sign Up - MyBuckStory.com
		</c:when>
		<c:when test="${fn:startsWith(resource,'profile/')}">
			${member.profile.fullName}'s Profile
		</c:when>	
		<c:when test="${resource == 'stories'}">
			Hunting Stories, Fishing Stories, Camping Stories, Trail Cam Shots - MyBuckStory.com
		</c:when>
		<c:when test="${fn:startsWith(resource,'/stories/')}">
			${story.title}
		</c:when>
		<c:when test="${resource == 'members'}">
			Members - MyBuckStory.com
		</c:when>
		<c:when test="${resource == 'photos'}">
			Photos - MyBuckStory.com
		</c:when>
		<c:when test="${resource == 'news'}">
			News - MyBuckStory.com
		</c:when>
		<c:when test="${fn:startsWith(resource,'/articles/')}">
			${article.title}
		</c:when>
		<c:when test="${resource == 'affiliates'}">
			Partners - MyBuckStory.com	
		</c:when>
		<c:when test="${resource == 'admin.jsp' || fn:contains(resource,'/manage/')}">
			Administration - MyBuckStory.com
		</c:when>
		<c:when test="${fn:startsWith(actualResource,'/contest')}">
			<c:if test="${not empty contest}">
				${contest.title}
			</c:if>
			<c:if test="${empty contest}">
				Free Hunting and Fishing Contests - Participation is Free! - MyBuckStory.com
			</c:if>
		</c:when>
		<c:otherwise>	
			Hunting Stories - Fishing Stories - Brag Board
		</c:otherwise>
	</c:choose>
</c:set>
<title>${pageTitle}</title>