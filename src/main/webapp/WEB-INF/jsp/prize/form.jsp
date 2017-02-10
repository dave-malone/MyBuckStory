<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/prize/list/" />">Manage Prizes</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Prize</h2>
	
	<form:form action="/prize/${new ? 'save' : 'update'}" enctype="multipart/form-data" method="POST">
		<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>
		</c:if>
		<fieldset>
			<p>
				<label for="name">Title</label>
				<form:input path="title" />
				<form:errors path="title" cssClass="error"/>
			</p>
			<p>
				<label for="image">Image</label>
				<input type="file" name="file" id="image"/>
				<form:errors path="image" cssClass="error"/>
			</p>
			<p>
				<label for="description">Description</label>
				<form:textarea cssClass="rte" path="description" />
				<form:errors path="description" cssClass="error"/>
			</p>						
		</fieldset>		
		<input type="hidden" name="imageType" value="PRIZE" />
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  	    