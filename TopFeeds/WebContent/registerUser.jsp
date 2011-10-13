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

<title>Register Topfeeds Account!</title>
</head>

<body>

	<div id="topbar">
		<div id="leftnav">
			<a href="index.jsp"><img src="images/home.png" alt="home">
			</a>
		</div>
	</div>
	<div id="content">
		<ul class="pageitem">
			<li class="textbox"><span class="header">Register
					TopFeeds Account</span>
			</li>
		</ul>
		<form method="post" action="RegisterUser">
			<div id="error">
				<%=request.getAttribute("error") == null ? "" : request
					.getAttribute("error")%>
			</div>
			<fieldset>
				<span class="graytitle">Enter Account Details</span>
				<ul class="pageitem">
					<li class="bigfield"><input type="text" name="username"
						placeholder="Username">
					</li>
					<li class="bigfield"><input type="password" name="password"
						placeholder="Password">
					</li>
				</ul>

				<ul class="pageitem">
					<li class="bigfield"><input type="password" name="password2"
						placeholder="Retype Password">
					</li>
				</ul>

				<span class="graytitle">User Details</span>
				<ul class="pageitem">
					<li class="smallfield"><span class="name">Name</span><input name ="name"
						type="text" placeholder="Name"></li>
					<li class="smallfield"><span class="name">Email</span><input name = "email"
						type="text" placeholder="Email"></li>

					<li class="button"><input type="submit"
						value="Register Account">
					</li>
				</ul>

			</fieldset>
		</form>
	</div>
	<div id="footer">
		<a href="http://iwebkit.net">Powered by iWebKit</a>
	</div>

</body>
</html>