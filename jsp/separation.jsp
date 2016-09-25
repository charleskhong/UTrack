<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Find degrees of separation</h1>
<script language="javascript">
function check_all_fields(form)
{
	var usr1_box = form.user1;
	var usr2_box = form.user2;
	if (usr1_box.value == "" || usr2_box.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
<%
	Connector con = new Connector();
	String users = User.getUsersHTML(con.stmt);
	out.println(users);
	con.stmt.close();
	con.closeConnection();
%>
<br>
	<form name="separation_form" method=post onsubmit="return check_all_fields(this)" action="getseparation.jsp">
		User 1 Login: <input type=text name="user1" length=40><br>
		User 2 Login: <input type=text name="user2" length=40><br>
		<input type=submit value="Find degrees of separation"/>	
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>