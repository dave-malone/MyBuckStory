<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>Hunting Events, Fishing Events and Outdoors Expos in your area</h2>
<div class="memberCount">
	<mbs:paginate total="${totalEvents}"/>
</div>
<ol id="mostRecentStories">    	
	<c:forEach items="${events}" var="event">
		<li class="clearfix event-list">
	 		<c:url value="${event.uri}" var="eventUrl">
				<c:param name="previousPage" value="/events.html" />
				<c:param name="previousPageTitle" value="Events" />
			</c:url>
			<h3><a href="${eventUrl}" title="">${event.title}</a></h3>
	      	-  <fmt:formatDate value="${event.date}" pattern="M/d/yyyy"/>       
	        <span class="gray">Posted by 
				<a href="<c:url value="/profile/show/${event.createdBy.id}" />" title="Visit ${story.createdBy.profile.firstName}'s profile">
					${event.createdBy.profile.fullName}</a> 
				on <fmt:formatDate value="${event.dateCreated}" pattern="M/d/yyyy 'at' h:mma"/> 
			</span>
		</li>
	</c:forEach>        
</ol>
<div class="memberCount">
	<mbs:paginate total="${totalEvents}"/>
</div>                                     