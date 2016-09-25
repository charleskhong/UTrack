<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Most useful results</h1>
</head>
<body>
<%
	String id = request.getParameter("pid");
	String num = request.getParameter("num");
	int pid = 0, n = 0;
	try
	{
		pid = Integer.parseInt(id);
		n = Integer.parseInt(num);
	}
	catch (Exception e)
	{
	}

	Connector con = new Connector();
	String results = Opinion.getMostUsefulOpinionsHTML(pid, n, con.stmt, con.con);
	out.println(results);
	con.stmt.close();
	con.closeConnection();
%>

<br>
<a href="mostuseful.jsp">Back</a>
<br>
<a href="menu.jsp">Back To Menu</a>

</body>
</html>