delimiter @

drop procedure if exists addUser@
create procedure addUser(firstname varchar(100),lastname varchar(100),loginname varchar(100), mail varchar(100),userPassword varchar(100))
begin

	insert into users(first_name,last_name, username, email, password)
	values(firstname, lastname,loginname,mail,userPassword);
	
end@

drop procedure if exists getUser@
create procedure getUser(userID int)
begin
	select * from users where ID = userID;
end@

create procedure getPopularQuizes()
begin
	select b.* from take_quize as a,quizes as b
	where a.quizID = b.ID
	group by a.quizID
	order by count(a.quizID) desc
	limit 2;
end@

create procedure getQuestionIDs(ID int)
begin
	select * from questions
	where quizID = ID;
end@

drop procedure if exists getQuestion@
create procedure getQuestion(questID int, categoryID int)
begin
	if categoryID=1 then 
		select * from fill_the_gaps
		where questionID = questID;
	elseif categoryID=2 then 
		select * from multiple_choice
		where questionID = questID;
	elseif categoryID=3 then 
		select * from picture_quiz
		where questionID = questID;
	elseif categoryID=4 then 
		select * from question_answer
		where questionID = questID;
	end if;
end@

drop procedure if exists getUnreadMessages@
create procedure getUnreadMessages(userID int)
begin
	select ID, fromID, toID, msg, seen from messages
	where toID = userID
	and seen = 0;

end@

drop procedure if exists getUserIDByUsername@
create procedure getUserByUsername(uname varchar(100))
begin
	select ID from users where  username = uname;
end@


drop procedure if exists recentCreatedQuizes@
create procedure recentCreatedQuizes(userID int)
begin
	select * from quizes as a
	where a.creatorID = userID
	order by a.quiz_date desc
	limit 2;
end@

drop procedure if exists changeSeen@
create procedure changeSeen(msgID int)
begin
	UPDATE messages SET seen = 1 WHERE ID = msgID;
end@

-- unda daabrunos si qvizebi romlebi userID-m sheqmna da tan bolos itamasha es qvizebi vigaceebma
-- anu bolos natamashebi am useris mier sheyqmnili qvizebi
drop procedure if exists recentlyPlayedQuizes@
create procedure recentlyPlayedQuizes(uID int)
begin

	select a.*from quizes as a, take_quize as b
	where a.creatorID = uID
	and a.ID = b.quizID
	order by b.take_tike desc
	limit 2;


end@


create procedure findFriends(usID int)
begin
	select friendID from friends 
	where userID = usID;
end@


create procedure RecPlaydQuizesByUser(uID int)
begin
	select a.* from quizes as a, take_quize as b
	where b.userID = uID
	and a.ID = b.quizID
	order by b.take_tike desc
	limit 2;
end@


create procedure getAllPlayedQuizes(uID int)
begin
	select a.ID, a.quiz_name,b.score from quizes as a, take_quize as b
	where b.userID = uID
	and a.ID = b.quizID
	order by b.score desc;
end@


create procedure getQuizByID(qID int)
begin
	select * from quizes
	where ID = qID;
end@
