<%@ page language="java" import="cs5530.*" %>
<%@ page language="java" import="java.util.*" %>
<html>
<head>
	<h1>Checkout your visits</h1>
</head>
<body>
	Visit Cart:
	<br>
<%
	ArrayList<Visit> visits = (ArrayList<Visit>) session.getAttribute("visits");
	if (visits == null)
		visits = new ArrayList<Visit>();
	String table = "<table style\"width:100%\"><tr><td>pid</td><td>cost</td><td>party size</td><td>date</td></tr>";
	for (Visit v : visits)
		table += "<tr><td>" + v.pid + "</td><td>" + v.cost + "</td><td>" + v.size + "</td><td>" + v.date + "</td></tr>";
	table += "</table>";
	out.println(table);
%>

<br>
<% 
	if (visits.size() != 0)
	{
%>
		<form name="cart_checkout" action="cartaction.jsp" method=post>
			<input type=hidden name="cart" value="cart_addall"/>
			<input type=submit value="Checkout Visits"/>
		</form>
		<form name="cart_delete" action ="cartaction.jsp" method=post>
			<input type=hidden name="cart" value="cart_removeall"/>
			<input type=submit value="Delete Visits"/>
		</form>
<%
	}
%>

<br>
<a href="menu.jsp">Back to menu</a>

</body>
</html>