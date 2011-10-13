<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta
	content="minimum-scale=1.0, width=device-width, maximum-scale=0.6667, user-scalable=no"
	name="viewport" />
<link href="css/style.css" rel="stylesheet" media="screen"
	type="text/css" />
<link href="css/mozillaStyle.css" rel="stylesheet" media="screen"
	type="text/css" />
<script src="javascript/functions.js" type="text/javascript"></script>

<title>Accounts!</title>
</head>
<%@ page import="au.edu.unsw.cse.topfeeds.model.TopFeedsUser"%>
<body>

	<div id="topbar">
		<div id="rightnav">
			<a href="Logout">Logout</a>
		</div>
	</div>
	<div id="content">
		<ul class="pageitem">
			<li class="textbox"><span class="header">Topfeeds Home</span></li>
		</ul>
		<fieldset>
			<span class="graytitle">Welcome <%
				TopFeedsUser tfu = (TopFeedsUser) session.getAttribute("user");

				if (tfu == null) {
					response.sendRedirect("index.jsp");
				} else {
					out.println(tfu.getUsername());
				}
			%>! </span>

			<div id="error">
				<%=request.getAttribute("error") == null ? "" : request
					.getAttribute("error")%>
			</div>

			<ul class="pageitem">
				<li class="menu"><a class="noeffect" href="GetFeeds?ranked=true&page=0">
						<img src="thumbs/feeds.png" alt="Feeds"> <span class="name">Ranked Feeds</span> 
							<span class="arrow"></span> </a>
				</li>

				<li class="menu"><a class="noeffect" href="GetFeeds?ranked=false&page=0">
						<img src="thumbs/feeds.png" alt="Feeds"> <span class="name">Most Recent Feeds</span> 
						<span class="arrow"></span> </a>
				</li>

				<li class="menu"><a class="noeffect" href="GetAccounts">
						<img src="thumbs/settings.png" alt="Accounts"> <span
						class="name">Accounts</span> <span class="arrow"></span> </a>
				</li>

				<li class="menu"><a class="noeffect" href="GetUserPref">
						<img src="thumbs/settings.png" alt="User Pref"> <span
						class="name">User Preference</span> <span class="arrow"></span> </a>
				</li>

				<li class="smallfield"><span class="name">More Coming
						soon!</span>
				</li>

			</ul>

		</fieldset>
	</div>
	<div id="footer">
		<a href="http://iwebkit.net">Powered by iWebKit</a>
	</div>

</body>
</html>