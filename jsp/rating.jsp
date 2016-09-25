<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Rate another user's opinion</h1>

<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.oid.value == "" || form_obj.usefulness.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}

	var oid = Number(form_obj.oid.value);
	var use = Number(form_obj.usefulness.value);
	if (pid !== pid || use !== use)
	{
		alert("Numbers only please");
		return false;
	}

	if (use > 2)
		use = 2;
	else if (use < 0)
		use = 0;

	form_obj.usefulness.value = use;

	return true;
}
</script>
</head>
<body>
	Which feedback would you like to rate<br><br>


<%
	String action = request.getParameter("action");
	if (action != null && action.equals("failed"))
	{
%>
		<script>
			alert("Failed to rate feedback");
		</script>
<%		
	}
	else if (action != null && action.equals("success"))
	{
%>
		<script>
			alert("Successfully rated feedback");
		</script>
<%
	}

	Connector con = new Connector();
	String opinions = Opinion.getOpinionsHTML(con.stmt);
	out.println(opinions);
	con.stmt.close();
	con.closeConnection();
%>

	<form name="rating_form" method=post onsubmit="return check_all_fields(this)" action="addrating.jsp">
		OID: <input type=text name="oid" length=3><br>
		Usefulness: <input type=text name="usefulness" length=3 placeholder="0, 1 or 2"><br>
		<input type=submit value="Rate feedback"/>
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>

</body>
</html>