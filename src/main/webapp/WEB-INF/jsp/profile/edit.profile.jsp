<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>Edit Your Profile</h2>
<c:if test="${not empty requestScope.successMessage}">
	<div class="message">${requestScope.successMessage}</div>
</c:if>
<c:if test="${not empty requestScope.errorMessage}">
	<div class="error">${requestScope.errorMessage}</div>
</c:if>
<c:if test="${not empty param.successMessage}">
	<div class="message">${param.successMessage}</div>
</c:if>
<c:if test="${not empty param.errorMessage}">
	<div class="error">${param.errorMessage}</div>
</c:if>
<div id="edit-profile-tabs">
    <ul>
        <li><a href="#info"><span>Info</span></a></li>
        <li><a href="#settings"><span>Settings</span></a></li>
        <li><a href="#account"><span>My Account</span></a></li>	        
    </ul>
	<div id="info">
		<form:form commandName="profileInfo" method="POST" action="/profile/updateProfileInfo">
			<button class="submitPersonal" type="submit" name="submitPersonal" value="submitPersonal">Update Info</button>
			<fieldset>
				<div class="formLayout">             	
					<label for="firstName">First Name</label>
					<form:input path="firstName" />
					<form:errors path="firstName" cssClass="error"/>
					<br />
					<label for="lastName">Last Name</label>
					<form:input path="lastName" />					
					<form:errors path="lastName" cssClass="error"/>
					<br />
					<label for="gender">Gender</label> 
					<form:select path="gender">
						<form:option value="Male">Male</form:option>
						<form:option value="Female">Female</form:option>
					</form:select>                                   	
					<br />        
					<label for="location">Location</label>
					<form:input path="location" />
					<form:errors path="location" cssClass="error"/>
					<br />
					<label>Birthday</label>   
					<mbs:dateSelector selectNamePrefix="dob" beginYear="1900" selectedDate="${member.profile.dob}"/>
					<form:errors path="dob" cssClass="error"/>				                                                                            
					<br />
					<label for="aboutMe">About Me <form:errors path="aboutMe" cssClass="error"/></label>
					<form:textarea path="aboutMe" />                     
					<br />
					<label for="interests">Interests <form:errors path="interests" cssClass="error"/></label>
					<form:textarea path="interests" />
					<br />
					<label for="favSpecies">Favorite Species To Hunt / Fish <form:errors path="favSpecies" cssClass="error"/></label>
					<form:textarea path="favSpecies" />
					<br />
					<label for="favGear">Favorite Hunting / Fishing Gear <form:errors path="favGear" cssClass="error"/></label>
					<form:textarea path="favGear" />
					<br />
					<label for="favMusic">Favorite Music <form:errors path="favMusic" cssClass="error"/></label>
					<form:textarea path="favMusic" />
					<br />
					<label for="favMovies">Favorite Movies <form:errors path="favMovies" cssClass="error"/></label>
					<form:textarea path="favMovies" />                 
				</div>
			</fieldset>
			<button class="submitPersonal" type="submit" name="submitPersonal" value="submitPersonal">Update Info</button>
		</form:form>
    </div>
    <div id="settings">
    	<form id="mySettings" action="/profile/updateSettings" method="POST">
			<fieldset>
				<p>                
					<input type="checkbox" name="showBirthday" id="showBirthday" <c:if test="${member.profile.showBirthday}">checked="checked"</c:if>/>
					<label for="showBirthday" class="checkboxLabel">Show my birthday</label>
				</p>
				<c:if test="${!member.profile.under18}">
					<p>
						<input type="checkbox" name="makePublic" id="makePublic" <c:if test="${member.profile.makePublic}">checked="checked"</c:if>/>
						<label for="makePublic" class="checkboxLabel">Make my profile Public (viewable by Everyone)</label>
					</p>
				</c:if>				
				<p>
					<input type="checkbox" name="disableAllEmailNotifications" id="noNotifications" <c:if test="${member.profile.disableAllEmailNotifications}">checked="checked"</c:if>/> 
					<label for="noNotifications" class="checkboxLabel">
				 		Please do not send notifications to my email account.
				 	</label>
				</p>                               
			</fieldset>
			<button id="submitSettings" type="submit" name="submitSettings" value="submitSettings">Update Settings</button>
		</form>
    </div>
    <div id="account">
    	<form:form commandName="loginInfo" action="/profile/updateLoginInfo" method="POST">
    		<fieldset>
	    		<div class="formLayout">	        
				    <label for="emailAddy">Email</label>
				    <form:input id="emailAddy" path="username" />
				    <form:errors path="username" cssClass="error"/>			    			    			    
					<br />
				    <label for="currPass">Current Password</label>
				    <form:password id="currPass" path="currPass" />
				    <form:errors path="currPass" cssClass="currPass"/>
					<br />
				    <label for="newPass">New Password</label>
				    <form:password id="newPass" path="newPass" />
				    <form:errors path="newPass" cssClass="newPass"/>
					<br />
				    <label for="newPassConf">Confirm New Password</label>
				    <form:password id="newPassConf" path="newPassConf" />				
				</div>
			</fieldset>
			<input type="submit" value="Update Account" />			
        </form:form>
    </div>
</div>