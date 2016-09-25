<%@ page language="java" import="cs5530.*" %>
<%@ page language="java" import="java.util.*" %>
<html>
<head>
	<h1>Suggestions of places for you to visit</h1>
</head>
<body>
<%
	String login = (String) session.getAttribute("login");
	Connector con = new Connector();

	List<Integer> visited = Visit.getVisited(login, con.stmt);
	for (int pid : visited)
	{
		String name = PlaceOfInterest.getName(pid, con.stmt);
		out.println("Because you visited " + name + " we suggest");
		List<PlaceOfInterest> sugg = Visit.suggestedPOIs(pid, login, con.stmt);
		String suggestions = PlaceOfInterest.suggestedPlacesHTML(sugg, con.stmt);
		out.println(suggestions + "<br><br>");
	}
%>

<a href="menu.jsp">Back To Menu</a>
</body>
</html>