<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="minimum-scale=1.0, width=device-width, maximum-scale=0.6667, user-scalable=no" name="viewport" />
<link href="css/style.css" rel="stylesheet" media="screen" type="text/css" />
<link href="css/mozillaStyle.css" rel="stylesheet" media="screen" type="text/css" />
<script src="javascript/functions.js" type="text/javascript"></script>

<title>Add Accounts</title>
</head>
<%@ page import="au.edu.unsw.cse.topfeeds.model.TopFeedsUser"%>

<body>

<div id="topbar">
	<div id="leftnav">
		<a href="menu.jsp"><img src="images/home.png" alt="home"></a><a href="GetAccounts">Accounts</a></div>
		
</div>
<div id="content">
	<ul class="pageitem">
		<li class="textbox"><span class="header">Select Account</span></li>
	</ul>
		<fieldset><span class="graytitle">Accounts</span>
		<ul class="pageitem">
			<li class="menu">
			<%
				TopFeedsUser tfu = (TopFeedsUser) session.getAttribute("user");
					
				if (tfu == null) {
					response.sendRedirect("index.jsp");
				}
			%>
			<a class="noeffect" href="RegisterAccount?type=FACEBOOK">
			<img src="thumbs/FACEBOOK.png" alt="facebook"/>
				<span class="name">Add Facebook</span>
				<span class="arrow"></span>
			</a>
		</li>
			<li class="menu">
			<a class="noeffect" href="RegisterAccount?type=TWITTER">
			<img src="thumbs/TWITTER.png" alt="twitter"/>
				<span class="name">Add Twitter</span>
				<span class="arrow"></span>
			</a>
		</li>
		
			<li class="smallfield"><span class="name">More Coming soon!</span>
			</li>

		</ul>
		
		</fieldset>
</div>
<div id="footer">
	<a href="http://iwebkit.net">Powered by iWebKit</a></div>

</body>
</html>