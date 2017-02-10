<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<c:catch var="e"><mbs:getCurrentUser id="user"/></c:catch>	
<c:if test="${not empty e}">${e.message}</c:if>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>	
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="verify-v1" content="KdGWojCK6jOAGbWl1TmpGcU4se2MHd8Zf/Np2aqzkk4=" />
		<tiles:insertAttribute name="seo" />
		<tiles:insertAttribute name="title" />
		<tiles:insertAttribute name="styles" />
		 <style type="text/css">
		    img, div { behavior: url(/scripts/iepngfix.htc) }
 		 </style>		
	</head>
	<body id="doc4" class="skin-mbs">
		<div id="hd">
			<div class="wrap">
				<tiles:insertAttribute name="header" ignore="true"/>
			</div>
		</div>
		<div id="bd">
			<div class="wrap">
				<div id="container" class="navset">	
					<tiles:insertAttribute name="main.nav" ignore="true"/>
					<div class="main-content">
						<div class="yui-ge">
   							<div class="mainholder">
   								<span id="ie6orless" style="visibility: collapse; color: red; padding: 1em; font-size:16px; font-weight:bold;">
   									Support for your browser is being phased out.  Please upgrade to a modern browser.
   								</span>
								<tiles:insertAttribute name="content" flush="true"/>
							</div><!-- End .yui-u first -->
						    <div class="sideholder">
						    	<%--
						    	<div class="share-cta" id="frontpage-cart">
									<a href="<c:url value="/order/cart" />" title="Cart">Cart: ${fn:length(cart)} Items</a>
								</div>
								 --%>
						    	<security:authorize access="isAnonymous()">
						    		<div class="share-cta" id="frontpage-login">
										<a href="<c:url value="/register/signin" />" title="Login">Sign In</a>
									</div>
						    		<div class="share-cta" id="frontpage-register">
										<a href="<c:url value="/register/signup" />" title="Sign Up">Sign Up</a>
									</div>		    
								</security:authorize>
								<security:authorize access="isAuthenticated()">
									<div class="share-cta" id="frontpage-story">
										<img id="sidebar-cta-mbslogo" alt="" src="<c:url value="/favicon.ico" />" />
							    		<a href="<c:url value="/story/create" />" title="Share a Story">Share a Story</a>
							    	</div>
							    	<div class="share-cta" id="frontpage-image">
							    		<img id="sidebar-cta-mbslogo" alt="" src="<c:url value="/favicon.ico" />" />
							    		<a href="<c:url value="/image/create" />" title="Share a Photo">Share a Photo</a>
							    	</div>
							    	<div class="share-cta" id="frontpage-video">
							    		<img id="sidebar-cta-mbslogo" alt="" src="<c:url value="/favicon.ico" />" />
							    		<a href="<c:url value="/video/create" />" title="Share a Video">Share a Video</a>
							    	</div>
							    	<div class="share-cta" id="frontpage-currentuser">
										<mbs:imageUri id="currentUserProfileImageUri" imageId="${user.profile.image.id}" maxWidthAndHeight="50"/>
	        							<img id="sidebar-currentuser-profilepic" alt="" src="${currentUserProfileImageUri}" />
										<a href="/profile/show/mine/" title="Go to your profile">											
											${user.profile.fullName}
										</a>										
										<img alt="menu" src="<c:url value="/images/down.png" />" id="drop-down-menu-icon" title="menu"/>
										<ul class="subnav">  
											<li><a href="<c:url value="/profile/edit" />" title="" id="editProfile">Edit Profile</a></li>
											<li><a href="<c:url value="/j_spring_security_logout" />" title="Sign Out">Sign Out</a></li>
								            <security:authorize access="hasRole('ROLE_ADMIN')">
								            	<li><a href="<c:url value="/admin.jsp" />" title="Admin">Admin</a></li>										    	    	
										    </security:authorize>
								        </ul>
									</div>									
								</security:authorize>	
						    
						        <tiles:insertAttribute name="ads" />                     
						    </div><!-- End .yui-u second -->
						</div><!-- End .yui-ge -->
					</div><!-- End .yui-content -->
				</div><!-- End .yui-container -->
			</div><!-- End .wrap -->			
			<div id="ft">
				<div class="wrap clearfix" style="padding: 5px;">
					<tiles:insertAttribute name="footer" />				
			    </div>
			</div>
		</div><!-- End #bd -->
		<div id="footer"></div>
		<!-- End #ft -->						
		<tiles:insertAttribute name="scripts" />
		<tiles:insertAttribute name="google.analytics" />
	</body>
</html>