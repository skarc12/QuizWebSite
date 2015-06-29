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
	Message[] messages = DBHelper.getUserUnreadMessages(user.getUserID());
	Quiz[] popQuizes = DBHelper.getPopularQuizes();
	Quiz[] recentCreatedQuizes = DBHelper.getRecentlyCreatedQuizes(user);
	Quiz[] recentQuizActivities = DBHelper.getRecentQuizActivities(user);
	Quiz[] userPlayedQuizes = DBHelper.getUserPlayedQuizes(user);
%>
	<head>
		<title>Quiz Website</title>
		<link rel="stylesheet" type="text/css" href="./css/styleHome.css"/>
		<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	</head>
	<body>
		
		<a href="creatQuiz.jsp"><img class = "quiz" src="./img/blaa.png" title="Create New Quiz"></a>
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
				<p><a href="home.jsp"><%=user.getFirstname() %> <%=user.getLastname() %></a></p>
				<a href="home.jsp"><img class = "user" src="./img/user.jpg"></a>
				
			</div>
		</div>
		<div id="notificationContainer"></div>
		<div id="messageContainer" style="display: none">
			<%for(int i=0; i<messages.length; i++){ %>
			<div>
				<input class="msgID" type="hidden" value="<%=messages[i].getId() %>">
				<h3><a href="<%=messages[i].getSender().getURL() %>"><%=messages[i].getSender().getFirstname() + " " +messages[i].getSender().getLastname() %></a></h3>
				<h6><%=messages[i].getMsg() %></h6>
			</div>
			<%} %>
		</div>
		<div id="challengeContainer" style="display: none">
		
		</div>
		<div id="friendRequestContainer" style="display: none">
			
		</div>
		
		<div class="notifications">
			<div style="display: inline-block">
				<h3 onclick="showMessages()">Unread Messages  <%= messages.length %></h3>
			</div>
			<div style="display: inline-block">
				<h3 onclick="showChallenges()">Unread Challenges  <%= messages.length %></h3>
			</div>
			<div style="display: inline-block">
				<h3 onclick="showFriendRequests()">Unread Friend Requests  <%= messages.length %></h3>
			</div>
		
		</div>
			<hr>
			<div class="Quizes">
			<fieldset style="display:inline-block">
			<legend>Most Popular Quizes:</legend>
			<%if(popQuizes != null) for(int i=0; i<popQuizes.length; i++){ %>
				<div><a href="quizPage.jsp?quizID=<%=popQuizes[i].getID() %>"><%=popQuizes[i].getQuizName()%></a></div>
			<%} %>
			
			</fieldset>
			<fieldset style="display:inline-block">
			<legend>Recent Created Quizes:</legend>
			<%if(recentCreatedQuizes != null) for(int i=0; i<recentCreatedQuizes.length; i++){ %>
				<div><%=recentCreatedQuizes[i].getQuizName() %></div>
			<%} %>
			</fieldset>
			<fieldset style="display:inline-block">
			<legend>Recent Quiz Activities:</legend>
			<%if(recentQuizActivities != null) for(int i=0; i<recentQuizActivities.length; i++){ %>
				<div><%=recentQuizActivities[i].getQuizName() %></div>
			<%} %>
			
			</fieldset>
			<fieldset style="display:inline-block">
			<legend>User Played Quizes:</legend>
			<%if(userPlayedQuizes != null) for(int i=0; i<userPlayedQuizes.length; i++){ %>
				<div><%=userPlayedQuizes[i].getQuizName() %></div>
			<%} %>
			
			</fieldset>
			
			</div>
	</body>
<%} %>

<script type="text/javascript">
function showMessages(){
	var arr = [];
	$.each($(".msgID"), function (i, e){
		arr.push($(e).val());
	});
	var data = {
			action: "messages",
			ids: arr
	};
	$.ajax({
		url: "clearNotifications",
		type: "post",
		data: JSON.stringify(data)
	});
	$("#notificationContainer").html($("#messageContainer").html());
}
function showChallenges(){
	$("#notificationContainer").html($("#challengeContainer").html());$.ajax({
		url: "clearNotifications",
		type: "post",
		data: "challenges"
	});
}
function showFriendRequests(){
	$("#notificationContainer").html($("#friendRequestContainer").html());$.ajax({
		url: "clearNotifications",
		type: "post",
		data: "friendRequests"
	});
}
</script>
</html>