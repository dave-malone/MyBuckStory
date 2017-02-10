<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/store/ "/>">Back to Store</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Item</h2>
	<form:form method="POST" action="${new ? 'save' : 'update'}" enctype="multipart/form-data" commandName="command">
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
				<label for="salePrice">Sale Price</label>
				<form:input path="salePrice" />
				<form:errors path="salePrice" cssClass="error"/>
			</p>			
			<p>
				<label for="description">Description</label>
				<form:textarea path="description" />
				<form:errors path="description" cssClass="error"/>
			</p>			
			<p>
				<label for="image">Product Image</label>
				<input type="file" name="file" id="image" />
			</p>
			<p>
				<label for="payPalItemNumber">PayPal Item Number</label>
				<form:input path="payPalItemNumber" />
				<form:errors path="payPalItemNumber" cssClass="error"/>
			</p>
		</fieldset>
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  
</div>	    