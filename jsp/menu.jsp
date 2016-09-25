<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<h1>Welcome to Charles's UTrack System</h1>
</head>

<body>
	
<%
	String numAdded = request.getParameter("affected");
	if (numAdded != null)
	{
%>
		<script>
			alert("<%=numAdded%> visits were affected");
		</script>
<%
	}
%>

<a href="addvisit.jsp">Add visit to cart</a><BR>
<a href="addpoi.jsp">(Admin Only) Add new POI</a><BR>
<a href="updpoi.jsp">(Admin Only) Update POI</a><BR>
<a href="favorite.jsp">Add POI as favorite</a><BR>
<a href="feedback.jsp">Add feedback to POI</a><BR>
<a href="rating.jsp">Rate feedback</a><BR>
<a href="trust.jsp">Declare another user as trusted/untrusted</a><BR>
<a href="browse.jsp">Browse for a POI</a><BR>
<a href="mostuseful.jsp">Most useful feedbacks for a POI</a><BR>
<a href="suggestions.jsp">Visiting Suggestions</a><BR>
<a href="separation.jsp">Degrees of Separation</a><BR>
<a href="statistics.jsp">Get POI statistics</a><BR>
<a href="awards.jsp">User awards</a><BR>
<a href="checkoutvisits.jsp">Add all visits from cart</a><br>
<a href="logout.jsp">Logout</a><BR>



</body>
</html>