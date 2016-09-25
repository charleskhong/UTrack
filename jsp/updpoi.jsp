<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Update Existing POI</h1>

<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.pid.valie == "")
	{
		alert("PID should be nonempty");
		return false;
	}

	var pid = Number(form_obj.pid.value);
	if (pid !== pid)
	{
		alert("Pid should be a number");
		return false;
	}

	if (form_obj.price.value != "")
	{
		var price = Number(form_obj.price.value);
		if (price !== price)
		{
			alert("Price should be a number");
			return false;
		}
		form_obj.price.value = form_obj.price.value.toPrecision(2);
	}

	return true;
}
</script>
</head>
<body>
<% 
	String login = (String) session.getAttribute("login");
	String action = request.getParameter("action");
	if (action != null && action.equals("failed"))
	{
%>
		<script>
			alert("Failed to update POI");
		</script>
<%
	}
	else if (action != null && action.equals("success"))
	{
%>
		<script>
			alert("Successfully updated POI");
		</script>
<%
	}

	if (!login.equals("admin"))
	{
%>
		<script>
			alert("Only the admin is allowed here!");
			window.location ="menu.jsp";
		</script>
<%
	}
%>
	Which place do you want to update?<br><br>

<%
	Connector con = new Connector();
	String places = PlaceOfInterest.getPlacesToEditHTML(con.stmt, con.con);
	out.println(places);
	con.stmt.close();
	con.closeConnection();
%>
<br> Leave fields blank if you do not wish to change it (PID Required). <br>
	Enter new information into the form to update those corresponding values. <br>
	
	<form name="updpoi_form" method=post onsubmit="return check_all_fields(this)" action="updatepoi.jsp">
		Pid: <input type=text name="pid" length=3><br>
		Place Name: <input type=text name="name" length=40><BR>
		URL: <input type=text name="url" length=40><BR>
		Year Established: <input type=text name="year" length=4 placeholder="1111"><BR>
		Hours of Operation: <input type=text name="hours" length=40><BR>
		Average Price: <input type=text name="price" length=10 placeholder="(12.34)"><BR>
		Remove Keywords: <input type=text name="old_keywords" length=100 placeholder="restaurant, fast food, etc. (optional)"><BR>
		New Keywords: <input type=text name="new_keywords" length=100><br>
		Address Line 1: <input type=text name="addr1" length=40><BR>
		Address Line 2: <input type=text name="addr2" length=40 placeholder="optional"><BR>
		City: <input type=text name="city" length=40><BR>
		State: <input type=text name="state" length=2 placeholder="UT"><BR>
		Zip: <input type=text name="zip" length=5 placeholder="11111"><BR>
		Phone: <input type=text name="phone" length=10 placeholder="1234567890"><BR>
		Category: <input type=text name="category" length=40><BR>
		<input type=submit value="Update POI"/>
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>

</body>
</html>