<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Add visits to your cart</h1>
	
<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.pid.value == "" || form_obj.cost.value == "" || form_obj.size.value == "" || form_obj.date.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}
	var pid = Number(form_obj.pid.value);
	var cost = Number(form_obj.cost.value);
	var size = Number(form_obj.size.value);
	if (pid !== pid || cost !== cost || size !== size)
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

	form_obj.cost.value = form_obj.cost.value.toPrecision(2);

	return true;
}
</script>

</head>
<body>
	Which place did you visit?<br><br>

<%
	String addFailed = request.getParameter("failed");
	if (addFailed != null)
	{
%>
		<script>
			alert("Previous visit that you tried to add failed.");
		</script>
<%
	}

	Connector con = new Connector();
	String places = PlaceOfInterest.getPlacesHTML(con.stmt, con.con);
	out.println(places);
	con.stmt.close();
	con.closeConnection();
%>

	<form name="visit_form" method=post onsubmit="return check_all_fields(this)" action="visittocart.jsp">
		PID: <input type=text name="pid" length=3><br>
		Cost: <input type=text name="cost" length=10 placeholder="(11.11)"><br>
		Party Size: <input type=text name="size" length=3><br>
		Date: <input type=text name="date" length=10 placeholder="mm/dd/yyyy"><br>
		<input type=submit value="Add Visit"/>
		
	</form>
<br>
<a href="checkoutvisits.jsp">Checkout Visits</a>
<br>
<a href="menu.jsp">Back To Menu</a>

</body>
</html>