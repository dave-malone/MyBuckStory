<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>Free Hunting and Fishing Contests</h2>
<div class="memberCount">
	<mbs:paginate total="${totalContests}" />
</div>
<ol id="mostRecentStories">
	<c:forEach items="${contests}" var="contest">
		<c:url value="/contest/list/" var="prevUrl">
			<c:param name="start" value="${param.start}"/>
			<c:param name="start" value="${param.max}"/>			
		</c:url>
		<c:url value="${contest.uri}" var="contestUrl">
        	<c:param name="previousPage" value="${prevUrl}" />
        	<c:param name="previousPageTitle" value="contests" />
        </c:url>
		<li class="clearfix">
 			<div class="recent-story-image-page"">
				<div class="recent-story-image-mid">
					<div class="recent-story-image-inner">
						<a href="${contestUrl}" title="Click here to read ${contest.title}">
							<mbs:imageUri id="contestImageUri" imageId="${contest.image.id}" maxWidthAndHeight="100"/>
							<img align="middle" alt="contest picture for ${contest.title}" src="${contestImageUri}" />
						</a>
					</div>
				</div>
			</div>
			<div style="float: left; width: 600px;">
				<h2><a href="${contestUrl}" title="${contest.title}">${contest.title}</a></h2>            
				<span class="gray">
					Sponsored By: 
					<c:if test="${not empty contest.sponsor}">
						${contest.sponsor.name} 
					</c:if>
					<c:if test="${empty contest.sponsor}">
						MyBuckStory.com
					</c:if>
				</span>
				<div style="margin-top:5px;">
					Start Date: <fmt:formatDate value="${contest.startDate}" pattern="MMMM d, yyyy"/>
					 - End Date: <fmt:formatDate value="${contest.endDate}" pattern="MMMM d, yyyy"/>
				</div>
				<div style="margin-top:10px;">
					<h2>Prizes</h2>
					<c:forEach items="${contest.contestPrizes}" var="contestPrize">
						<div style="margin-top:5px;">
							<h3>
							<c:if test="${contestPrize.rank == 1}">1st Place</c:if>
							<c:if test="${contestPrize.rank == 2}">2nd Place</c:if>
							<c:if test="${contestPrize.rank == 3}">3rd Place</c:if>
							<c:if test="${contestPrize.rank > 3}">${contestPrize.rank}th Place</c:if>				
							 - ${contestPrize.prize.title}</h3>
							<c:if test="${not empty contestPrize.winningStory}">
								Winning Story: <a href="<c:url value="${contestPrize.winningStory.uri}" />">${contestPrize.winningStory.title}</a>								
							</c:if>
							<c:if test="${not empty contestPrize.winningImage}">								
								Winning Image:
								<br />
								<a href="<c:url value="/image.html?imageId=${contestPrize.winningImage.id}" />">
									<mbs:imageUri id="contestPrizeWinningImageUri" imageId="${contestPrize.winningImage.id}" maxWidthAndHeight="50"/>
									<img src="${contestPrizeWinningImageUri}" alt="${contestPrize.prize.title}" />
								</a>								
							</c:if>
						</div>
					</c:forEach>
				</div>				
			</div>
     	</li>    	
	</c:forEach>  	
</ol>
<div class="memberCount">
	<mbs:paginate total="${totalContests}" />
</div>                               
	    