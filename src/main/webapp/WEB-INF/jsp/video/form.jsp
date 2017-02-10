<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>Upload a Video</h2>
<c:set var="new" value="${command.id == null}" />
<div id="videoUploadProgress"></div>

<iframe id="target_upload" name="target_upload" src="" style="display: none"></iframe>



<form:form id="videoUploadForm" name="dialogForm" method="POST" action="/video/${new ? 'save' : 'update'}" enctype="multipart/form-data" target="target_upload">
	<c:if test="${!new}">
		<form:hidden path="id"/>	
		<form:hidden path="version"/>
	</c:if>
    <fieldset>
    	<p>
    		<div id="uploadStatus" style="display:none;">
			    <!-– specify width on this uploadProgressBar, otherwise the progress indicator won't move –->
			    Video Upload Progress: 
			    <div id="uploadProgressBar" style="width:200px;">
			        <div id="uploadIndicator"></div>
			    </div>
			    <div id="uploadPercentage"></div>
			</div>
    	</p>
    	<c:if test="${not empty param.errorMessage}">
	     	<p>
	     		<div class="error">
	     			${errorMessage}
	     		</div>
	     	</p>
    	</c:if>	
    	<p>
            <label for="title">Title</label>                    
            <form:input path="title" id="title" cssStyle="width:400px;"/>
        </p>
        <p>
        	<c:if test="${param.mode == 'edit'}">
        		<p style="font-size: xx-small;">
	        		You do not need to reselect your video while editing.
	        		Any video you choose now will replace the existing one.
        		</p>
        	</c:if>                	
            <label for="video">Video (40 MB max size)</label>                        
            <input type="file" name="file" id="video" />
        </p>
        <p>
            <label for="description">Description</label>
            <form:textarea path="description" id="description" cssClass="rte" cssStyle="width:100%; height:250px;"/>
        </p>
        <p>
	     	<form:hidden path="id"/>	
	     	<form:hidden path="version"/>        	
	     	<c:set var="submitButtonName" value="${new ? 'Publish Video' : 'Update Video'}" />	
	     	<input id="videoSubmit" type="submit" value="${submitButtonName}"/>
        </p>            
    </fieldset>                    
</form:form>