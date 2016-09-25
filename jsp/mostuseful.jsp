<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Most useful feedbacks for a POI</h1>
<script language="javascript">
function check_all_fields(form)
{
	var pid_box = form.pid;
	var num_box = form.num;
	if (pid_box.value == "" || num_box.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}

	var pid = Number(pid_box.value);
	var num = Number(num_box.value);

	if (pid !== pid || num !== num)
	{
		alert("Numbers only please");
		return false;
	}

	return true;
}
</script>
</head>
<body>
	Which place do you want feedback for<br><br>

<%
	Connector con = new Connector();
	String places = PlaceOfInterest.getPlacesHTML(con.stmt, con.con);
	out.println(places);
	con.stmt.close();
	con.closeConnection();
%>

	<form name="most_useful_form" method=post onsubmit="return check_all_fields(this)" action="mostusefulresults.jsp">
		PID: <input type=text name="pid" length=3><br>
		Number of reviews: <input type=text name="num" length=3><br>
		<input type=submit value="Get feedback"/>
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>