<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	
</head>
<body>
<%
	String login = (String) session.getAttribute("login");
	String id = request.getParameter("pid");
	String sc = request.getParameter("score");
	int pid = 0;
	int score = 0;
	try
	{
		pid = Integer.parseInt(id);
		score = Integer.parseInt(sc);
	}
	catch (Exception e)
	{
%>
		<script>
			var form = document.createElement('form');
			form.method = 'post';
			form.action = 'feedback.jsp'
			var input = document.createElement('input');
			input.type = 'text';
			input.name = 'action';
			input.value = 'failed';
			form.appendChild(input);
			form.submit();
		</script>
<%
	}

	String description = request.getParameter("description");
	String date = request.getParameter("date");

	Connector con = new Connector();
	Opinion.addOpinion(score, description, date, pid, login, con.stmt);
	con.stmt.close();
	con.closeConnection();
%>
	<script>
		var form = document.createElement('form');
		form.method = 'post';
		form.action = 'feedback.jsp'
		var input = document.createElement('input');
		input.type = 'text';
		input.name = 'action';
		input.value = 'success';
		form.appendChild(input);
		form.submit();
	</script>

</body>
</html>