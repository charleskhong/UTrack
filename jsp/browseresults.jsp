<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Search results</h1>
</head>
<body>
<%
	String login = (String) session.getAttribute("login");
	String lo = request.getParameter("low");
	String hi = request.getParameter("high");
	double low = 0, high = 0;
	try
	{
		low = Double.parseDouble(lo);
		high = Double.parseDouble(hi);
	}
	catch (Exception e)
	{
	}

	String city = request.getParameter("city");
	String state = request.getParameter("state");
	String name = request.getParameter("keyword");
	String category = request.getParameter("cat");
	String sort = request.getParameter("sort_options");
	int sb = 0;
	try
	{
		sb = Integer.parseInt(sort);
	}
	catch (Exception e)
	{

	}


	Connector con = new Connector();
	String results = PlaceOfInterest.browsePOIHTML(login, high, low, city, state, name, category, sb, con.stmt, con.con);
	out.println(results);
	con.stmt.close();
	con.closeConnection();
%>

<br>
<a href="browse.jsp">Back</a>
<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>