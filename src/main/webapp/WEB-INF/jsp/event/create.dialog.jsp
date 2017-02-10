<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<!-- users can create events, but those only show up on their friends feeds.
	When admins create events, they get published to everyones page -->
	
<c:set var="new" value="${command.id == null}" />
<c:set var="title" value="${new ? 'Create a new' : 'Update' }" />  
<div id="createEventDialog">
    <div class="bd">
        <form:form method="POST" action="/event/${new ? 'save' : 'update'}/" id="createEvent">
        	<c:if test="${!new}">
				<form:hidden path="id"/>	
				<form:hidden path="version"/>
			</c:if>
            <fieldset>
                <p>
                    <label for="eventTitle">Event Title:</label>
                    <form:input path="title" id="eventTitle"/>
                    <form:errors path="title" cssClass="error"/>
                </p>
                <p>
                    <label>Event Date:</label>
                    <mbs:dateSelector selectNamePrefix="event" beginYear="1900" selectedDate="${command.date}"/>   
                    <form:errors path="date" cssClass="error"/>                 
                    <br />                    
                </p>
                <p>
                    <label for="eventDetails">Details: <form:errors path="description" cssClass="error"/></label>
                    <form:textarea cssClass="rte" path="description" id="eventDetails" cssStyle="width:100%; height:250px;"/>                                     
                </p>
                <security:authorize access="hasRole('ROLE_ADMIN')">
	                <p>
	                	<form:checkbox path="publishInUsersFeeds" id="publishEvent"/>
	                    Publish in users feed. 
	                </p>               
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
                <input type="Submit" value="${new ? 'Publish' : 'Update'} Event" id="submitEvent"/>
            </fieldset>
        </form:form>
    </div>
</div>

