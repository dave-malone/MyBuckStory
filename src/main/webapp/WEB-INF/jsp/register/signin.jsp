<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>	
	<c:if test="${not empty param.login_required}">
	    <div style="border: #d9acac solid 1px; color: #ff000; padding: 1em;">
	    	You must be signed in to perform this action.  Please login to continue
	    </div>
    </c:if>

    <c:if test="${not empty param.login_error}">
		<div style="border: #d9acac solid 1px; color: #ff000; padding: 1em;">
			Your login attempt was not successful.  Please try again, or register
			for an account using the form below.
			<br />
			<%--
			TODO - add better logic here.  We can detect if the following conditions are true:
			1. username/password entered don't match any accounts
			2. username entered doesn't exist
			3. account has not yet been activated
			
			we should add links to perform the following actions:
			
			1. request the account verification email to be resent
			2. reest password (Forgot password link)
			 --%>
			<p><h4><b>Reason:</b>User not found.</h4>(Have you confirmed your account? Check your email)</p>
		</div>
	</c:if>
    <div class="bd">
        <div class="loginForms clearfix">            
			<div id="signup-left">
				<form method="post" action="<c:url value="/j_spring_security_check"/>" id="signIn">
					<fieldset>
						<h2>Sign In</h2>
						<input type="text" class="emailSi" id="emailSi" name="j_username" value="${not empty param.login_error ? SPRING_SECURITY_LAST_USERNAME : 'Email'}"/>
						<input type="password" class="passwordSi" id="passwordSi" value="Password" name="j_password" />                            
						<span class="yui-button yui-push-button" style="margin-left: 13px;">
							<span class="first-child">                      
								<button type="submit" />Sign In</button>
							</span>
						</span>
						<br /><br />						
                        <input type="checkbox" id="remember" name="_spring_security_remember_me" checked="checked"/> 
                        <label for="remember" id="rememberLabel">Keep me signed in for two weeks</label>
                        <br />
						<a href="<c:url value="/pwreset.html" />" title="Forgot Password?" style="color:#6ec7f5;">Forgot Your Password?</a>									                   
					</fieldset>
				</form>
				
			</div>
	        <div class="signUp">			
				<h2>Not a Member?</h2>  
				<a href="<c:url value="/register/signup" />" title="Sign Up">Sign Up</a> now for free - it's quick and easy 		            
            </div>
        </div>
	</div>