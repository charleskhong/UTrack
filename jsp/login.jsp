<%@ page language="java" import="cs5530.*" %>
<html>
<head>

<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.username.value == "" || form_obj.password.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}
	return true;
}
</script>

</head>
<body>

	<form name="login" method=post onsubmit="return check_all_fields(this)" action="trylogin.jsp">
		Username: <input type=text name="username" length =40>
		<BR>
		Password: <input type=password name="password" length=40>
		<input type=submit value="Login"/>
	</form>
<%
String loginFailed = request.getParameter("failed");
if (loginFailed != null)
{
%>	
	<BR>
	Login failed. Please try again.
<%
}
%>

	<BR>
	<a href="register.jsp">Register as a new user</a>

 </body>
 </html>