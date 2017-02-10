<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<h2>${member.profile.firstName}${fn:endsWith(member.profile.firstName, 's') ? '\'' : '\'s'} Info</h2>
<div id="myInfo">
	<h3>Location</h3>
	<p>
		${member.profile.location}
	</p>
	<c:if test="${member.profile.showBirthday}">
		<h3>Birthday</h3>
		<p>
			<fmt:formatDate value="${member.profile.dob}" pattern="MMMM d, yyyy"/>
		</p>
	</c:if>
	<h3>About Me</h3>
    <p>
    	${member.profile.about}
    </p>
    <h3>Interests</h3>
    <p>
    	${member.profile.interests}
    </p>
    <h3>Favorite Species To Hunt / Fish</h3>
    <p>
    	${member.profile.favoriteSpecies}
    </p>
    <h3>Favorite Hunting / Fishing Gear</h3>
    <p>
    	${member.profile.favoriteGear}
    </p>
	<h3>Favorite Music</h3>
    <p>
    	${member.profile.favoriteMusic}
    </p>
    <h3>Favorite Movies</h3>
    <p>
    	${member.profile.favoriteMovies}
    </p>
</div>