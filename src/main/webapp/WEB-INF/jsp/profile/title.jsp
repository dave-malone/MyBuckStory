<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="pageTitle">
	<c:choose>
		<c:when test="${member != null && member.profile != null && not empty member.profile.fullName}">
			${member.profile.fullName}${fn:endsWith(member.profile.fullName, 's') ? '\'' : '\'s'} Profile
		</c:when>
		<c:otherwise>	
			Members - <mbs:paginationTitle total="${totalMembers}"/>			
		</c:otherwise>
		<%-- TODO - if we are viewing a user's profile, then 
		change the title to the view being rendered, i.e. feed, wall, etc --%>
	</c:choose>
</c:set>
<title>${pageTitle}</title>