<%@ page language="java" import="cs5530.*" %>
<html>
<head>
</head>

<body>

<%
String usr = request.getParameter("username");
String name = request.getParameter("name");
String pw = request.getParameter("password");
String addr1 = request.getParameter("addr1");
String addr2 = request.getParameter("addr2");
String city = request.getParameter("city");
String state = request.getParameter("state");
String zip = request.getParameter("zip");
String phone = request.getParameter("phone");
Connector con = new Connector();
User.addUser(usr, name, pw, addr1, addr2, city, state, zip, phone, con.stmt);
con.stmt.close();
con.closeConnection();

session.setAttribute("login", usr);
%>
<script>
	window.location = "menu.jsp";	
</script>

</body>
</html>