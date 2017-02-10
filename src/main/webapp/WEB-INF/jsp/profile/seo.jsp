<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:if test="${not empty member}">
	<meta name="keywords" content="${member.profile.fullName}, ${member.profile.firstName}, ${member.profile.lastName}" />
	<meta name="description" content="${member.profile.fullName}${fn:endsWith(member.profile.fullName, 's' ? '\'' : '\'s')} Profile on MyBuckStory.com" />
	<%-- TODO - if we are viewing a user's profile, then 
		change the title to the view being rendered, i.e. feed, wall, etc --%>
</c:if>
<c:if test="${empty member}">
	<meta name="keywords" content="${not empty keywords ? keywords : 'Outdoor enthusiasts, Hunting Social Network, Fishing Social Network, Outdoor Social Network, Free Membership'}" />
	<meta name="description" content="${not empty description ? description : 'Hunting News, Fishing News, and Outdoors News'}" />
</c:if>
		