<%@page import="model.Quiz.QuizHandle"%>
<%@ page import="model.*"%>
<%@ page import="Servlets.*"%>

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
	String username = request.getParameter("username");
	User other = DBHelper.findUser(username);
%>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><%=other==null?"User" : other.getUsername() %></title>
		<link rel="stylesheet" type="text/css" href="./css/styleHome.css"/>
		<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	</head>
<body>
<%if(other == null){%>
	<h1>No Such User: '<%=username %>'</h1>
<% }else{ %>
	<a href="creatQuiz.jsp"><img class = "quiz" src="./img/blaa.png" title="Create New Quiz"></a>
			<hr>
	
		<div class = "headerMenu" size = "60">
			<div class = "search-box">
<script type="text/javascript">
function search(){
	window.location.replace("search.jsp?query="+$("#searchField").val());
}
</script>
				<form action="search.jsp" method = "GET" id = "search">
					<input id="searchField" type = "text" name="q" size="60" placeholder="Search ...">
				</form>
				<a onclick="search()"><img class = "link" src="./img/2.png"></a>
				<p class="out"><a href="SignOutServlet">Sing Out</a></p>
				<p><a href="home.jsp"><%=user.getFirstname() %> <%=user.getLastname() %></a></p>
				<a href="home.jsp"><img class = "user" src="./img/user.jpg"></a>
				
			</div>
		</div>
		<div>
			<img class="hello" src="./img/hello.gif">
			<img class = "mini" src="./img/mini.png">
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<h1><%=other.getFirstname() + "  " + other.getLastname() %></h1>
			<h3 id="username"><%=other.getUsername() %></h3>
			<button onclick="toggleFriend(this)"><%=user.isFriend(other)?"Unfriend" : "Add Friend"%></button>
		</div>
		<hr>
		<div style="display:inline-block">
			<fieldset>
				<h3>write Message: </h3>
				<textarea id="message" rows="3" cols="30"></textarea>
				<button onclick="SendMsg()">Send</button>
			</fieldset>
		</div>	
		<div style="display:inline-block">
			<fieldset>
				<h3>Send Challenge:</h3>
				<select id="challenge"><% 
				QuizHandle[] quizes = DBHelper.getAllQuizes(user);
				for(int i=0; i<quizes.length; i++){
				%>
					<option value="<%=quizes[i].ID %>"><%=quizes[i].name %></option>
					
				
				</select>
				<input class="ownScore" type="hidden" value="<%=quizes[i].score%>"/>
				<%} %>
				<textarea id="challengeText" rows="3" cols="30"></textarea>
				<button onclick="SendChalenge()">Send</button>
			</fieldset>
		</div>
</body>
<script type="text/javascript">
function toggleFriend(e){
	var data = {};
	data.action = $(e).html();
	data.username = $("#username").html();
	console.log(data);
	$.ajax({
		url: "userManager",
		type: "post",
		data: JSON.stringify(data),
		success: function(content){
			$(e).html(content);
		}
	});
}

function SendMsg(){
	var request = {}
	request.action = "sendMessage";
	request.msg = $("#message").val();
	request.username = $("#username").html();
	$.ajax({
		url: "userManager",
		type: "post",
		data: JSON.stringify(request),
		success: function(content){
			$("#message").val("");
		}
	});
}

function SendChalenge(){
	var request = {};
	request.action = "sendChallenge";
	request.username =$("#username").html();
	request.quizID = $("#challenge").val();
	request.ownScore = $("#challenge").find(".ownScore").val();
	request.message = $("#challengeText").val();
	$.ajax({
		url: "userManager",
		type: "post",
		data: JSON.stringify(request),
		success: function(content){
			alert("Challenge Sent");
		}
	});
}

</script>

<%} }%>

</html>