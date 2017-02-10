<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<h2>Your News Article was Submitted</h2>
	<p>
		<a href="<c:url value="/newsArticle/create" />">Post another News Article</a><br /><br />
		<a href="" onclick="self.parent.refresh('<c:url value="${command.uri}" />');self.parent.tb_remove();">View your News Article</a><br /><br />
		<a href="" onclick="self.parent.tb_remove();">Close this dialog</a>
	</p>
</div>