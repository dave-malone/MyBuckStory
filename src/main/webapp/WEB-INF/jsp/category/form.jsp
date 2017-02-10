<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/category/list/" />">Manage Categories</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Category</h2>
	<form:form method="POST" action="/category/${new ? 'save' : 'update'}">
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
                <label for="parent">Parent Category (Optional)</label>
				<form:select path="parent" multiple="false">
					<form:option value="">- Please select a category -</form:option>
					<form:options items="${parentCategories}" itemLabel="name" itemValue="id"/>
				</form:select>                  
				<form:errors path="parent" cssClass="error"/>
            </p>
		</fieldset>
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  
</div>
	    