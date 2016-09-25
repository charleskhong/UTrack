<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>POI Statistics</h1>
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
	<form name="stats_form" method=post onsubmit="return check_all_fields(this)" action="statisticsresults.jsp">
		Type of Statistic: 
		<select name="stats_options">
			<option value="1">Most popular POIs for each category</option>
			<option value="2">Most expensive POIs for each category</option>
			<option value="3">Highest rated POIs for each category</option>
		</select><br>
		Number of records: <input type=text name="num" length=3><br>
		<input type=submit value="Get statistics"/>
	</form>

<br>
<a href="menu.jsp">Back To Menu</a>		
</body>
</html>