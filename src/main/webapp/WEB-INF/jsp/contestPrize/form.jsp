<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/contestPrize/list/" />">Manage Contest Prizes</a>
	<br />
	<c:set var="new" value="${command.id == null}" />
	<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />
	<h2>${title} Contest Prize</h2>
	
	<form:form method="POST" action="/contestPrize/${new ? 'save' : 'update'}" enctype="multipart/form-data">
		<c:if test="${!new}">
			<form:hidden path="id"/>	
			<form:hidden path="version"/>
		</c:if>
		<fieldset>
			<p>
				<label for="rank">Rank</label>
				<form:input path="rank" />
				<form:errors path="rank" cssClass="error"/>
			</p>
			<p>
				<label for="contest">Contest</label>
				<form:select path="contest">
					<form:option value="">--Select a Contest--</form:option>
					<form:options items="${contests}" itemLabel="title" itemValue="id" />
				</form:select>
				<form:errors path="contest" cssClass="error"/>
			</p>
			<p>
				<label for="prize">Prize</label>
				<form:select path="prize">
					<form:option value="">--Select a Prize--</form:option>
					<form:options items="${prizes}" itemLabel="title" itemValue="id"/>
				</form:select>
				<form:errors path="prize" cssClass="error"/>
			</p>
			<p>
				<label for="badge">Badge</label>
				<form:select path="badge">
					<form:option value="">--Select a Badge--</form:option>
					<form:options items="${badges}" itemLabel="name" itemValue="id"/>
				</form:select>
				<form:errors path="badge" cssClass="error"/>
			</p>
			<c:if test="${!new}">
				<p>
					<label for="winningStory">Winning Story</label>
					<form:select path="winningStory">
						<form:option value="">--Select a Story--</form:option>
						<form:options items="${storiesInCompetitionCategory}" itemLabel="title" itemValue="id"/>
					</form:select>
					<form:errors path="winningStory" cssClass="error"/>
				</p>			
			</c:if>					
		</fieldset>		
		<input type="submit" value="${new ? 'Save' : 'Update'}" />
	</form:form>                                  	    