<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mbs" uri="http://mybuckstory.com/tags" %>
<%@page import="java.util.Iterator"%>
<%@page import="net.sf.ehcache.*"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en" id="top">
<head>
	<title>Ehcache Manager</title>
</head>
<body>

<h1>All Caches</h1>

<%
	List cacheManagers = CacheManager.ALL_CACHE_MANAGERS;
	
	for(Iterator iter = cacheManagers.iterator(); iter.hasNext();){
		Object obj = iter.next();
		CacheManager manager = (CacheManager)obj;		
		
%>
	<h2>Cache Manager: <%= manager %></h2>
	<ul>
<%
		String[] names = manager.getCacheNames();
		Arrays.sort(names);
		for(String cacheName : names){
%>
	<li><a href="?id=<%= cacheName %>&mgrName=<%= manager.getName() %>"><%= cacheName %></a></li>
<%
		}
%>
	</ul>
<%
	}
%>
<hr />
<c:if test="${not empty param.id}">
<%
	CacheManager manager = null;
	for(Iterator iter = cacheManagers.iterator(); iter.hasNext();){
		CacheManager mgr = (CacheManager)iter.next();
		
		if(mgr.getName().equals(request.getParameter("mgrName"))){
			manager = mgr;
			break;
		}
	}
	Cache cache = manager.getCache(request.getParameter("id"));
%>
<h2>Cache: ${param.id}</h2>	
	<c:if test="${param.command == 'clear'}">
		<% cache.removeAll(); %>
		<%= cache.getName() %> Cleared
	</c:if>
	<c:if test="${param.command == 'evict'}">
		<% cache.evictExpiredElements(); %>
		<%= cache.getName() %> - Expired Elements Evicted
	</c:if>
	
	
	<a href="?id=${param.id}&command=clear">Clear Cache</a><br />
	<a href="?id=${param.id}&command=evict">Evict Expired Elements</a><br />
	<br />
		
	Cache Size: <%= cache.getSize() %>
	
	<h3>Memory Usage</h3>	
	In Memory Size: <%= cache.calculateInMemorySize() %> (bytes)<br />
	Memory Store Size: <%= cache.getMemoryStoreSize() %><br />
	
	Eviction Policy: <%= cache.getMemoryStoreEvictionPolicy() %>
	
	<h3>Disk Usage</h3>
	Disk Store Size: <%= cache.getDiskStoreSize() %><br />
		
	
	<h3>Cache Statistics</h3>
	<% Statistics stats = cache.getStatistics(); %>
	Cache Hits: <%= stats.getCacheHits() %><br />
	Cache Misses: <%= stats.getCacheMisses() %><br />
	In Memory Hits: <%= stats.getInMemoryHits() %><br />
	Object Count: <%= stats.getObjectCount() %><br />
	On Disk Hits: <%= stats.getOnDiskHits() %><br />
	Statistics Accuracy: <%= stats.getStatisticsAccuracy() %><br />
	
	<table border="1" cellpadding="3" cellspacing="3">
		<tr>
			<th>
				Key
			</th>
			<th>
				In Memory?
			</th>
			<th>
				On Disk?
			</th>
			<th>
				Creation Time
			</th>
			<th>
				Expiration Time
			</th>
			<th>
				Last Accessed
			</th>
			<th>
				Last Updated
			</th>
			<th>
				Hit Count
			</th>
		</tr>
	<% 
		for(Iterator iter = cache.getKeys().iterator(); iter.hasNext();){
			Object key = iter.next();
			Element element = cache.get(key);
			if(element != null){
	%>
		<tr>
			<td>
				<%= element.getKey() %>
			</td>			
			<td>
				<%= cache.isElementInMemory(element.getKey())  %>
			</td>
			<td>
				<%= cache.isElementOnDisk(element.getKey()) %>
			</td>
			<td>
				<fmt:formatDate value="<%= new java.util.Date(element.getCreationTime()) %>" pattern="MMM dd, yyyy h:mm a"/>				
			</td>
			<td>
				<fmt:formatDate value="<%= new java.util.Date(element.getExpirationTime()) %>" pattern="MMM dd, yyyy h:mm a"/>				
			</td>
			<td>
				<fmt:formatDate value="<%= new java.util.Date(element.getLastAccessTime()) %>" pattern="MMM dd, yyyy h:mm a"/>				
			</td>
			<td>
				<fmt:formatDate value="<%= new java.util.Date(element.getLastUpdateTime()) %>" pattern="MMM dd, yyyy h:mm a"/>				
			</td>
			<td>
				<%= element.getHitCount() %>
			</td>
		</tr>
	<%
			}else{
	%>
		<tr><td colspan="8">Cache Element with key <%= key %> was null</td></tr>
	<%
			}
		}
	%>
	</table>
	
</c:if>

	

</body>
</html>
