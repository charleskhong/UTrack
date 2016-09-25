<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>User awards</h1>
<script language="javascript">
function check_all_fields(form)
{
	if (form.num.value == "")
	{
		alert("Fields should be nonempty");
		return false;
	}

	var num = Number(form.num.value);
	if (num !== num)
	{
		alert("Please enter a number for the number of records");
		return false;
	}

	return true;
}
</script>	
</head>
<body>
	<form name="awards_form" method=post onsubmit="return check_all_fields(this)" action="awardsresults.jsp">
		Type of Award: 
		<select name="award_options">
			<option value="1">Most trusted users</option>
			<option value="2">Most useful users</option>
		</select><br>
		Number of records: <input type="text" name="num" length=3><br>
		<input type=submit value="Get awards"/>
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>