<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>User awards results</h1>
</head>
<body>
<%
	String option = request.getParameter("award_options");
	String num = request.getParameter("num");
	int opt = 0, n = 0;
	try
	{
		opt = Integer.parseInt(option);
		n = Integer.parseInt(num);
	}
	catch (Exception e)
	{
		%>
			<script>
				alert("Something went wrong <%=e%>");
			</script>
		<%
	}

	Connector con = new Connector();
	String results = "";
	if (opt == 1)
		results = User.getMostTrustedHTML(n, con.stmt);
	else if (opt == 2)
		results = User.getMostUsefulHTML(n, con.stmt);

	out.println(results + "<br>");
	con.stmt.close();
	con.closeConnection();
%>

<br>
<a href="awards.jsp">Back</a>
<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>