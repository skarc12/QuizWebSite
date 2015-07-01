<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.DBHelper"%>
<%@ page import="model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
</head>
<body class = "special" >
	<a href="creatQuiz.jsp"><img class="quiz" src="./img/blaa.png"
		title="Create New Quiz"></a>
	<hr>
	<!--<img class="hello" src="./img/gipi.gif">
		<img class = "mini" src="./img/mini.png">-->
	<div class="headerMenu" size="60">
		<div class="search-box">

			<form action="search" method="GET" id="search">
				<input id="searchForm" type="text" name="q" size="60"
					placeholder="Search ...">
			</form>
			
		</div>
	</div>
	<fieldSet>
		<legend>Result:</legend>
		<p>Find User</p>

		<%
			User[] users = (User[]) request.getAttribute("users");
			if (users.length == 0) {
		%>
		<p>there is no User</p>
		<%
			}
			for (int i = 0; i < users.length; i++) {
		%>

		<p>
			<a href="<%=users[i].getURL()%>"> <%=users[i].getFirstname() + " "
						+ users[i].getLastname()%></a>
		</p>
		<%
			}
		%>
		<h5>
			<a href="home.jsp">RETURN HOMEPAGE:</a>
		</h5>
	</fieldSet>

</body>
</html>