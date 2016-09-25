<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Please fill out the following form.</h1>
<script language="javascript">
function check_all_fields(form_obj)
{
	if (form_obj.username.value == "" || form_obj.name.value == "" || form_obj.password.value == "" || form_obj.addr1.value == "" || form_obj.city.value == "" || form_obj.state.value == "" || form_obj.zip.value == "" || form_obj.phone.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}
	return true;
}

</script>
</head>

<body>

	<form name ="register_form" method=post onsubmit="return check_all_fields(this)" action="registration.jsp">
		Username: <input type=text name="username" length=40><BR>
		Name: <input type=text name="name" length=40><BR>
		Password: <input type=password name="password" length=40><BR>
		Address Line 1: <input type=text name="addr1" length=40><BR>
		Address Line 2: <input type=text name="addr2" length=40 placeholder="optional"><BR>
		City: <input type=text name="city" length=40><BR>
		State: <input type=text name="state" length=2 placeholder="(UT)"><BR>
		Zip: <input type=text name="zip" length=5 placeholder="(11111)"><BR>
		Phone: <input type=text name="phone" length=10 placeholder="(1234567890)"><BR>
		<input type=submit value="Register"/>
	</form>

<br>
<a href="login.jsp">Back</a>
</body>
</html>