<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<a href="<c:url value="/admin.jsp" />">Admin Home</a>
<h2>Contest Prizes</h2>
<a href="<c:url value="/contestPrize/create" />" style="float:right">Create new Contest Prize</a>
<div>
	<mbs:paginate total="${contestPrizeTotal}"/>
</div>
<table width="100%" cellpadding="5" cellspacing="5" border="1" id="admin-contestPrizes">		
	<tr>
		<th></th>
		<th>Rank</th>
		<th>contest</th>
		<th>Prize</th>			
		<th>Badge</th>			
		<th>Winning Story</th>
	</tr>		
	<c:forEach items="${contestPrizeList}" var="contestPrize">
		<tr>
			<td>
				<a href="<c:url value="/contestPrize/edit/${contestPrize.id}" />">Edit</a>
				<form action="delete" method="POST" class="deleteForm">
					<input type="hidden" name="id" value="${contestPrize.id}" />
					<a href="#" class="delete">Delete</a>
				</form>
			</td>
			<td>${contestPrize.rank}</td>
			<td>${contestPrize.contest.title}</td>
			<td>${contestPrize.prize.title}</td>
			<td>${contestPrize.badge.name}</td>					
			<td>
				<c:if test="${not empty contestPrize.winningStory}">
					${contestPrize.winningStory.title}
				</c:if>
			</td>
		</tr>			    
	</c:forEach>
</table>