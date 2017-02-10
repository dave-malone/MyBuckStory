<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>	
    <div class="bd">
        <div class="loginForms clearfix">            
			<div id="signup-left">
				<h2>Sign Up today for free to take advantage of these great benefits</h2>
				<ul>
					<li>Share hunting and fishing stories, tips, and photos</li>
					<li>Connect with outdoor enthusiasts</li>
					<li>Win cash and prizes</li>
					<li>Stay up to date on the latest outdoor news</li>
					<li>Join the excitement today, membership is 100% FREE!</li>
				</ul>		
			</div>
	        <div class="signUp">
				<form:form action="/register/signup" id="signUp" method="POST" commandName="user">
					<fieldset>
			            <p style="color: red;">
							<form:errors path="*"/>
						</p>
		                <h2>Sign Up - It's Free</h2>
                        <p>
                            <label for="emailSu">Email Address</label>
                            <form:input path="username" id="emailSu"/>                            
                        </p>
                        <p>
                            <label for="reEnteredUsername">Re-Enter Email Address</label>
                            <form:input path="reEnteredUsername"/>                            
                        </p>
                        <p>
                            <label for="passwordSu">Password</label>
                            <form:password path="password" id="passwordSu"/>                            
                        </p>
                        <p>
                            <label for="retypePasswordSu">Retype Password</label>
                            <form:password path="reEnteredPassword" id="retypePasswordSu"/>
                        </p>
                        <p>
                            <label for="firstNameSu">First Name</label>
                            <form:input path="profile.firstName" id="firstNameSu"/>                            
                        </p>
                        <p>
                            <label for="lastNameSu">Last Name</label>
                            <form:input path="profile.lastName" id="lastNameSu"/>                            
                        </p>
                        <p>
                        	<label for="gender">Gender</label>
                        	<form:select path="profile.gender" id="gender">
                        		<form:option value="Male">Male</form:option>
                        		<form:option value="Female">Female</form:option>
                        	</form:select>                            
                        </p>
                        <p>
                            <label>
                                Birthday 
                            </label>
                            <mbs:dateSelector selectNamePrefix="profile.dob" beginYear="1900" selectedDate="${userReg.profile.dob}"/>                            
                        </p>
                        <p class="checkboxLabels">
                            <input type="checkbox" name="acceptedTOS" id="acceptTOSSu" checked="checked"/>
                            <label for="acceptTOSSu">I agree with the <a href="<c:url value="/tos.html" />">Terms of Service</a></label>
                        </p>  
                        <span class="yui-button yui-push-button">
                			<span class="first-child">                      
                       			<button type="submit" />Sign Up</button>
                       		</span>
                       	</span>
                    </fieldset>  
                </form:form>                
            </div>
        </div>
	</div>