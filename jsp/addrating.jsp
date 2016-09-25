<%@ page language="java" import="cs5530.*" %>
<html>
<head>

</head>
<body>
<%
	String login = (String) session.getAttribute("login");
	String id = request.getParameter("oid");
	String use = request.getParameter("usefulness");
	int oid = 0, usefulness = 0;
	try
	{
		oid = Integer.parseInt(id);
		usefulness = Integer.parseInt(use);
	}
	catch (Exception e)
	{
%>
		<script>
			var form = document.createElement('form');
			form.method = 'post';
			form.action = 'rating.jsp'
			var input = document.createElement('input');
			input.type = 'text';
			input.name = 'action';
			input.value = 'failed';
			form.appendChild(input);
			form.submit();
		</script>
<%
	}

	Connector con = new Connector();
	boolean succesful = OpinionRating.addRating(oid, login, usefulness, con.stmt);
	String action = "";
	if (succesful)
		action = "success";
	else
		action = "failed";

	con.stmt.close();
	con.closeConnection();
%>

	<script>
		var form = document.createElement('form');
		form.method = 'post';
		form.action = 'rating.jsp'
		var input = document.createElement('input');
		input.type = 'text';
		input.name = 'action';
		input.value = '<%=action%>';
		form.appendChild(input);
		form.submit();
	</script>
</body>
</html>