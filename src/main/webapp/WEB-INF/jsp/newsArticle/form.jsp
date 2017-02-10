<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/newsArticle/manage/" />">Manage News</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} News Artile</h2>
	<form:form method="POST" enctype="multipart/form-data" action="${new ? 'save' : '/newsArticle/update'}">
		<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>
		</c:if>
		<fieldset>
			<p>
				<form:errors path="*" />
			</p>
			<p>
                <label for="newsTitle">Title:</label>
                <form:input path="title" id="newsTitle" />
            </p>
            <p>
                <label for="image">Photo</label>					                  
                <input type="file" name="file" id="image" />
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
            </p>
            <p>
                <label for="newsDescription">Description</label>
                <form:textarea cssClass="rte" path="content" id="newsDescription"/>
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
		</fieldset>		
		<input type="hidden" name="imageType" value="NEWS_ARTICLE" />
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  
</div>
	    