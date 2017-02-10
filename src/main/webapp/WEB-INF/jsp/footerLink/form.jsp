<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/footerLink/manage/ "/>">Manage Footer Links</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Footer Link</h2>
	<form:form method="POST" action="${new ? 'save' : '/footerLink/update'}">
		<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>
		</c:if>
		<fieldset>
			<p>
				<form:errors path="*" />
			</p>
			<p>
				<label for="name">Name</label>
				<form:input path="name" />
				<form:errors path="name" cssClass="error"/>
			</p>			
			<p>
				<label for="url">Website</label>
				<form:input path="url" />
				<form:errors path="url" cssClass="error"/>
			</p>			
			<p>
				<label for="category">Category</label>
				<form:select path="category">
					<form:option value="recommendedSites">Recommended Sites</form:option>
					<form:option value="resources">Resources</form:option>
					<form:option value="extras">Extras</form:option>
				</form:select>	
				<form:errors path="category" cssClass="error"/>			
			</p>
		</fieldset>
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  
</div>	    