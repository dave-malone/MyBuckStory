<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div>
	<c:if test="${not empty requestScope.successMessage}">
		<div class="message">${requestScope.successMessage}</div>
	</c:if>
	<c:if test="${not empty requestScope.errorMessage}">
		<div class="error">${requestScope.errorMessage}</div>
	</c:if>
	<c:if test="${not empty param.successMessage}">
		<div class="message">${param.successMessage}</div>
	</c:if>
	<c:if test="${not empty param.errorMessage}">
		<div class="error">${param.errorMessage}</div>
	</c:if>	

	<form id="myProfilePic" action="/profile/edit/picture" method="POST" enctype="multipart/form-data">
		<fieldset>				
			<p>
				<h2>Current Picture</h2>
	        	<div>
	        		<mbs:imageUri id="memberProfileImageUri" imageId="${member.profile.image.id}" maxWidthAndHeight="100"/>
	            	<img alt="Your Current Profile Pic" src="${memberProfileImageUri}" />
	        	</div>
	        </p>
	        <p>
		        <h2>Select a new photo</h2>
		        <input type="file" id="profilePicture" name="profilePicture" />
			</p>
			<p>
                <label for="title">Title</label>                    
                <input type="text" name="title" id="title" />
            </p>             
            <p>
				<label for="tags">Tags (comma delimited; for example:  whitetail deer, deer, wisconsin, trail cam)</label>
				<input type="text"  name="tags" />
            </p>
            <p>
                <label for="description">Description</label>
                <textarea name="description" id="description" cssClass="rte" cssStyle="width:100%; height:150px;"></textarea>
            </p>
			<p>
		        <input id="messageSubmit" type="submit" value="Change Picture"/>  
		        <input type="button" value="Cancel" onclick="self.parent.tb_remove();"/>
			</p>
		</fieldset>
	</form>
</div>