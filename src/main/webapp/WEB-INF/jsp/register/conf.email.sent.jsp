<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
	<h2>Confirm Your Email Address</h2>
	<br />
	<div style="border: #d9acac solid 1px; color: #ff000; padding: 1em;">
		<b>Thanks for signing up for an account on MyBuckStory.com!</b><br />
		We have sent an email to <strong>${user.username}</strong>. <br /><br />
		<b>1.)</b> Check your email address you provided. You may need to check your <b style="color:red">SPAM</b> box and mark it as 'not spam'.<br />
		<b>2.)</b> Follow the confirmation link within the email to activate your account and sign in.<br />
		<b>3.)</b> Once activated, you are free to customize your profile, post stories, and comment.<br />
	</div>
	<br />
	<br />		
	<p>
		Thanks for signing up for an account on MyBuckStory.com! We just sent you a confirmation email to 
		<strong>${user.username}</strong>.  
	</p>
	<br />
	<br />
	<p>
		<h2>Tell your friends about us</h2>
		<form action="/refer/submit" method="POST">						
			<label for="email1">Email # 1:</label>
			<input name="email1" id="email1"/>
			<label for="email2">Email # 2:</label>
			<input name="email2" id="email2"/>
			<label for="email3">Email # 3:</label>
			<input name="email3" id="email3"/>
			<label for="email4">Email # 4:</label>
			<input name="email4" id="email4"/>
			
			<label for="message">Message</label>
			<textarea class="textarea" id="message" name="message">Hey!  I've found a great social network for outdoor enthusiasts where we get to connect with fellow enthusiasts, share hunting or fishing stories, friends can make comments on them, and there's even a section with the latest outdoors news.  You should sign up for a free account and	add me as your friend when you get there!</textarea>
			<input type="hidden" name="from" value="${user.profile.fullName}" />
			<input type="hidden" name="fromEmail" value="${user.username}" />
			<br />
			<a href="<c:url value="/" />">Skip</a>
			<input type="submit" value="Send" />
		</form>
	</p>	
	<%-- p>Have questions? <a href="http://www.facebook.com/help.php?topic=signup">See Help</a>.</p--%>