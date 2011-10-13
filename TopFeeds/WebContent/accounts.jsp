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
<%@ page import="au.edu.unsw.cse.topfeeds.model.Account"%>
<%@ page import="java.util.List"%>


<body>

	<div id="topbar">
		<div id="leftnav">
			<a href="menu.jsp"><img src="images/home.png" alt="home"> </a>
		</div>
	</div>
	<div id="content">
		<ul class="pageitem">
			<li class="textbox"><span class="header">Linked Accounts</span>
			</li>
		</ul>
		<fieldset>
			<span class="graytitle">Accounts</span>
			
			<div id="error">
				<%=request.getAttribute("error") == null ? "" : request
					.getAttribute("error")%>
			</div>
			<ul class="pageitem">
				<%
					List<Account> accts = (List<Account>) request
							.getAttribute("accounts");
					if (accts != null) {
						for (Account acct : accts) {
				%>

				<li class="menu"><a class="noeffect"
					href="showAccDetail?id=<%=acct.getId()%>"> <img
						src="thumbs/<%=acct.getType()%>.png" alt="<%=acct.getType()%>">
						<span class="name"><%=acct.getUsername()%></span> <span
						class="arrow"></span> </a></li>
				<%
					}
					}
				%>
				<li class="menu"><a class="noeffect" href="selectAccounts.jsp">
						<span class="name">+ Add Account </span> <span class="arrow"></span>
				</a>
				</li>

				</li>
		</fieldset>
		</form>
	</div>
	<div id="footer">
		<a href="http://iwebkit.net">Powered by iWebKit</a>
	</div>

</body>
</html>