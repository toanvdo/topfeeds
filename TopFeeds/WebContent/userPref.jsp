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

<title>User Preference</title>
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
			<li class="textbox"><span class="header">User Preference</span>
			</li>
		</ul>
		<form method="post" action="SaveUserPref">
			<fieldset>
				<span class="graytitle"> Do you Prefer recent posts?</span>
				<ul class="pageitem">
					<li class="select"><select name="recencyPref">
							<option value="100.0">Most Recent</option>
							<option value="75.0">More Recent</option>
							<option value="50.0" selected="selected">Neutral</option>
							<option value="25.0">So so</option>
							<option value="0.0">Doesn't Matter</option>
					</select><span class="arrow"></span></li>

				</ul>
				
				<span class="graytitle">Most Interactions Vs. Most Mutual Friends</span>
				<ul class="pageitem">
					<li class="select"><select name="socialPref">
							<option value="1.0">Most Interactions</option>
							<option value="0.75">More Interactions</option>
							<option value="0.5" selected="selected">Neutral</option>
							<option value="0.25">More Mutual Friends</option>
							<option value="0.0">Most Mutual Friends</option>
							
					</select><span class="arrow"></span></li>
				</ul>

				<span class="graytitle"> Most Popular Vs. Most Socially Close</span>
				<ul class="pageitem">
					<li class="select"><select name="popularPref">
							<option value="1.0">Most Popular</option>
							<option value="0.75">More Popular</option>
							<option value="0.5" selected="selected">Neutral</option>
							<option value="0.25">More Social</option>
							<option value="0.0">Most Social</option>
							
					</select><span class="arrow"></span></li>
				</ul>

				<span class="graytitle">Which Network do you prefer?</span>

				<ul class="pageitem">
					<li class="select"><select name="networkPref">
							<option value="FACEBOOK">Facebook</option>
							<option value="TWITTER">Twitter</option>
							<option value="NONE" selected="selected">I don't mind</option>
					</select><span class="arrow"></span></li>

					<li><input type="submit" value="Save Preference">
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