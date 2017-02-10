<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div id="myPhotos">
   	<div class="clearfix">
   		<c:if test="${not empty profileImages}">
	        <h2>Photos of ${member.profile.firstName}</h2>
	        <ul class="photoGallery">
	        	<c:forEach items="${profileImages}" var="image">			
					<li>                   
		                <a href="<c:url value="/image.html?imageId=${image.id}&album=profile" />" title="View ${image.title}">
		                	<mbs:imageUri id="profileImageUri" imageId="${image.id}" maxWidthAndHeight="180"/>
		                    <img alt="${image.title}" src="${profileImageUri}" />
		                </a>                                      
	               </li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${not empty storyImages}">
			<h2>Story Photos</h2>
	        <ul class="photoGallery">
	        	<c:forEach items="${storyImages}" var="image">			
					<li>                   
		                <a href="<c:url value="/image.html?imageId=${image.id}&album=profile" />" title="View ${image.title}">
		                	<mbs:imageUri id="profileImageUri" imageId="${image.id}" maxWidthAndHeight="180"/>
		                    <img alt="${image.title}" src="${profileImageUri}" />
		                </a>                                      
	               </li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${not empty albums}">
			<h2>${member.profile.firstName}${fn:endsWith(member.profile.firstName, 's') ? '\'' : '\'s'} Albums</h2>
	        <ul class="photoGallery">
				<c:forEach items="${albums}" var="album">
					<c:forEach items="${album.images}" var="image" end="0">
						<li>                   
			                <a href="<c:url value="/image/album/show/${album.id}" />" title="View ${album.name}">
			                	<mbs:imageUri id="albumImageUri" imageId="${image.id}" maxWidthAndHeight="180"/>
			                    <img alt="${image.title}" src="${albumImageUri}" />
			                </a>                                      
		               </li>
	               </c:forEach>
				</c:forEach>
			</ul>
		</c:if>
	</div>
</div>
















<%--
<div id="myPhotos">
	<div class="clearfix">
	
	<!-- Photo galleries code -->
	<h3>Recent Photo Galleries</h3>
	<ul class="photoGalleries">
	    <li>
	        <div class="imgLeft">
	            <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	                <img alt="" src="style/affiliate_mossberg.png" />
	            </a>
	        </div>
	        <div class="left">
	            <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	                BWCA trip 08 (13 photos)
	            </a>
	            <p>
	               Created by <a href="" title="">Creator Name</a>
	            </p>
	        </div>
	    </li>
	    <li>
	        <div class="imgLeft">
	            <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	                <img alt="" src="style/affiliate_mossberg.png" />
	            </a>
	        </div>
	        <div class="left">
	            <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	                Door County Whitetails (13 photos)
	            </a>
	            <p>
	               Created by <a href="" title="">Creator Name</a>
	            </p>
	        </div>
	    </li>
	    <li>
	        <div class="imgLeft">
	            <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	                <img alt="" src="style/affiliate_mossberg.png" />
	            </a>
	        </div>
	        <div class="left">
	            <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	                Building a ice house from scratch (13 photos)
	            </a>
	            <p>
	               Created by <a href="" title="">Creator Name</a>
	            </p>
	        </div>
	    </li>
	</ul>
	<!-- End photo galleries code -->
	
	<!-- Inside a photo gallery code -->
	<h3>BWCA trip 08 photos</h3>
	<p>
	   Created by <a href="" title="">Creator Name</a>
	</p>
	<ul class="photoGallery">
	    <li>
	        <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	            <img alt="" src="style/affiliate_mossberg.png" />
	        </a>
	    </li>
	    <li>
	        <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	            <img alt="" src="style/affiliate_mossberg.png" />
	        </a>
	    </li>
	    <li>
	        <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	            <img alt="" src="style/affiliate_mossberg.png" />
	        </a>
	    </li>
	    <li>
	        <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	            <img alt="" src="style/affiliate_mossberg.png" />
	        </a>
	    </li>
	    <li>
	        <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	            <img alt="" src="style/affiliate_mossberg.png" />
	        </a>
	    </li>
	    <li>
	        <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	            <img alt="" src="style/affiliate_mossberg.png" />
	        </a>
	    </li>
	    <li>
	        <a href="http://www.mossberg.com/" title="Go to Mossberg.com">
	            <img alt="" src="style/affiliate_mossberg.png" />
	        </a>
	    </li>
	</ul>
	<!-- End inside a photo gallery code -->
	
	<!-- Viewing photo code -->
	<p>
	    <img alt="" src="style/affiliate_mossberg.png" />
	</p>
	<p>
	    <form action="" method="">
	        <fieldset>
	            <textarea class="max" rel="150"></textarea>
	        </fieldset>
	        <button type="button" id="photoComment">Post Comment</button>
	    </form>
	
	</p>
	<!-- End viewing photo code -->
	
</div>
--%>
