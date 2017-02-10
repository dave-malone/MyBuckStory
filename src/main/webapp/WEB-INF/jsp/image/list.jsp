<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<c:if test="${not empty param.previousPage}">
	<a href="<c:url value="${param.previousPage}" />" title="Back to ${param.previousPageTitle}">Back to ${param.previousPageTitle}</a> 
</c:if>



<div id="myPhotos">
   	<div class="clearfix">
        <h1>Hunting Pictures</h1>
        
        <div class="memberCount" style="margin:10px;clear:both;">
	  		<mbs:paginate total="${imageTotal}" defaultMax="60"/>
		</div> 
        
        <table class="allPictures" style="clear:both;margin:10px;">
        	<c:forEach items="${imageList}" var="image" varStatus="status">
        		<c:if test="${status.count % 6 == 1}">
        			<tr>
        		</c:if>			
				<td height="140px;">                   
	                <a href="<c:url value="/image.html?imageId=${image.id}&album=all&max=${not empty param.max ? param.max : 60}&start=${not empty param.start ? param.start : 0}" />" title="View full size image">
	                	<mbs:imageUri id="imageUri" imageId="${image.id}" maxWidthAndHeight="180"/>
	                    <img alt="${image.title}" src="${imageUri}" />
	                </a>                                      
               </td>
				<c:if test="${status.last || status.count % 6 == 0}">
					</tr>
				</c:if>	
			</c:forEach>
		</table>
		
		<div class="memberCount" style="margin:10px;clear:both;">
	  		<mbs:paginate total="${imageTotal}" defaultMax="60"/>
		</div>		
	</div>
</div>

    
                   