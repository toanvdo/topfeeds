<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="index,follow" name="robots" />
<meta content="text/html; charset=iso-8859-1" http-equiv="Content-Type" />
<link href="pics/homescreen.gif" rel="apple-touch-icon" />
<meta
	content="minimum-scale=1.0, width=device-width, maximum-scale=0.6667, user-scalable=no"
	name="viewport" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/mozillaStyle.css" />

<script src="javascript/functions.js" type="text/javascript"></script>
<title>TopFeeds - Display</title>
</head>
<%@ page import="au.edu.unsw.cse.topfeeds.model.Post"%>
<%@ page import="java.util.List"%>

<body class="list">

	<div id="topbar">
		<div id="leftnav">
			<a href="menu.jsp"><img alt="home" src="images/home.png" /> </a>
		</div>
		<div id="title"><%=request.getAttribute("type")%> Feed</div>
	</div>
	<div id="content">
		<ul>
			<%
				List<Post> posts = (List<Post>) request.getAttribute("posts");
				for (Post post : posts) {
			%>
			<li class="withimage"><a class="noeffect"
				href="<%=post.getUrl()%>"> <img alt="<%=post.getSenderId()%>"
					src="" /><span class="name"><%=post.getSenderName()%></span><span
					class="comment"><%=post.getMessage()%></span> </a></li>

			<%
				}
			%>

		</ul>
	</div>
	<div id="footer">
		<a href="http://iwebkit.net">Powered by iWebKit</a>
	</div>

</body>

</html>
