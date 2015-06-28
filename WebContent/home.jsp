<%@ page import="model.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
HttpSession ses = request.getSession();

User user = (User) ses.getAttribute("user");
if(user == null)
	response.sendRedirect("index.html");
else{
%>
	<head>
		<title>Quiz Website</title>
		<link rel="stylesheet" type="text/css" href="./css/styleHome.css"/>
	</head>
	<body>
		
			<img class = "quiz" src="./img/blaa.png">
				<hr>
			<!--<img class="hello" src="./img/gipi.gif">
			<img class = "mini" src="./img/mini.png">-->
			<div class = "headerMenu" size = "60">
					<div class = "search-box">

						<form action="search.php" method = "GET" id = "search">
							<input type = "text" name="q" size="60" placeholder="Search ...">
						</form>
						<a href="https://instagram.com/"><img class = "link" src="./img/2.png"></a>
						<p class="out"><a href="SignOutServlet">Sing Out</a></p>
						<p><a href="userPage.html"><%=user.getFirstname() %> <%=user.getLastname() %></a></p>
						<a href="userPage.html"><img class = "user" src="./img/user.jpg"></a>
						
					</div>
				</div>
			</div>

			<h1><a href ="createQuiz.jsp">Create Quize</a></h1>
		</table>
	</body>
<%} %>
</html>