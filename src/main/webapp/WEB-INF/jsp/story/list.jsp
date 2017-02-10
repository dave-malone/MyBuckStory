<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:set var="title">
	<c:if test="${empty param.filter || param.filter == 'mostRecent'}">
		Most Recent Stories
	</c:if>
	<c:if test="${param.filter == 'mostPopular'}">
		Most Popular Stories
	</c:if>
	<c:if test="${param.filter == 'topVoted'}">
		Top Voted Stories
	</c:if>		
	<c:if test="${not empty param.category}">
		in the <c:out value="${param.category}" /> Category
	</c:if>
</c:set>

<h1>${title}</h1>

<div class="memberCount" style="clear:both;">
	<mbs:paginate total="${totalStories}" params="category, filter"/>
</div>

<div>
	<div id="storyFilters">
		<h3>Story Filters</h3>
		<ol id="staticFilters">
			<c:url value="/stories.html" var="mostRecentStoriesUrl">
				<c:param name="filter" value="mostRecent" />				
				<c:param name="category" value="${params.category}" />
			</c:url>
			<li <c:if test="${param.filter == 'mostRecent'}">class="selected"</c:if>>
				<a href="${mostRecentStoriesUrl}">Most Recent</a>
			</li>
			<c:url value="/stories.html" var="mostPopularStoriesUrl">
				<c:param name="filter" value="mostPopular" />				
				<c:param name="category" value="${params.category}" />
			</c:url>
			<li <c:if test="${param.filter == 'mostPopular'}">class="selected"</c:if>>
				<a href="${mostPopularStoriesUrl}">Most Popular</a>
			</li>
			<c:url value="/stories.html" var="topVotedStoriesUrl">
				<c:param name="filter" value="topVoted" />				
				<c:param name="category" value="${params.category}" />
			</c:url>
			<li <c:if test="${param.filter == 'topVoted'}">class="selected"</c:if>>
				<a href="${topVotedStoriesUrl}">Top Voted</a>
			</li>		
		</ol>
		
		<h3>Categories</h3>
		<ol id="storyCategories">
			<c:forEach items="${categories}" var="category">
				<c:url value="/stories.html" var="storiesFilteredByCategoryUrl">
					<c:param name="filter" value="${param.filter}" />					
					<c:param name="category" value="${category.name}" />
				</c:url>
				<li class="story-category<c:if test="${param.category == category.name}"> selected</c:if>">
		   			<a href="${storiesFilteredByCategoryUrl}" title="${category.name}">${category.name}</a>			   		
		   		</li>
		   		<c:if test="${not empty category.children}">
			   		<c:forEach items="${category.children}" var="childCategory">
						<c:url value="/stories.html" var="storiesFilteredBySubcategoryUrl">
							<c:param name="filter" value="${param.filter}" />								
							<c:param name="category" value="${childCategory.name}" />
						</c:url>
						<li class="story-subcategory<c:if test="${param.category == childCategory.name}"> selected</c:if>">
				    		<a href="${storiesFilteredBySubcategoryUrl}" title="${childCategory.name}">${childCategory.name}</a>
				    	</li>
				    </c:forEach>
				</c:if>
		   	</c:forEach>	
		</ol>
	</div>
	
	<ol id="mostRecentStories" style="float:right; width:500px;">    	
		<c:forEach items="${stories}" var="story">
			<c:url value="/stories.html" var="prevUrl">
				<c:param name="start" value="${param.start}"/>
				<c:param name="max" value="${param.max}"/>
				<c:param name="category" value="${param.category}" />	
				<c:param name="filter" value="${param.filter}" />								
			</c:url>
			<c:url value="${story.uri}" var="storyUrl">
	        	<c:param name="previousPage" value="${prevUrl}" />
	        	<c:param name="previousPageTitle" value="Stories" />
	        </c:url> 
	 		<li class="clearfix">
	 			<div class="recent-story-image-page"">
					<div class="recent-story-image-mid">
						<div class="recent-story-image-inner">
							<a href="${storyUrl}" title="Click here to read ${story.title}">
								<mbs:imageUri id="storyImageUri" imageId="${story.image.id}" maxWidthAndHeight="100"/>
								<img align="middle" alt="Story picture for ${story.title}" src="${storyImageUri}" />
							</a>
						</div>
					</div>
				</div>
				<div style="">
					<h2><a href="${storyUrl}" title="Click here to read ${story.title}">${story.title}</a></h2>            
					<span class="gray">Posted by 
						<a href="<c:url value="/profile/show/${story.createdBy.id}" />" title="Visit ${story.createdBy.profile.firstName}'s profile">
							${story.createdBy.profile.fullName}
	       				</a> 
						on <fmt:formatDate value="${story.dateCreated}" pattern="M/d/yyyy 'at' h:mma"/> 
					</span>
					<div>
						Categories:
						<c:forEach items="${story.categories}" var="category">
	                     	<a href="<c:url value="/stories.html?category=${category.name}" />" title="category">${category.name}</a>
	                     </c:forEach>
					</div>
					<div id="averageRating">
						Average Rating:
						<c:if test="${not empty story.averageRating}"><fmt:formatNumber value="${story.averageRating}" pattern="#.###"/></c:if>
						<c:if test="${empty story.averageRating}">(Not Yet Rated)</c:if>
					</div>
					<div>
						Story Votes: 
						<c:if test="${not empty story.voteCount}">${story.voteCount}</c:if>
						<c:if test="${empty story.voteCount}">(No Votes Yet)</c:if>
					</div>				
				</div>
	     	</li>
		</c:forEach>        
	</ol>

</div>
<div class="memberCount" style="clear:both;">
	<mbs:paginate total="${totalStories}" params="category, filter"/>
</div>                                       