<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/contest/manage/" />">Manage Contests</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} contest</h2>
	<c:if test="${not empty param.errorMessage}">
		<div class="error">${param.errorMessage}</div>
	</c:if>
	<form:form method="POST" action="/contest/${new ? 'save' : 'update'}" enctype="multipart/form-data">
		<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>			
		</c:if>
		<fieldset>
			<p>
				<label for="title">Title</label>
				<form:input path="title" />
				<form:errors path="title" cssClass="error"/>
			</p>
			<p>
                <label>Start Date:</label>
                <mbs:dateSelector selectNamePrefix="startDate" beginYear="2009" selectedDate="${command.startDate}"/>   
                <form:errors path="startDate" cssClass="error"/>                 
                <br />                    
            </p>
            <p>
                <label>End Date:</label>
                <mbs:dateSelector selectNamePrefix="endDate" beginYear="2009" selectedDate="${command.endDate}"/>   
                <form:errors path="endDate" cssClass="error"/>                 
                <br />                    
            </p>
            <p>
				<label for="storyCategory">Story Category</label>
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
				<form:errors path="storyCategory" cssClass="error"/>
			</p>
            <p>
				<label for="sponsor">Sponsor</label>
				<form:select path="sponsor">
					<form:option value="">--Select a Sponsor</form:option>
					<form:options items="${affiliates}" itemLabel="name" itemValue="id"/>
				</form:select>
				<form:errors path="sponsor" cssClass="error"/>
			</p>
			<p>
				<label for="image">Image</label>
				<input type="file" name="file" id="image"/>
				<form:errors path="file" cssClass="error"/>
			</p>
			<p>
				<label for="description">Description</label>
				<form:textarea cssClass="rte" path="description" />
				<form:errors path="description" cssClass="error" cssStyle="width:100%"/>
			</p> 
			<p>
				<label for="rules">Rules</label>
				<form:textarea cssClass="rte" path="rules" />
				<form:errors path="rules" cssClass="error" cssStyle="width:100%"/>
			</p>             
	    	<h2>SEO</h2>
			<p>
				<label for="metaKeywords">Keywords</label>
				<form:input path="metaKeywords" id="metaKeywords"/>
			</p>
			<p>
				<label for="metaDescription">Description</label>
				<form:textarea path="metaDescription" id="metaDescription" />
			</p>            
		</fieldset>
		<input type="hidden" name="imageType" value="contest" />
		<input type="submit" value="${new ? 'Publish' : 'Update'}" />
	</form:form>                                  
</div>
	    