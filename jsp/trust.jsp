<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Trust/Untrust another user</h1>

<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.ologin.value == "" || form_obj.trusts.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}

	var trusts = form_obj.trusts.value;
	trusts = trusts.toLowerCase();

	if (trusts != "y" && trusts != "n")
	{
		alert("Enter either a 'y' or an 'n' (case insensitive)");
		return false;
	}

	form_obj.trusts.value = trusts;

	return true;
}
</script>
</head>
<body>
	Which user do you want to trust/untrust<br><br>

<%
	String action = request.getParameter("action");
	if (action != null && action.equals("failed"))
	{
%>
		<script>
			alert("Failed to trust/untrust user");
		</script>
<%		
	}
	else if (action != null && action.equals("success"))
	{
%>
		<script>
			alert("Successfully trusted/untrusted user");
		</script>
<%
	}

	Connector con = new Connector();
	String users = User.getUsersHTML(con.stmt);
	out.println(users);
	con.stmt.close();
	con.closeConnection();
%>

	<form name="trust_form" method=post onsubmit="return check_all_fields(this)" action="addtrust.jsp">
		Other User's Login: <input type=text name="ologin" length=40><br>
		Yes or No (Y/N): <input type=text name="trusts" length=5 placeholder="(Y/N)"><br>
		<input type=submit value="Trust/Untrust User"/>
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>