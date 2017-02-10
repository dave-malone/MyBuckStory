<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

	<c:set var="new" value="${story.id == null}" />
	<c:set var="submitButtonName" value="${new ? 'Share a Story' : 'Update Story'}" />	

    <h2>${submitButtonName}</h2>
    
    <form:form id="dialogForm" name="dialogForm" method="POST" action="/story/${new ? 'save' : 'update'}" enctype="multipart/form-data" commandName="story">
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
        	
        	<c:set var="validationErrors"><form:errors path="*" /></c:set>
        	<c:if test="${not empty validationErrors}">
	        	<p class="error">
	        		Whoops!  You missed some important stuff.  Please fix the problems outlined in red.
	        	</p>
        	</c:if>
        	
        	<p>
                <label for="title">Title</label>                    
                <form:input path="title" id="title" />
                <form:errors path="title" cssClass="error"/>
            </p>
            <p>
            	<c:if test="${param.mode == 'edit'}">
            		<p style="font-size: xx-small;">
            		You do not need to reselect your photo while editing your story.
            		Any photo you choose now will replace the existing one.
            		</p>
            	</c:if>                	
                <label for="image">Photo</label>                    
                <input type="file" name="file" id="image" value="<c:if test="${not empty story.file && not empty story.file.originalFilename}">${story.file.originalFilename}</c:if>"/>
                <form:errors path="file" cssClass="error"/>
            </p>
            <p>
             <label for="category">Category</label>
             <form:select path="categories" multiple="false">
             	<form:option value="">-- Please select a category --</form:option>
             	<c:forEach items="${categories}" var="category">
             		<option value="${category.id}" <c:if test="${mbsfn:contains(story.categories, category)}">selected="selected"</c:if>>${category.name}</option>
		    		<c:if test="${not empty category.children}">			    			
						<c:forEach items="${category.children}" var="childCategory">
					    	<option value="${childCategory.id}" <c:if test="${mbsfn:contains(story.categories, childCategory)}">selected="selected"</c:if>> - ${childCategory.name}</option>
					    </c:forEach>							
		    		</c:if>
		    	</c:forEach>             	
             </form:select>
             <form:errors path="categories" cssClass="error"/>
            </p>
            <p>
                <label for="description">Your Story</label><form:errors path="text" cssClass="error"/>
                <form:textarea path="text" id="description" cssClass="rte" cssStyle="width:100%; height:850px;"/>
            </p>
            <security:authorize access="hasRole('ROLE_ADMIN')">                
               	<h2>SEO</h2>
               	<p>
                	<label for="metaKeywords">Keywords</label>
                	<form:input path="metaKeywords" id="metaKeywords"/>
               	</p>
               	<p>
                	<label for="metaDescription">Description</label>
                	<form:textarea path="metaDescription" id="metaDescription"/>
               	</p>                
			</security:authorize>
            <p>
	        	<input type="hidden" name="imageType" value="STORY" />
	        	<form:hidden path="id"/>	
	        	<form:hidden path="version"/>        	
	        	
	        	<input id="storySubmit" type="submit" value="${submitButtonName}"/>
            </p>            
        </fieldset>                    
    </form:form>