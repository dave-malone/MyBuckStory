<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div class="gallery-forms clearfix">
	<h2>Create an Album</h2>

    <form:form action="/image/album/save" method="POST" enctype="multipart/form-data">
	    <fieldset>
	    	<table>
		    	<tr>
			    	<td>
						<p style="color: red;">
							<form:errors path="*"/>
						</p>
					</td>
				</tr>
				<tr>
					<td>
					    <p>
							<label for="emailSu">Album Name</label>
					        <form:input path="name" id="photoform"/>                            
					    </p>
				    </td>
			    </tr>
			    <tr>
				    <td>
					    <p>
							<label for="emailSu">Album Description</label>
							<form:textarea path="description" />
					    </p>
				    </td>
			    </tr>
			    <tr>
					<td>
						<h2>Upload up to 10 Photos at a time</h2>						
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
						<span class="yui-button yui-push-button">
							<span class="first-child">                      
								<button type="submit" />Save Album</button>
							</span>
						</span>
					</td>
				</tr>
			</table>
		</fieldset>  
	</form:form>                
</div>