<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<div class="main-content">
	<a href="<c:url value="/admin.jsp" />">Admin Home</a> &gt;
	<a href="<c:url value="/user/list/" />">Manage Users</a>
	<br />
	<h2>${param.mode} User</h2>
	<form:form action="/user/update" enctype="multipart/form-data" method="POST">
		<fieldset>
			<p>
				<form:errors path="*" />
			</p>
			<p>
				Enabled: <form:checkbox path="enabled" />
			</p>
			<p>
				Pro Staff: <form:checkbox path="proStaff" />
			</p>
			<p>
				<label for="username">Email</label>
				<form:input path="username" />
			</p>
			<p>
				<label for="password">Password</label>
				<form:password path="password" showPassword="true"/>
			</p>
			<p>
				<label for="profile.firstName">First Name</label>
				<form:input path="profile.firstName" />
			</p>
			<p>
				<label for="profile.lastName">Last Name</label>
				<form:input path="profile.lastName" />
			</p>
			<p>
				<label for="roles">Roles</label>
				<form:select path="roles" items="${roles}" itemValue="id" itemLabel="name" />
			</p>
		</fieldset>
		<form:hidden path="id"/>
		<input type="Submit" value="Update User" />
	</form:form>                                  
</div>
	    