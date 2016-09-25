<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Add New POI</h1>

<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.name.value == "" || form_obj.url.value == "" || form_obj.year.value == "" || form_obj.hours.value == "" || form_obj.price.value == "" || form_obj.addr1.value == "" || form_obj.city.value == "" || form_obj.state.value == "" || form_obj.zip.value == "" || form_obj.phone.value == "" || form_obj.category.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}

	var price = Number(form_obj.price.value);
	if (price !== price)
	{
		alert("Price should be a number");
		return false;
	}

	form_obj.price.value = form_obj.price.value.toPrecision(2);

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
			alert("Failed to add POI");
		</script>
<%
	}
	else if (action != null && action.equals("success"))
	{
%>
		<script>
			alert("Successfully added POI");
		</script>
<%
	}

	if (!login.equals("admin"))
	{
%>
		<script>
			alert("Only the admin is allowed here!");
			window.location = "menu.jsp";
		</script>
<%		
	}
%>

	<form name="poi_form" method=post onsubmit="return check_all_fields(this)" action="insertpoi.jsp">
		Place Name: <input type=text name="name" length=40><BR>
		URL: <input type=text name="url" length=40><BR>
		Year Established: <input type=text name="year" length=4 placeholder="1111"><BR>
		Hours of Operation: <input type=text name="hours" length=40><BR>
		Average Price: <input type=text name="price" length=10><BR>
		Keywords: <input type=text name="keywords" length=100 placeholder="restaurant, fast food, etc. (optional)"><BR>
		Address Line 1: <input type=text name="addr1" length=40><BR>
		Address Line 2: <input type=text name="addr2" length=40 placeholder="optional"><BR>
		City: <input type=text name="city" length=40><BR>
		State: <input type=text name="state" length=2 placeholder="UT"><BR>
		Zip: <input type=text name="zip" length=5 placeholder="11111"><BR>
		Phone: <input type=text name="phone" length=10 placeholder="1234567890"><BR>
		Category: <input type=text name="category" length=40><BR>
		<input type=submit value="Add POI"/>
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>