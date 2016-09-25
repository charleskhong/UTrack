<%@ page language="java" import="cs5530.*" %>
<html>
<head>
</head>

<body>

<%
String username = request.getParameter("username");
String password = request.getParameter("password");
Connector con = new Connector();
boolean successful = User.login(username, password, con.stmt);
con.stmt.close();
con.closeConnection();
if (successful)
{
	session.setAttribute("login", username);
%>
<script>
	window.location = "menu.jsp";	
</script>
<%
}
else
{
%>

<script>
	var form = document.createElement('form');
	form.method = 'post';
	form.action = 'login.jsp'
	var input = document.createElement('input');
	input.type = 'text';
	input.name = 'failed';
	input.value = 'failed';
	form.appendChild(input);
	form.submit();
</script>	

<%
}
%>
</body>
</html>