<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<c:catch var="e">
	<mbs:getCurrentUser id="user"/>
</c:catch>	
<c:if test="${not empty e}">
	${e.message}
</c:if>
<span id="hd-prompt">	
	<%-- 
	<security:authorize access="isAnonymous()">
		Not a Member? <a href="<c:url value="/register/signup" />" title="Register" style="color:#6ec7f5;">Sign up now</a>, it's free!		    
	</security:authorize>
	<security:authorize access="isAuthenticated()">
		<p id="hd-user">Signed in as ${user.profile.fullName}</p>	
	</security:authorize>
	--%>
</span>
	<div class="left">	
		<%-- 		
		<security:authorize access="isAnonymous()">	
			<form method="post" action="<c:url value="/j_spring_security_check"/>" id="signIn">
				<fieldset>
					<p style="float:left;">
						<c:set var="emailValue">
						<c:choose>
								<c:when test="${not empty param.login_error}"><c:out value="${security_SECURITY_LAST_USERNAME}"/></c:when>
								<c:otherwise>Email</c:otherwise>
							</c:choose>
						</c:set>						
						<input type="text" class="emailSi" id="emailSi" name="j_username" value="${emailValue}"/>
					</p>
					<p style="float:left;">
						<input type="password" class="passwordSi" id="passwordSi" value="Password" name="j_password" />                            
					</p>
					<span class="yui-button yui-push-button" style="margin-left: 13px;">
						<span class="first-child">                      
							<button type="submit" />Sign In</button>
						</span>
					</span> 					
                     <p style="position: absolute; top: 70px; left: 20px;">
                        <input type="checkbox" id="remember" name="_spring_security_remember_me" checked="checked"/> 
                        <label for="remember" id="rememberLabel">Keep me signed in for two weeks</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="<c:url value="/pwreset.html" />" title="Forgot Password?" style="color:#6ec7f5;">Forgot Password?</a>	 
                    </p>					                   
				</fieldset>
			</form>  
		</security:authorize>
		--%>		   			         
	</div>					
	<div id="right">
		<%--
		<security:authorize access="isAuthenticated()">		
			<a href="<c:url value="/event/create?KeepThis=true&TB_iframe=true&height=450&width=750" />" class="thickbox" title="Create an Event">
			Create an event</a> | 
			<a href="<c:url value="/profile/edit" />" title="" id="editProfile">Edit Profile</a> | 
			<a href="<c:url value="/j_spring_security_logout" />" title="Sign Out">Sign Out</a>
		</security:authorize>
		 --%>
	</div>
  <div id="logo"><center><a href="<c:url value="/" />"><img src="/images/clear.gif" /></a></center></div>