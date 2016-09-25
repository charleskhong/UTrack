<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Leave feedback on a place</h1>

<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.pid.value == "" || form_obj.score.value == "" || form_obj.date.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}

	var pid = Number(form_obj.pid.value);
	var score = parseInt(form_obj.score.value);
	if (pid !== pid || score !== score)
	{
		alert("Numbers only please");
		return false;
	}

	var date_re = /^[01]?[0-9]\/[0-3]?[0-9]\/[12][90][0-9][0-9]$/;
	if (!date_re.test(form_obj.date.value))
	{
		alert("Date should be in the format: mm/dd/yyyy");
		return false;
	}

	if (score > 10)
		score = 10;
	else if (score < 0)
		score = 0;

	form_obj.score.value = score;

	return true;
}
</script>
</head>
<body>
	Which place would you like to leave feedback for<br><br>

<%
	String action = request.getParameter("action");
	if (action != null && action.equals("failed"))
	{
%>
		<script>
			alert("Failed to leave feedback for POI");
		</script>
<%		
	}
	else if (action != null && action.equals("success"))
	{
%>
		<script>
			alert("Successfully left feedback for POI");
		</script>
<%
	}

	Connector con = new Connector();
	String places = PlaceOfInterest.getPlacesHTML(con.stmt, con.con);
	out.println(places);
	con.stmt.close();
	con.closeConnection();
%>

	<form name="opinion_form" method=post onsubmit="return check_all_fields(this)" action="addfeedback.jsp">
		PID: <input type=text name="pid" length=3><br>
		Score: <input type=text name="score" length=3 placeholder="(0-10)"><br>
		Description: <input type=text name="description" length=40 placeholder="optional"><br>
		Date: <input type=text name="date" length=40 placeholder="mm/dd/yyyy"><br>
		<input type=submit value="Leave feedback"/>

	</form>

<br>
<a href="menu.jsp">Back To Menu</a>

</body>
</html>