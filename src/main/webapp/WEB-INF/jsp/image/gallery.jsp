<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div id="single-image-top">
	<c:if test="${not empty param.previousPage}">
		<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">&laquo; Back to ${param.previousPageTitle}</a>
	</c:if>
	<a href="<c:url value="/profile/show/${image.createdBy.id}" />" title="" id="top-breadcrumb-left">&laquo; Back To ${image.createdBy.profile.firstName}'s Profile</a>
	<p id="top-breadcrumb-right">You Are Browsing Jon's Profile Photo's</p>
</div>

	<div id="single-image-contain">
		<a href="<c:url value="/streamimage.do?imageId=${image.id}" />" title="View Picture" style="margin: auto;">
		 	<img alt="Full Size Image" src="http://mybuckstory.com/static/images/NEWS_ARTICLE/21080/21080_580_350.jpg" />
		</a>
		<div id="view-full">
			<a href="" >View Full +</a>
		</div>
	</div>
	<div id="single-image-rotate">
		<a href="" id="prev-image">&laquo; Previous Image</a>
		<a href="" id="next-image">Next Image &raquo;</a>
	</div>
	<div id="single-admin-bar">
		<li><a class="delete">Delete Photo</a></li>
		<li><a>Edit Title</a></li>
		<li><a>Add to Album</a></li>
		<li><a>Make Profile Image</a></li>
	</div>
	<div id="image-comments">
		<h2>Image Title</h2>
		<div id="like-image">
			<a href="">2 People Like this!</a> <a href="" class="like-this">Like This</a>
		</div>
		<div class="single-comment">
			<img src="http://mybuckstory.com/static/images/PROFILE/1658/1658_180_180.jpg" />
			<div class="single-comment-info"><a href="">Jon Best</a> on June 13th, 2011</div>
			<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris tempor magna eget dolor congue in faucibus est rutrum. Mauris bibendum neque a nulla rutrum vitae faucibus eros iaculis.</p>
			<div class="clear"></div>
		</div>
		<div class="single-comment">
			<img src="http://mybuckstory.com/static/images/PROFILE/1658/1658_180_180.jpg" />
			<div class="single-comment-info"><a href="">Jon Best</a> on June 13th, 2011</div>
			<p>Curabitur tristique urna in nunc condimentum non tincidunt magna dapibus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Ut elementum feugiat risus ut dictum. Quisque fermentum, odio non semper tincidunt, nunc ante tristique urna, sed commodo justo libero non augue. Aenean diam turpis, accumsan ut porta non, feugiat ut augue.</p>
			<div class="clear"></div>
		</div>
	</div>
	