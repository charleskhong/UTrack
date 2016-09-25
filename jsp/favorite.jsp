<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Favorite a place</h1>

<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.pid.value == "")
	{
		alert("Fields should be nonempty");
		return false
	}

	var pid = Number(form_obj.pid.value);
	if (pid !== pid)
	{
		alert("Numbers only please");
		return false;
	}

	return true;
}
</script>
</head>
<body>
	Which place do you want to favorite<br><br>

<%
	String action = request.getParameter("action");
	if (action != null && action.equals("failed"))
	{
%>
		<script>
			alert("Failed to favorite POI");
		</script>
<%		
	}
	else if (action != null && action.equals("success"))
	{
%>
		<script>
			alert("Successfully favorited POI");
		</script>
<%
	}

	Connector con = new Connector();
	String places = PlaceOfInterest.getPlacesHTML(con.stmt, con.con);
	out.println(places);
	con.stmt.close();
	con.closeConnection();
%>

	<form name="favorite_form" method=post onsubmit="return check_all_fields(this)" action="addfavorite.jsp">
		PID: <input type=text name="pid" length=3>
		<input type=submit value="Add Favorite"/>
	</form>

	<br>
	<a href="menu.jsp">Back To Menu</a>
</body>
</html>