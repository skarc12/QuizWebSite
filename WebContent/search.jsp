<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Servlets.DBHelper"%>
    <%@ page import="model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <%
HttpSession ses = request.getSession();
User user = (User)ses.getAttribute("user");
if(user == null){
	response.sendRedirect("index.html");
}else{

	String searchQuery = request.getParameter("query");
	User[] result = DBHelper.searchUsers(searchQuery);
%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
</head>
<body>
	<fieldSet>
	<p>mokled aq shemovida da daabrunaa es userebi</p>
	<%
		User [] users =(User[]) request.getAttribute("users");
		for(int i=0; i<users.length; i++){
			%>
			
			<p><a href="<&=users[i].getURL() %>"> <%= users[i].getFirstname() + " " + users[i].getLastname() %></a></p>
			
			
			<%
		}
	
	
	%>
	
	</fieldSet>

</body>
</html>