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
	Challenge[] challenges = DBHelper.getUnseenChallenges(user);
	FriendRequest[] friendRequest = DBHelper.getUnseenFriendRequest(user);
	Quiz[] popQuizes = DBHelper.getPopularQuizes();
	Quiz[] recentCreatedQuizes = DBHelper.getRecentlyCreatedQuizes(user);
	Quiz[] recentQuizActivities = DBHelper.getRecentQuizActivities(user);
	Quiz[] userPlayedQuizes = DBHelper.getUserPlayedQuizes(user);
	//User[] search = DBHelper.searchUsers("");
%>
	<head>
		<title>Quiz Website</title>
		<link rel="stylesheet" type="text/css" href="./css/styleHome.css"/>
		<link rel="stylesheet" type="text/css" href="./css/animate.css"/>
<!-- 		<link rel="stylesheet" type="text/css" href="./css/bootstrap/css/bootstrap.min.css"/>
 -->		<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
		
	</head>
	<body>
		
		<a href="creatQuiz.jsp"><img class = "quiz" src="./img/blaa.png" title="Create New Quiz"></a>
			<hr>
		<div class = "headerMenu" size = "60">
			<div class = "search-box">

				<form action="search" method = "GET" id = "search">
					<input id="searchForm" type = "text" name="q" size="60" placeholder="Search ...">
				</form>
				<a href="https://instagram.com/"><img class = "link" src="./img/2.png"></a>
				<p class="out"><a href="SignOutServlet">Sing Out</a></p>
				<p class = "boloshi"><a href="home.jsp"><%=user.getFirstname() %> <%=user.getLastname() %></a></p>
				<a href="home.jsp"><img class = "user" src="./img/user.jpg"></a>
				
			</div>
		</div>
		<div id="notificationContainer"></div>
		<div id="messageContainer" style="display: none">
			<%for(int i=0; i<messages.length; i++){ %>
			<div>
				<input class="msgID" type="hidden" value="<%=messages[i].getId() %>">
				<h3>From: <a href="<%=messages[i].getSender().getURL() %>"><%=messages[i].getSender().getFirstname() + " " +messages[i].getSender().getLastname() %></a></h3>
				<h4 color= blue>Msg: <%=messages[i].getMsg() %></h4>
			</div>
			<%} %>
		</div>
		<div id="challengeContainer" style="display: none">
			<%for(int i=0; i<challenges.length; i++){ %>
			<div>
				<input class="msgIDChallenge" type="hidden" value="<%=challenges[i].getId() %>">
				<h3> From: <a href="<%=challenges[i].getSender().getURL()%>"><%=challenges[i].getSender().getFirstname() + " " +challenges[i].getSender().getLastname() %></a></h3>
				Quiz: <a href="quizPage.jsp?quizID=<%=challenges[i].getQuizid() %>">get Quiz</a>
				<h4 color=blue>Msg: <%=challenges[i].getMsg() %></h4>
			</div>
			<%} %>
		</div>
		<div id="friendRequestContainer" style="display: none">
			<%for(int i=0; i<friendRequest.length; i++){ %>
			<div>
				<input class="msgIDRequest" type="hidden" value="<%=friendRequest[i].getId() %>">
				<h3><a class = "bolo" href="<%=friendRequest[i].getSender().getURL() %>"><%=friendRequest[i].getSender().getFirstname() + " " +friendRequest[i].getSender().getLastname() %></a> added you into his/her friends.</h3>
				<h4 color =red>If you don't want to friend this user you can go to his/her profile and unfriend him/her.</h4>
			</div>
			<%} %>
		</div>
		
		<div class="notifications">
			<div style="display: inline-block">
				<h3 onclick="showMessages()">Messages  <%= messages.length %></h3>
			</div>
			<div style="display: inline-block">
				<h3 onclick="showChallenges()">Challenges  <%= challenges.length %></h3>
			</div>
			<div style="display: inline-block">
				<h3 onclick="showFriendRequests()">Friend Requests  <%= friendRequest.length %></h3>
			</div>
		
		</div>
			<hr>
			<div class="Quizes">
			<fieldset style="display:inline-block">
			<legend>Popular Quizes:</legend>
			<%if(popQuizes != null) for(int i=0; i<popQuizes.length; i++){ %>
				<div><a href="quizPage.jsp?quizID=<%=popQuizes[i].getID() %>"><%=popQuizes[i].getQuizName()%></a></div>
			<%} %>
			
			</fieldset>
			<fieldset style="display:inline-block">
			<legend>Recent Created Quizes:</legend>
			<%if(recentCreatedQuizes != null) for(int i=0; i<recentCreatedQuizes.length; i++){ %>
				<div><a href="quizPage.jsp?quizID=<%=recentCreatedQuizes[i].getID() %>"><%=recentCreatedQuizes[i].getQuizName() %></a></div>
			<%} %>
			</fieldset>
			<fieldset style="display:inline-block">
			<legend>Recent Quiz Activities:</legend>
			<%if(recentQuizActivities != null) for(int i=0; i<recentQuizActivities.length; i++){ %>
				<div><a href="quizPage.jsp?quizID=<%=recentQuizActivities[i].getID() %>"><%=recentQuizActivities[i].getQuizName() %></a></div>
			<%} %>
			
			</fieldset>
			<fieldset style="display:inline-block">
			<legend>User Played Quizes:</legend>
			<%if(userPlayedQuizes != null) for(int i=0; i<userPlayedQuizes.length; i++){ %>
				<div><a href="quizPage.jsp?quizID=<%=userPlayedQuizes[i].getID() %>"><%=userPlayedQuizes[i].getQuizName() %></a></div>
			<%} %>
			
			</fieldset>
			<fieldset style="display:inline-block">
			<legend>Friends:</legend>
			<%if(user.getFriends() != null) for(int i=0; i<user.getFriends().size(); i++){ %>
				<div><a href="<%=user.getFriends().get(i).getURL()%>"><%=user.getFriends().get(i).toString() %></a></div>
			<%} %>
			
			</fieldset>
			</div>
			<img id="minions" class = "animated bounceInUp" src="./img/salo.jpg">
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
	var arr = [];
	$.each($(".msgIDChallenge"), function (i, e){
		arr.push($(e).val());
	});
	var data={
			action: "challenges",
			ids :arr
	};
	 $.ajax({
		url:"clearNotifications",
		type:"post",
		data: JSON.stringify(data)
	}); 
	$("#notificationContainer").html($("#challengeContainer").html());
}
function showFriendRequests(){
	var arr = [];
	$.each($(".msgIDRequest"), function (i, e){
		arr.push($(e).val());
	});
	var data={
			action: "friendRequests",
			ids :arr
	};
	$("#notificationContainer").html($("#friendRequestContainer").html());$.ajax({
		url: "clearNotifications",
		type: "post",
		data: JSON.stringify(data)
	});
}
</script>
</html>