<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div>
	<h2>Your Account is Ready!</h2>
	<p>
		Simply <a href="<c:url value="/register/signup?login_required=1" />">login</a> on with your email address (${requestScope.email}) and password.
	</p>
	<p> 
		You can <a href="<c:url value="/pwreset.html" />">reset your password</a> if you have forgotten it.
	</p>
</div>