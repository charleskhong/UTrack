<%@ page language="java" import="cs5530.*" %>
<%@ page language="java" import="java.util.*" %>
<html>
<head>
	
</head>
<body>
<%
	String login = (String) session.getAttribute("login");
	String p = request.getParameter("pid");
	String c = request.getParameter("cost");
	String s = request.getParameter("size");
	String date = request.getParameter("date");
	ArrayList<Visit> visits = null;
	visits = (ArrayList<Visit>) session.getAttribute("visits");
	if (visits == null)
	{
		visits = new ArrayList<Visit>();
		session.setAttribute("visits", visits);
	}
	Visit v = new Visit();
	int pid = -1;
	double cost = -1;
	int size = -1;
	try
	{
		pid = Integer.parseInt(p);
		cost = Double.parseDouble(c);
		size = Integer.parseInt(s);
	}
	catch (Exception e)
	{
%>
		<script>
			var form = document.createElement("form");
			form.method = "post";
			form.action = "addvisit.jsp";
			var input = document.createElement("input");
			input.type = "text";
			input.name + "failed";
			input.value = "failed";
			form.appendChild(input);
			form.submit();
		</script>

<%
	 }
	 v.pid = pid;
	 v.cost = cost;
	 v.size = size;
	 v.date = date;
	 v.login = login;
	 visits.add(v);
	 session.setAttribute("visits", visits);
%>
<script>
	window.location = "addvisit.jsp";
</script>



</body>
</html>