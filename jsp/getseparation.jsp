<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Degrees of separation</h1>
</head>
<body>
<%
	String user1 = request.getParameter("user1");
	String user2 = request.getParameter("user2");

	Connector con = new Connector();
	int deg = Favorite.getDegreesOfSeparation(user1, user2, con.stmt);
	out.print(user1 + " and " + user2 + " ");
	if (deg == 0)
		out.println("are the same. They have 0 degrees of separation.");
	else if (deg == 1)
		out.println("have 1 degree of separation.");
	else if (deg == 2)
		out.println("have 2 degrees of separation.");
	else if (deg == -1)
		out.println("are separated by more than 2 degrees");
%>

<br>
<a href="separation.jsp">Back</a>
<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>