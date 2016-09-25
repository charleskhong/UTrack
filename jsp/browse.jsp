<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Browse for a POI</h1>
<script language="javascript">
function check_all_fields(form)
{
	var price_box = form.price;
	var addr_box = form.address;
	var name_box = form.name;
	var cat_box = form.category;
	var low_box = document.getElementsByName("low")[0];
	var high_box = document.getElementsByName("high")[0];
	var city_box = document.getElementsByName("city")[0];
	var state_box = document.getElementsByName("state")[0];
	var kw_box = document.getElementsByName("keyword")[0];
	var cate_box = document.getElementsByName("cat")[0];

	if (!price_box.checked && !addr_box.checked && !name_box.checked && !cat_box.checked)
	{
		alert("Must select at least one method of searching");
		return false;
	}

	if (price_box.checked)
	{
		if (low_box.value == "" && high_box.value == "")
		{
			low_box.value = "0";
			high_box.value = "100";
		}

		var low = Number(low_box.value);
		var high = Number(high_box.value);
		if (low !== low || high !== high)
		{
			alert("Must enter numbers for low and high ranges");
			return false;
		}

		if (low < 0)
			low = 0;
		if (high <= low)
			high = 100;

		low_box.value = low.toPrecision(2);
		high_box.value = high.toPrecision(2);
	}

	if (addr_box.checked)
	{
		if (city_box.value == "" && state_box.value == "")
		{
			alert("Must enter a city and/or state to search");
			return false;
		}
	}

	if (name_box.checked)
	{
		if (kw_box.value == "")
		{
			alert("Must enter a name to search");
			return false;
		}
	}

	if (cat_box.checked)
	{
		if (cate_box.value == "")
		{
			alert("Must enter a category to search");
			return false;
		}
	}
	return true;

}

function handle_price_click(checkbox)
{
	var div = document.getElementById("priceline");
	if (checkbox.checked)
		div.style.display = "block";
	
	else
	{
		div.style.display = "none";
		document.getElementsByName("low")[0].value = "";
		document.getElementsByName("high")[0].value = "";
	}
}

function handle_address_click(checkbox)
{
	var div = document.getElementById("addrline");
	if (checkbox.checked)
		div.style.display = "block";
	
	else
	{
		div.style.display = "none";
		document.getElementsByName("city")[0].value = "";
		document.getElementsByName("state")[0].value = "";
	}
}

function handle_name_click(checkbox)
{
	var div = document.getElementById("nameline");
	if (checkbox.checked)
		div.style.display = "block";
	
	else
	{
		div.style.display = "none";
		document.getElementsByName("keyword")[0].value = "";
	}
}

function handle_cat_click(checkbox)
{
	var div = document.getElementById("catline");
	if (checkbox.checked)
		div.style.display = "block";
	
	else
	{
		div.style.display = "none";
		document.getElementsByName("cat")[0].value = "";
	}
}
</script>

</head>
<body>

	<form name="browse_form" method=post onsubmit="return check_all_fields(this)" action="browseresults.jsp">
		Search By: <br>
		Price Range: <input type="checkbox" name="price" onclick=handle_price_click(this);><br>
		Address: <input type="checkbox" name="address" onclick=handle_address_click(this);><br>
		By Name: <input type="checkbox" name="name" onclick=handle_name_click(this);><br>
		Category: <input type="checkbox" name="category" onclick=handle_cat_click(this)><br>
		<div id="priceline" style="display:none">
			Price Range: <input type=text name="low" length=5 placeholder="Default is 0">
			To <input type=text name="high" length=5 placeholder="Default is 100"><br>
		</div>
		<div id="addrline" style="display:none">
			City: <input type=text name="city" length=40><br>
			State: <input type=text name="state" length=2 placeholder="UT"><br>
		</div>
		<div id="nameline" style="display:none">
			Name: <input type=text name="keyword" length=40><br>
		</div>
		<div id="catline" style="display:none">
			Category: <input type=text name="cat" length=40><br>
		</div>
		<br>
		Sort By:
		<select name="sort_options">
			<option value="1">Price</option>
			<option value="2">Feedback Score</option>
			<option value="3">Trusted Feedback Score</option>
		</select><br>
		<input type=submit value="Search"/>

<br>
<a href="menu.jsp">Back To Menu</a>
</body>
</html>