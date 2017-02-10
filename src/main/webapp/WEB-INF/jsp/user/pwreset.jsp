<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
			
<div class="main-content clearfix">
	<c:if test="${not empty requestScope.error}">
		<p>
			${requestScope.error}
		</p>
	</c:if>
	<c:choose>
		<c:when test="${empty requestScope.newPassword}">
			<h2>Trouble Accessing Your Account?</h2>
			<hr />
			<p>
				Forgot your password? Enter your login email below. 
				We will send you an email with a link to reset your password. 
			</p>
			<form action="/user/pwResetRequest" method="POST">				
				Email Address :<input type="text" name="email" value="${param.email}"/><br />
				<input type="submit" value="Reset Your Password" />
			</form>
		</c:when>
		<c:otherwise>
			Your password has been successfully reset.  Your new password is <b>${requestScope.newPassword}</b><br />
			We strongly encourage you to <a href="<c:url value="/register/signup?login_required=1" />">log in</a> and change your password now.
		</c:otherwise>
	</c:choose>
</div>
