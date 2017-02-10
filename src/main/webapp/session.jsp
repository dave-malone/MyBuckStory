<!--

	Copyright (c) 2003 BEA Systems, Inc.
	All rights reserved

	THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF BEA Systems, Inc.
	The copyright notice above does not evidence any actual or intended
	publication of such source code.
	
-->
<%@ page language="java" import="java.io.*, java.util.*" session="true" %>
<html>
<head>
<title>BEA Web application session inspector</title>
</head>
<body>
<h3>BEA Global Alliances Technical Services</h3> 
<h1>Web application session inspector</h1>
<h2>Introduction</h2>
This simple HttpSession inspector provides an estimate of the total size of the HTTP session  
and the size of each session attribute. These estimates are based on the amount of
memory required to serialize each attribute, which gives a fair representation 
of the amount of effort required to replicate the session across a cluster. As a rule of thumb,
the session size should not exceed 50 kB. 
<p>
Some recommendations to keep the session size manageable: <ul>
<li>Use page scope for temporary objects</li>
<li>Explicitly remove objects from the session when they are no longer used</li>
<li>Avoid storing large objects the session. Specifically, collections and maps such as Hashtable, 
HashMap, Vector, etc. should not be stored in the session.</li>
</ul>
<h2>Session information</h2> 
<%
		ObjectOutputStream oos = null;		
		long totalSize = 0;
		TreeMap map = new TreeMap();
		TreeMap nonserializable = new TreeMap(); 
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			
			// Write some fake data to init the output stream
			oos.writeObject("start");
			oos.flush();
			baos.reset();
			
			// Loop through the session and calculate the size of each attribute
			for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
				String attrName = (String) e.nextElement();
				Object attrValue = session.getAttribute(attrName);
				try {
					baos.reset();
					oos.writeObject(attrName);
					oos.flush();
					long nameSize = baos.size();
					totalSize += nameSize;
					
					baos.reset();
					oos.writeObject(attrValue);
					oos.flush();
					long valueSize = baos.size();
					totalSize += valueSize;
					
					
					Long size = new Long(nameSize+valueSize);
					map.put(size, attrName);			
				} catch (NotSerializableException nse) {
					String errorClassName = nse.getMessage();
					nonserializable.put(attrName, errorClassName);
				}
			}
		} finally {
				if (oos != null) oos.close();
		}
%>
<pre>Session id: <%= session.getId() %>
Session created: <%= new java.sql.Timestamp(session.getCreationTime()) %>
Total session size: <%
		if (totalSize > 50*1024) {
%><font color="red"><b><%
		}
%><%= Long.toString(totalSize) %> bytes (<%= Long.toString(totalSize/1024) %> kB)<% 
		if (totalSize > 50*1024) {
%></b></font><%
		}
%></pre>
<%
		if (totalSize > 50*1024) {
%><font color="red"><b>Attention! Your total session size is larger than 50 kB, which may result in 
performance problems when replicating across a cluster. It is highly recommended that
you reduce the size of the HTTP session by following the recommendations above.</b></font>
<% 
	}
%>
<h2>Session attributes</h2>
The estimated size in the table below is based on the amount of memory (in bytes) required to serialize 
both the name and the value of each attribute. The table is sorted by the size of 
the attributes, with the largest attributes shown first. Collection or map objects are marked in 
<b><font color="red">red</font></b>. Non-serializable attributes are also 
indicated: you should only store serializable objects in the session.
<br /><br />
<table border="1" cellpadding="2">
<tr><th>Attribute name</th><th>Estimated size (bytes)</th><th>Class</th><th>Comments</th></tr>
<%
		for (Iterator iter = nonserializable.keySet().iterator(); iter.hasNext();) {
			String attrName = (String) iter.next();
			Object attrValue = session.getAttribute(attrName);
			String attrClass = attrValue.getClass().getName();
			String errorClassName = (String) nonserializable.get(attrName);
			boolean isCollection = (attrValue instanceof Collection) || (attrValue instanceof Map);
			if (attrValue instanceof Collection) {
				int size = ((Collection)attrValue).size();
				String contentType = "N/A";
				if (size > 0) {
					contentType = "[" + ((Collection)attrValue).iterator().next().getClass().getName() + "]";
				}
				attrClass = attrClass + " size=" + size + ", " + contentType;
				
			} else if (attrValue instanceof Map) {
				int size = ((Map)attrValue).entrySet().size();
				String contentType = "N/A";
				if (size > 0) {
					Map.Entry entry = (Map.Entry)((Map)attrValue).entrySet().iterator().next();
					contentType = "[" + entry.getKey().getClass().getName() + "/" + entry.getValue().getClass().getName() + "]";
				}
				attrClass = attrClass + " keys=" + size + ", " + contentType;
			}
		
%><tr>
	<td><pre><%=attrName %></pre></td>
	<td><pre><b><font color="red">Not Serializable</font></b></pre></td>
	<td><pre><% 
		if (isCollection) { 
			%><b><font color="red"><% 
		} %><%=attrClass %><% 
		if (isCollection) { 
			%></font></b><% 
		} %></pre></td>
	<td><pre>Contains a non-serializable object of class "<%= errorClassName %>"</pre></td>
</tr>
<%				
  	}
  	Object[] array = map.keySet().toArray();
		for (int i = array.length; --i >= 0; ) {
			Object attrSize = array[i];
			String attrName = (String) map.get(attrSize);
			Object attrValue = session.getAttribute(attrName);
			String attrClass = attrValue.getClass().getName();
			boolean isCollection = (attrValue instanceof Collection) || (attrValue instanceof Map);
			if (attrValue instanceof Collection) {
				int size = ((Collection)attrValue).size();
				String contentType = "N/A";
				if (size > 0) {
					contentType = "[" + ((Collection)attrValue).iterator().next().getClass().getName() + "]";
				}
				attrClass = attrClass + " size=" + size + ", " + contentType;
				
			} else if (attrValue instanceof Map) {
				int size = ((Map)attrValue).entrySet().size();
				String contentType = "N/A";
				if (size > 0) {
					Map.Entry entry = (Map.Entry)((Map)attrValue).entrySet().iterator().next();
					contentType = "[" + entry.getKey().getClass().getName() + "/" + entry.getValue().getClass().getName() + "]";
				}
				attrClass = attrClass + " keys=" + size + ", " + contentType;
			}


%><tr>
	<td><pre><%= attrName %></pre></td>
	<td><pre><%=attrSize %></pre></td>
	<td><pre><% 
		if (isCollection)  { 
			%><b><font color="red"><% 
		} %><%=attrClass %><% 
		if (isCollection) { 
			%></font></b><% 
		} %></pre></td>
	<td><% 
		if (isCollection) { 
			%><pre>Avoid storing collections or maps in the HTTP session</pre><% 
		} %>&nbsp;</td>
</tr>
<%			
}
%>
</table>
<br />
<font size="-2">Copyright (c) 2003 BEA Systems, Inc.</font>
</body>
</html>