<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content image-forms clearfix">
	<h2>Upload Images</h2>
	<a href="<c:url value="/profile/show/mine/" />" title="Back">&lt; Back to my Profile</a>
	<c:if test="${not empty requestScope.successMessage}">
		<div>
			${requestScope.successMessage}
		</div>
	</c:if>
	<div style="font-size:xx-small;margin-top:10px;margin-bottom:10px;">							
		You may only upload a maximum of 10 images at a time, or a total of 20MB at a time.		 							 
	</div>	
	<form:form method="post" enctype="multipart/form-data" action="/upload.images.do">
		<table>
			<tr>
				<td colspan="2">				
					<form:errors path="*" />					
				</td>
			</tr>
			<tr>
				<td colspan="2">						
					<hr />
					<table id="files" cellpadding="5">							
						<tr style="background-color:#a9a9a9">								
							<td>
								<label>Image</label>			
							</td>			
							<td>
								<input class="required-input" type="file" name="image1" id="image1"/>
							</td>
							<td>
								<label>Title</label>
							</td>			
							<td><input type="text" name="title1" id="imageTitle1" value="" class="required-input" size="25" /></td>								
						</tr>
					</table>					
					<a href="#" onclick="return addImage();" class="add-link" style="margin-top:10px;margin-bottom:10px;">
						<img class="controlIcon" src="<c:url value="/images/ico_add.jpg" />" alt="Add Image"/> 
						Add Image
					</a>					
					<hr />											
				</td>			
			</tr>
			<tr>
				<td>
					<input type="hidden" name="type" id="images" />
					<input type="hidden" name="imageType" value="GALLERY" />							
					<input type="submit" value="Upload" />			
				</td>
			</tr>
		</table>
	</form:form>    
</div>