<%@ page language="java" import="cs5530.*" %>
<%@ page language="java" import="java.util.*" %>
<html>
<head>
</head>
<body>
<%
	String action = request.getParameter("cart");
	ArrayList<Visit> visits = (ArrayList<Visit>) session.getAttribute("visits");
	int affected = 0;
	if (visits != null)
	{
		affected = visits.size();
		Connector con = new Connector();
		if (action.equals("cart_addall"))
			for (Visit v : visits)
				Visit.addVisit(v.login, v.pid, v.cost, v.size, v.date, con.stmt);
				
		con.stmt.close();
		con.closeConnection();
	}
	session.setAttribute("visits", new ArrayList<Visit>());
%>

<script>
	var form = document.createElement('form');
	form.method = 'post';
	form.action = 'menu.jsp'
	var input = document.createElement('input');
	input.type = 'text';
	input.name = 'affected';
	input.value = '<%=affected %>';
	form.appendChild(input);
	form.submit();
</script>

</body>
</html>