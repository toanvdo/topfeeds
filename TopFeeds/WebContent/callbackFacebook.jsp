<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
function queryString(parameter) { 
	  var loc = location.search.substring(1, location.search.length);
	  var param_value = false;

	  var params = loc.split("&");
	  for (i=0; i<params.length;i++) {
	      param_name = params[i].substring(0,params[i].indexOf('='));
	      if (param_name == parameter) {
	          param_value = params[i].substring(params[i].indexOf('=')+1)
	      }
	  }
	  if (param_value) {
	      return param_value;
	  }
	  else {
	      return false; //Here determine return if no parameter is found
	  }
	}

		var h = window.location.hash;
		h=h.substring(1);
		
		var id =queryString('id');
		
		var url = 'AddFacebook?id='+id+'&' + h;

		document.location = url;
	</script>

</body>
</html>