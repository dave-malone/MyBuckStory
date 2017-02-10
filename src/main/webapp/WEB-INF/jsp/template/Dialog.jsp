<%@ page session="false" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>	
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 		
		<title>MyBuckStory.com - What's your story?<tiles:insertAttribute name="title" ignore="true"/></title>
		<tiles:insertAttribute name="styles"/>
		<style type="text/css">
			html {background: none; margin:0; padding:0;}
		</style>
	</head>
	<body>
		<tiles:insertAttribute name="content" />
		<tiles:insertAttribute name="scripts" />
		<tiles:insertAttribute name="google.analytics" />	
	</body>
</html>