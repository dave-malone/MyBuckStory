<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, org.apache.commons.codec.digest.DigestUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="fbAppId" value="155206304516832"/>
<c:set var="fbApiKey" value="3b74c988e6c7c399f8d8b45b7a24cfb9"/>
<c:set var="fbSecret" value="093f93bd857dfb9b8e8131eaa70e03d0"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>MyBuckStory.com Facebook Application</title>
</head>
<body>
Welcome to the MyBuckStory.com Facebook Application.  Share your hunting 
and fishing stories with our outdoor community from Facebook!
<hr />
<h4>Params</h4>


<ul>
<c:forEach items="${paramValues}" var="paramVal">
	<li>${paramVal.key}:${param[paramVal.key]}</li>
</c:forEach>
</ul>


<% 
	List<String> fbSigParams = new ArrayList<String>();
	
	for(Enumeration<String> paramNames = request.getParameterNames(); paramNames.hasMoreElements();){
		String name = paramNames.nextElement();
		if(name.startsWith("fb_sig_")){
			fbSigParams.add(name);
		}
	}
	//fb requires that the params get sorted alphabetically
	//before using them to create the MD5 hash
	Collections.sort(fbSigParams);
	
	StringBuffer fbSigBuffer = new StringBuffer();	
	for(String name : fbSigParams){		
		fbSigBuffer.append(name.replaceFirst("fb_sig_", ""));
		fbSigBuffer.append("=");
		fbSigBuffer.append(request.getParameter(name));		
	}
	//application secret
	fbSigBuffer.append(pageContext.findAttribute("fbSecret"));
	
	String md5 = DigestUtils.md5Hex(fbSigBuffer.toString());
	boolean reqFromFB = md5.equals(request.getParameter("fb_sig"));
%>
Authentic FB request? <%= reqFromFB %><br />
Calculated MD5: <%= md5 %>

<hr />
<h4>Cookies</h4>
<ul>
<c:set var="isLoggedIntoFB" value="${false}"/>

<c:forEach items="${cookie}" var="cke">
	<li>${cke.key}:${cookie[cke.key].value}</li>
	<c:if test="${'uid' eq cke.key}">
		<c:set var="isLoggedIntoFB" value="${true}"/>	
		<c:set var="uid" value="${cookie[cke.key].value}" />		
	</c:if>
	<c:if test="${'access_token' eq cke.key}">
		<c:set var="access_token" value="${cookie[cke.key].value}" />
	</c:if>
</c:forEach>
</ul>
<hr />

<c:if test="${isLoggedIntoFB}">
	Your user ID is ${uid}<br />
	Access token: ${access_token}
</c:if>
<c:if test="${!isLoggedIntoFB}">
	<fb:login-button></fb:login-button>
</c:if>


<div id="fb-root"></div>
<script>
  window.fbAsyncInit = function() {
    FB.init({appId: '${fbAppId}', status: true, cookie: true,
             xfbml: true});
  };
  (function() {
    var e = document.createElement('script'); e.async = true;
    e.src = document.location.protocol +
      '//connect.facebook.net/en_US/all.js';
    document.getElementById('fb-root').appendChild(e);
  }());
</script>

</body>
</html>


