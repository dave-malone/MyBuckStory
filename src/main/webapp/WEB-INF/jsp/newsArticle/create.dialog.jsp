<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div id="writeNews">
    <div class="bd">
    	<c:set var="new" value="${command.id == null}" />
		<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
        <form:form id="newsForm" name="newsForm" method="POST" action="/newsArticle/${new ? 'save' : 'update'}/" enctype="multipart/form-data">
            <c:if test="${!new}">
				<form:hidden path="id"/>	
				<form:hidden path="version"/>
			</c:if>
            <fieldset>	
                <p>
                    <label for="newsTitle">Title:</label>
                    <form:input path="title" id="newsTitle" />
                    <form:errors path="title" cssClass="error"/>
                </p>
                <p>
                    <label for="image">Photo</label>					                  
                    <input type="file" name="file" id="image" />
                    <form:errors path="image" cssClass="error"/>
                </p>
                <p>
                    <label for="category">Category</label>
	                <form:select path="categories" multiple="false">
		             	<form:option value="">-- Please select a category --</form:option>
		             	<c:forEach items="${categories}" var="category">
		             		<form:option value="${category.id}">${category.name}</form:option>
				    		<c:if test="${not empty category.children}">			    			
								<c:forEach items="${category.children}" var="childCategory">
							    	<form:option value="${childCategory.id}"> - ${childCategory.name}</form:option>
							    </c:forEach>							
				    		</c:if>
				    	</c:forEach>
				    </form:select> 
	                <form:errors path="categories" cssClass="error"/>                 
                </p>
                <p>
                    <label for="newsDescription">Description <form:errors path="content" cssClass="error"/></label>
                    <form:textarea cssClass="rte" path="content" id="newsDescription" cssStyle="width:100%; height:250px;"/>
                </p>
                <security:authorize access="hasRole('ROLE_ADMIN')">                
                	<h2>SEO</h2>
                	<p>
	                	<label for="metaKeywords">Keywords</label>
	                	<form:input path="metaKeywords" id="metaKeywords"/>
                	</p>
                	<p>
	                	<label for="metaDescription">Description</label>
	                	<form:input path="metaDescription" id="metaDescription"/>
                	</p>                
                </security:authorize>
            </fieldset>            
            <p>
            	<input type="hidden" name="imageType" value="NEWS_ARTICLE" />
            	<input id="newsSubmit" type="submit" value="${new ? 'Save' : 'Update'}"/>            
            </p>            
        </form:form>
    </div>
</div>