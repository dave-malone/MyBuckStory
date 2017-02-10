<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div class="main-content">
	
	<h5>MyBuckStory.com has a problem with it's code.</h5>
	
	<p>	
		We're aware of this bug and can fix it, please check back in shortly.			
	</p>
	<c:if test="${not empty error}">
		${error.message}<br /><br />
		<% 
			Exception ex = (Exception)pageContext.findAttribute("error");
			ex.printStackTrace(pageContext.getResponse().getWriter());
		%>
	</c:if>
</div><!-- End .yui-content -->