<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<h2>Ad Clicks for ${ad.name}</h2>		
<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-ads">		
	<tr>
		<th>ID</th>
		<th>Date</th>
		<th>Referrer</th>
		<th>IP Address</th>						
	</tr>		
	<c:forEach items="${adClicks}" var="adClick">
		<tr>
			<td>${adClick.id}</td>
			<td><fmt:formatDate value="${adClick.dateCreated}" pattern="MMMM d, yyyy 'at' hh:mma"/></td>
			<td>${adClick.referrer}</td>
			<td>${adClick.userIpAddress}</td>						
		</tr>			    
	</c:forEach>
</table>
<mbs:paginate total="${totalAdClicks}"/>