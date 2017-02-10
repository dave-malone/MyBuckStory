<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<h2>Your Event was Submitted</h2>
	<p>
		<a href="<c:url value="/event/create" />">Post another event</a><br /><br />
		<a href="" onclick="self.parent.refresh('<c:url value="${command.uri}" />');self.parent.tb_remove();">View your event</a><br /><br />
		<a href="" onclick="self.parent.tb_remove();">Close this dialog</a>
	</p>
</div>