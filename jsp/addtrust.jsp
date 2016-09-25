<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	
</head>
<body>
<%
	String login = (String) session.getAttribute("login");
	String ologin = request.getParameter("ologin");
	String trusts = request.getParameter("trusts");

	Connector con = new Connector();
	boolean successful = User.addOrChangeTrust(login, ologin, trusts, con.stmt);
	String action = "";
	if (successful)
		action = "success";
	else
		action = "failed";

	con.stmt.close();
	con.closeConnection();
%>

	<script>
		var form = document.createElement('form');
		form.method = 'post';
		form.action = 'trust.jsp'
		var input = document.createElement('input');
		input.type = 'text';
		input.name = 'action';
		input.value = '<%=action%>';
		form.appendChild(input);
		form.submit();
	</script>

</body>
</html>