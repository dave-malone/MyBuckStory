<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div id="myPhotos">
   	<div class="clearfix">
        <h3>Photos of ${member.profile.firstName}</h3>
        <ul class="photoGallery">
        	<c:forEach items="${profileImages}" var="image">			
				<li>
				  <div class="gallery-image">
				    <div class="gallery-image-mid">
				      <div class="gallery-image-inner">        
		                <a href="<c:url value="/image.html?imageId=${image.id}&album=profile" />" title="View full size image">
		                	<mbs:imageUri id="imageUri" imageId="$image.id}" maxWidthAndHeight="95"/>
		                    <img alt="${image.title}" src="${imageUri}" />
		                </a>
	                  </div>
	                 </div>
	               </div>                                    
               </li>
			</c:forEach>
		</ul>
		<mbs:authorizeToViewProfile user="${member}" displayIfAuthorized="true">
 			<li class="user-side"><a href="<c:url value="/profile/edit/" />" style="color: #5da2ca; margin-left: 30px;"><span>Upload New Picture</span></a></li>
 		</mbs:authorizeToViewProfile>
	</div>
</div>
