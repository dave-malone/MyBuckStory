<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/affiliate/manage/" />">Manage Partners</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Partner</h2>
	<form:form method="POST" action="/affiliate/${new ? 'save' : 'update'}" enctype="multipart/form-data">
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
				<label for="website">Website</label>
				<form:input path="website" />
				<form:errors path="website" cssClass="error"/>
			</p>
			<p>
				<label for="description">Description</label>
				<form:textarea cssClass="rte" path="description" />
				<form:errors path="description" cssClass="error"/>
			</p>
		</fieldset>
		<input type="hidden" name="imageType" value="AFFILIATE" />
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  
</div>
	    