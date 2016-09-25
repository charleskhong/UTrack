<%@ page language="java" import="cs5530.*" %>
<%@ page language="java" import="java.util.*" %>
<html>
<head>
	
</head>
<body>
<%
	String pname = request.getParameter("name");
	String url = request.getParameter("url");
	String year = request.getParameter("year");
	String hours = request.getParameter("hours");
	String p = request.getParameter("price");
	double price = 0;
	try
	{
		price = Double.parseDouble(p);
	}
	catch (Exception e)
	{
%>
		<script>
			var form = document.createElement('form');
			form.method = 'post';
			form.action = 'addpoi.jsp'
			var input = document.createElement('input');
			input.type = 'text';
			input.name = 'action';
			input.value = 'failed';
			form.appendChild(input);
			form.submit();
		</script>
<%
	}
	
	String keywords = request.getParameter("keywords");
	String addr1 = request.getParameter("addr1");
	String addr2 = request.getParameter("addr2");
	String city = request.getParameter("city");
	String state = request.getParameter("state");
	String zip = request.getParameter("zip");
	String phone = request.getParameter("phone");
	String category = request.getParameter("category");
	String[] kws = keywords.split("\\s*,\\s*");
	List<String> kwords = new ArrayList<String>();
	for (String k : kws)
		kwords.add(k);
	
	Connector con = new Connector();
	PlaceOfInterest.addPOI(pname, url, year, hours, price, category, kwords, addr1, addr2, city, state, zip, phone, con.stmt);
	con.stmt.close();
	con.closeConnection();
%>
	<script>
		var form = document.createElement('form');
		form.method = 'post';
		form.action = 'addpoi.jsp'
		var input = document.createElement('input');
		input.type = 'text';
		input.name = 'action';
		input.value = 'success';
		form.appendChild(input);
		form.submit();
	</script>

</body>
</html>