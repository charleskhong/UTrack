<%@ page language="java" import="cs5530.*" %>
<%@ page language="java" import="java.util.*" %>

<html>
<head>
	<h1>Statistic results</h1>
</head>
<body>
<%
	String option = request.getParameter("stats_options");
	String num = request.getParameter("num");
	int opt = 0, n = 0;
	try
	{
		opt = Integer.parseInt(option);
		n = Integer.parseInt(num);
	}
	catch (Exception e)
	{
	}

	Connector con = new Connector();
	List<String> outputs = new ArrayList<String>();
	if (opt == 1)
		outputs = PlaceOfInterest.getMostPopularHTML(n, con.stmt);
	else if (opt == 2)
		outputs = PlaceOfInterest.getMostExpensiveHTML(n, con.stmt);
	else if (opt == 3)
		outputs = PlaceOfInterest.getHighestRatedHTML(n, con.stmt);

	for (String o : outputs)
		out.println(o + "<br>");
	con.stmt.close();
	con.closeConnection();
%>

<br>
<a href="statistics.jsp">Back</a>
<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>