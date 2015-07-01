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


drop procedure if exists getPopularQuizes@
create procedure getPopularQuizes()
begin
	select b.* from take_quize as a,quizes as b
	where a.quizID = b.ID
	group by a.quizID
	order by count(a.quizID) desc
	limit 5;
end@

drop procedure if exists getQuestionIDs@
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

drop procedure if exists getUserByUsername@
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
	limit 5;
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
	limit 5;


end@

drop procedure if exists findFriends@
create procedure findFriends(usID int)
begin
	select friendID from friends 
	where userID = usID;
end@

drop procedure if exists getUnseenChallanges@
create procedure getUnseenChallanges(usID int)
begin
	select * from challenge
	where toID = usID
	and challenge_seen  = 0;
end@

drop procedure if exists changeSeenInChallenges@
create procedure changeSeenInChallenges(chID int)
begin
	UPDATE challenge SET challenge_seen = 1 WHERE ID = chID;
end@

drop procedure if exists getUnseenFriendRequests@
create procedure getUnseenFriendRequests(usID int)
begin
	select * from friendrequest
	where toID = usID
	and seen  = 0;
end@

drop procedure if exists changeSeenInRequests@
create procedure changeSeenInRequests(frID int)
begin
	UPDATE friendrequest SET seen = 1 WHERE ID = frID;
end@

drop procedure if exists RecPlaydQuizesByUser@
create procedure RecPlaydQuizesByUser(uID int)
begin
	select a.* from quizes as a, take_quize as b
	where b.userID = uID
	and a.ID = b.quizID
	order by b.take_tike desc
	limit 5;
end@

drop procedure if exists getAllPlayedQuizes@
create procedure getAllPlayedQuizes(uID int)
begin
	select a.ID, a.quiz_name,b.score from quizes as a, take_quize as b
	where b.userID = uID
	and a.ID = b.quizID
	order by b.score desc;
end@

drop procedure if exists getQuizByID@
create procedure getQuizByID(qID int)
begin
	select * from quizes
	where ID = qID;
end@

drop procedure if exists insertRequest@
create procedure insertRequest(fromid int, toid int)
begin
	insert into friendrequest(fromID,toID,seen)
	values(fromid,toid,0);
	
end@

drop procedure if exists insertFriend@
create procedure insertFriend(userid int, friendid int)
begin
	insert into friends(userID, friendID)
	values (userid, friendid);
	insert into friends(userID, friendID)
	values (friendid, userid);
end@

drop procedure if exists unfriend@
create procedure unfriend(userid int, friendid int)
begin
	delete from friends where userID = userid;
	delete from friends where userID = friendid;
end@

drop procedure if exists insertChallenge@
create procedure insertChallenge(fromid int, toid int, quizid int, mg text, fscore int)
begin
	insert into challenge(fromID, toID, quizID, msg, first_Score, second_Score, challenge_seen)
	values(fromid, toid, quizid , mg, fscore, 0, 0);

end@

drop procedure if exists insertIntoQuestions@
create procedure insertIntoQuestions(catId int, quizid int)
begin
	insert into questions(question_categoryID, quizID)
	values(catId, quizid);
	select Max(ID) as ID from questions;
end@
drop procedure if exists insertQuiz@
create procedure insertQuiz(creatorid int ,qname varchar(100),descript varchar(100), createDate datetime)
begin
	insert into quizes(creatorID, quiz_name, description, quiz_date)
	values(creatorid, qname, descript, createDate);
	select Max(ID) as ID from quizes;
end@
drop procedure if exists insertIntoMultChoice@

create procedure insertIntoMultChoice(quest varchar(500), quizid int, answ1 varchar(100), answ2 varchar(100),
										answ3 varchar(100), answ4 varchar(100), corr_answ varchar(100), questid int)
begin
	insert into multiple_choice(question,quizID,answer1,answer2,answer3,answer4,correct_answer,questionID)
	values(quest, quizid, answ1, answ2, answ3, answ4, corr_answ, questid);
end@
drop procedure if exists insertIntoPictQuez@

create procedure insertIntoPictQuez(link varchar(500), answ varchar(90), quizid int, questid int)
begin
	insert into picture_quiz(url,answer,quizID,questionID)
	values(link, answ, quizid, questid);
end@


drop procedure if exists insertIntoQuestAnswer@

create procedure insertIntoQuestAnswer(quest varchar(500), answ varchar(100), quizid int, questid int )
begin
	insert into question_answer (question,answer,quizID,questionID)
	values(quest, answ, quizid, questid);
end@


drop procedure if exists insertIntoFillTheGaps@
create procedure insertIntoFillTheGaps(quest varchar(500), answ varchar(500), num int, quizid int, questid int)
begin
	insert into fill_the_gaps (question,answer,num_of_answers,quizID,questionID)
	values(quest, answ, num, quizid, questid);
end@

drop procedure if exists sendMessage@
create procedure sendMessage(fromid int, toid int, msg text)
begin
	insert into messages(fromID,toID,msg,seen)
	values(fromid, toid, msg, 0);

end@

drop procedure if exists search@
create procedure search(str text)
begin
	select * from users
	where username LIKE CONCAT('%', str, '%');

end@


drop procedure if exists inserIntoQuiz_take@
create procedure inserIntoQuiz_take(quizid int, userid int, pnt int, take_time datetime)
begin
	insert into take_quize(quizID,userID, score, take_tike)
	values(quizid, userid, pnt, take_time);
end@

drop procedure if exists getAllQuizesCreatedByUser@
create procedure getAllQuizesCreatedByUser(id int)
begin
	select * from quizes where creatorID = id;
end@
