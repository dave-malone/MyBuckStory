<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

	<c:set var="new" value="${command.id == null}" />

	<c:set var="submitButtonName" value="${new ? 'Share an Image' : 'Update Image'}" />	

    <h2>${submitButtonName}</h2>    
    <form:form id="dialogForm" name="dialogForm" method="POST" action="/image/${new ? 'save' : 'update'}" enctype="multipart/form-data">
    	<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>
		</c:if>
        <fieldset>
        	<c:if test="${not empty param.errorMessage}">
	        	<p>
	        		<div class="error">
	        			${errorMessage}
	        		</div>
	        	</p>
        	</c:if> 
        	     	
            
           	<c:if test="${new}">
           		<p>
            		<label for="image">Photo</label>                    
                	<input type="file" name="file" id="image" />
               	</p>
           	</c:if>  
           	<c:if test="${!new}">
           		<p style="float:left; padding:20px">
            		<mbs:imageUri id="imageUri" imageId="${command.id}" maxWidthAndHeight="200"/>
	            	<img alt="${command.title}" src="${imageUri}" />
            	</p> 
           	</c:if>                
            
            
            <p>
                <label for="title">Title</label>                    
                <form:input path="title" id="title" />
            </p>             
            <p>
				<label for="tags">Tags (comma delimited; for example:  whitetail deer, deer, wisconsin, trail cam)</label>
				<form:input path="tags" />
            </p>
            <p>
                <label for="description">Description</label>
                <form:textarea path="description" id="description" cssClass="rte" cssStyle="width:100%; height:150px;"/>
            </p>
            <p>
            	<c:if test="${!new}">
					<input type="hidden" name="type" value="${not empty command.type ? command.type : 'null'}" />
				</c:if>
				<c:if test="${new}">
					<input type="hidden" name="type" value="PICS" />
				</c:if>
	        	
	        	<input id="imageSubmit" type="submit" value="${submitButtonName}"/>
            </p>            
        </fieldset>                    
    </form:form>