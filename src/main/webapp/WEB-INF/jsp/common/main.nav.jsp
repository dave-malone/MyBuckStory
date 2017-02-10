<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<ul class="main-site-nav">
	<mbs:menuLink uri="/index.html" title="Home" otherUris="equals:/" />   
	<%-- 
    <mbs:menuLink uri="/store" title="Store" otherUris="startsWith:/store/, startsWith:/order/, startsWith:/sellableItem/" />
     --%>
    <c:if test="${not empty isMyProfile && !isMyProfile}">    		
   		<li class="activelink selected"><a href="/profile/show/${member.id}/"><em>${member.profile.firstName}'s&nbsp;Profile</em></a></li>
   	</c:if>
   	    
    <mbs:menuLink uri="/stories.html" title="Stories" otherUris="startsWith:/stories/" />
    <mbs:menuLink uri="/image/list" title="Pictures" otherUris="startsWith:/image" />
    <mbs:menuLink uri="/videos" title="Videos" otherUris="startsWith:/video" />
    <mbs:menuLink uri="/contests" title="Contests" otherUris="startsWith:/contest" />
    <mbs:menuLink uri="/members.html" title="Members" otherUris="startsWith:/members/" />
    
    <%-- 
    <mbs:menuLink uri="/news.html" title="News" otherUris="startsWith:/news, startsWith:/article" />
    --%>
    <mbs:menuLink uri="/affiliates.html" title="Partners" otherUris="startsWith:/affiliate" />
    <%-- 
    <mbs:menuLink uri="/events.html" title="Events" otherUris="startsWith:/event" />
    --%>    
</ul>