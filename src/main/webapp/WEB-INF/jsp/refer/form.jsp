<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<c:set var="defaultMessageText">Hey!  I've found a great social network for outdoor enthusiasts where we get to connect with fellow enthusiasts, share hunting or fishing stories, friends can make comments on them, and there's even a section with the latest outdoors news.  You should sign up for a free account and	add me as your friend when you get there!</c:set>

	<h2>Tell your friends about us</h2>

	<form:form action="/refer/submit" method="POST">
		<c:set var="errorText">
			<form:errors path="*"/>
		</c:set>
		<c:if test="${not empty errorText}">
			<div style="border: #d9acac solid 1px; color: #ff000; padding: 1em;">
				${errorText}		
			</div>			
		</c:if>	
		<label for="email1">Email # 1:</label>
		<form:input path="email1"/>
		<label for="email2">Email # 2:</label>
		<form:input path="email2"/>
		<label for="email3">Email # 3:</label>
		<form:input path="email3"/>
		<label for="email4">Email # 4:</label>
		<form:input path="email4"/>
		
		<label for="message">Message</label>
		<textarea class="rte textarea" id="message" name="message">${not empty command.message ? command.message : defaultMessageText}</textarea>		
		
		<form:hidden path="from"/>
		<form:hidden path="fromEmail"/>
		<br />
		<input type="submit" value="Send" />
	</form:form>		