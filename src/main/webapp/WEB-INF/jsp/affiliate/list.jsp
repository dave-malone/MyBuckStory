<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>Partners</h2>
<ul class="affiliates">
	<c:forEach items="${affiliates}" var="affiliate">
		<li>
	        <div class="imgLeft affiliatesImg">
	            <a href="${affiliate.website}" title="Go to ${affiliate.website}" target="_blank">
	            	<mbs:imageUri id="affiliateImageUri" imageId="${affiliate.image.id}" maxWidthAndHeight="148" />
	                <img alt="${affiliate.name} Logo" src="${affiliateImageUri}" />
	            </a>
	        </div>
	        <div class="left">
	            <h4>${affiliate.name}</h4>
	            <a href="${affiliate.website}" title="Go to ">
	                ${affiliate.website}
	            </a>
	            <p>
	                ${affiliate.description} 
	            </p>
	        </div>
	    </li>    	
	</c:forEach>  	
</ul>                               
	    