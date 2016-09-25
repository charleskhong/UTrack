<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	
</head>
<body>
<%
	String id = request.getParameter("pid");
	String login = (String) session.getAttribute("login");
	int pid = 0;
	try
	{
		pid = Integer.parseInt(id);
	}
	catch (Exception e)
	{
%>
		<script>
			var form = document.createElement('form');
			form.method = 'post';
			form.action = 'favorite.jsp'
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
	Favorite.addFavorite(login, pid, con.stmt);
	con.stmt.close();
	con.closeConnection();
%>
	<script>
		var form = document.createElement('form');
		form.method = 'post';
		form.action = 'favorite.jsp'
		var input = document.createElement('input');
		input.type = 'text';
		input.name = 'action';
		input.value = 'success';
		form.appendChild(input);
		form.submit();
	</script>

</body>
</html>