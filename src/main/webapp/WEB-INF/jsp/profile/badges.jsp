<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>${member.profile.firstName}${fn:endsWith(member.profile.firstName, 's') ? '\'' : '\'s'} Badges & Prizes</h2>
<div id="myInfo">
	<c:if test="${not empty contestPrizes}">
		<h3>Prizes</h3>
		
		<c:forEach items="${contestPrizes}" var="contestPrize" varStatus="status">
			<p>
				Contest: <a href="<c:url value="${contestPrize.contest.uri}" />">${contestPrize.contest.title}</a>
			</p>
			<p>
				<c:if test="${not empty contestPrize.winningStory}">
					Winning Story: <a href="<c:url value="${contestPrize.winningStory.uri}" />">${contestPrize.winningStory.title}</a>
				</c:if>
				<c:if test="${not empty contestPrize.winningImage}">
					Winning Image:
					<br />
					<a href="<c:url value="/image.html?imageId=${contestPrize.winningImage.id}" />">
						<mbs:imageUri id="contestPrizeWinningImageUri" imageId="${contestPrize.winningImage.id}" maxWidthAndHeight="50"/>
						<img src="${contestPrizeWinningImageUri}" alt="" />
					</a>
				</c:if>
			</p>
			<p>
				<div>Prize:</div>
				<a href="<c:url value="${contestPrize.contest.uri}#${contestPrize.prize.id}" />">
					<c:if test="${contestPrize.rank == 1}">1st Place</c:if>
					<c:if test="${contestPrize.rank == 2}">2nd Place</c:if>
					<c:if test="${contestPrize.rank == 3}">3rd Place</c:if>
					<c:if test="${contestPrize.rank > 3}">${contestPrize.rank}th Place</c:if>				
					 - ${contestPrize.prize.title}</a>
				<c:if test="${not empty contestPrize.prize.image}">
					<a href="<c:url value="${contestPrize.contest.uri}#${contestPrize.prize.id}" />">
						<mbs:imageUri id="contestPrizeImageUri" imageId="${contestPrize.prize.image.id}" />
						<img src="${contestPrizeImageUri}" alt="${contestPrize.prize.title}" class="imgLeft" />
					</a>
				</c:if>				
				<div>${contestPrize.prize.description}</div>				
			</p>					
		</c:forEach>		
	</c:if>
	<h3>Badges</h3>
    <table cellspacing="10" cellpadding="10" width="100%">    	
   		<c:forEach items="${badges}" var="badge" varStatus="status">
   			<c:if test="${status.count % 5 == 1}">
				<tr>
			</c:if>
			<td>
				<mbs:imageUri id="badgeImageUri" imageId="${badge.image.id}" />
	   			<img src="${badgeImageUri}" alt="${badge.name}" title="${badge.name}"/>
	   			<br />${badge.name}				
			</td>	
			<c:if test="${status.last || status.count % 5 == 0}">
				</tr>
			</c:if>					
		</c:forEach>    	
    	<c:if test="${empty badges}">
    		${member.profile.firstName} has not earned any badges yet
    	</c:if>
    </table>    
</div>