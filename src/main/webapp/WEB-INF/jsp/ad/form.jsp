<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/ad/manage/" />">Manage Advertisements</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Ad</h2>
	
	<form:form action="/ad/${new ? 'save' : 'update'}" enctype="multipart/form-data" method="POST">
		<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>
		</c:if>
		<fieldset>
			<p>
				Enter either Image, and Website OR Raw Code.  
				If Raw Code is entered, then Image and Website
				will be ignored 
			</p>
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
				<h3>OR</h3>
				<label for="rawCode">Raw Code</label>
				<form:textarea path="rawCode" />
				<form:errors path="rawCode" cssClass="error"/>
			</p>			
			<p>
				<label for="rank">Rank</label>
				<form:input path="rank" />
				<form:errors path="rank" cssClass="error"/>
			</p>			
			<p>
				<form:checkbox path="enabled" /> Enabled				
			</p>			
		</fieldset>		
		<input type="hidden" name="imageType" value="AD" />
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  	    