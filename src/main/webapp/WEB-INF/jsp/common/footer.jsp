<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<div>
	<ul>
	    <li><b>My Buck Story</b></li>
	    <li><a href="<c:url value="/" />">Home</a></li>
	    <security:authorize access="isAuthenticated()">
	    	<li><a href="<c:url value="/profile/show/mine" />">My Profile</a></li>
	    </security:authorize>
	    <li><a href="<c:url value="/stories.html" />">Stories</a></li>
	    <li><a href="<c:url value="/members.html" />">Members</a></li>
	    <li><a href="<c:url value="/news.html" />">News</a></li>
	</ul>
</div>
<div>
	<ul>
	    <li><b>Recommended Sites</b></li>
	    <mbs:footerLinkIterator category="recommendedSites" id="link">
	    	<li><a href="${link.url}" rel="nofollow">${link.name}</a></li>	
	    </mbs:footerLinkIterator>
	</ul>
</div>
<div>
	<ul>
	    <li><b>Resources</b></li>
	    <mbs:footerLinkIterator category="resources" id="link">
	    	<li><a href="${link.url}" rel="nofollow">${link.name}</a></li>	
	    </mbs:footerLinkIterator>
	</ul>
</div>
<div>
	<ul>
	    <li><b>Extras</b></li>    
	    <li><a href="<c:url value="/affiliates.html" />">Affiliates</a></li>
	    <li><a href="<c:url value="/privacy.html" />">Privacy Policy</a></li>
	    <li><a href="<c:url value="/tos.html" />">Terms Of Service</a></li>
	    <li><a href="<c:url value="/howto.html?KeepThis=true&TB_iframe=true&height=450&width=750" />" class="thickbox" title="FAQ">FAQ</a></li>
	    <li><a href="<c:url value="/howto.html?KeepThis=true&TB_iframe=true&height=450&width=750" />" class="thickbox" title="Advertise on MyBuckStory.com">Advertise on MyBuckStory.com</a></li>
	    <li><a href="<c:url value="/howto.html?KeepThis=true&TB_iframe=true&height=450&width=750" />" class="thickbox" title="Become an Affiliate">Become an Affiliate</a></li>
	    <li><a href="<c:url value="/howto.html?KeepThis=true&TB_iframe=true&height=450&width=750" />" class="thickbox" title="Report a Problem">Report a Problem</a></li>
	    <mbs:footerLinkIterator category="extras" id="link">
	    	<li><a href="${link.url}" rel="nofollow">${link.name}</a></li>	
	    </mbs:footerLinkIterator>    
	</ul>
</div>
<div id="copy">
	&copy; 2009 Badava Group, LLC
</div>