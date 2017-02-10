<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/badge/list/" />">Manage Badges</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Badge</h2>
	<form:form method="POST" action="/badge/${new ? 'save' : 'update'}" enctype="multipart/form-data">
		<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>
		</c:if>
		<fieldset>
			<p>
				<label for="name">Name</label>
				<form:input path="name" />
				<form:errors path="name" cssClass="error"/>
			</p>
			<p>
				<label for="image">Image</label>
				<input type="file" name="file" id="image"/>
				<form:errors path="image" cssClass="error"/>
			</p>
			<p>
                <label for="category">Category (Optional)</label>
				<form:select path="storyCategory" multiple="false">
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
				<label for="name"># Stories Posted In Category To Earn Badge</label>
				<form:input path="numberOfStoriesInCategory" />
				<form:errors path="numberOfStoriesInCategory" cssClass="error"/>
			</p>
			
			<p>
				<label for="description">Description</label>
				<form:textarea path="description" />
				<form:errors path="description" cssClass="error"/>
			</p>
		</fieldset>
		<input type="hidden" name="imageType" value="BADGE" />
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  
</div>
	    