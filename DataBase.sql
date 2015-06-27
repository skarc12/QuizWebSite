
create table users(
	ID int auto_increment not null primary key,
	first_name varchar(100),
	last_name varchar(100),
	username varchar(100),
	email varchar(100),
	password varchar(100)
);
alter table users add unique key (username);

create table categories(
	ID int auto_increment not null primary key,
	type varchar(30)
);

create table quizes(
	ID int not null auto_increment primary key,
	creatorID int,
	categoryID int,
	url varchar(100),
	quiz_name varchar(100),
	description varchar(100),
	isOnePage boolean,
	feedback boolean,
	random boolean,
	quiz_date datetime,
	foreign key (creatorID) references users (ID),
	foreign key (categoryID) references categories(ID)
);

create table take_quize(
	ID int auto_increment primary key,
	quizID int,
	userID int,
	score int,
	take_tike datetime,
	foreign key (userID) references users (ID),
	foreign key (quizID)  references quizes(ID)
);

create table multiple_choice(
	ID int auto_increment not null primary key,
	question varchar(500),
	quizID int,
	answer1 varchar(100),
	answer2 varchar(100),
	answer3 varchar(100),
	answer4 varchar(100),
	correct_answer varchar(100),
	score int,
	foreign key (quizID) references quizes(ID)
);

create table picture_quiz(
	ID int auto_increment not null primary key,
	url varchar(500),
	answer varchar(90),
	score int,
	quizID int,
	foreign key (quizID) references quizes(ID)
);
create table question_answer(
	ID int auto_increment not null primary key,
	question varchar(500),
	answer varchar(100),
	score int,
	quizID int,
	foreign key (quizID) references quizes(ID)
);

create table fill_the_gaps(
	ID int auto_increment not null primary key,
	question varchar(500),
	answer varchar(500),
	num_of_answers int,
	score int,
	quizID int,
	foreign key (quizID) references quizes(ID)
);

create table friends(
	ID int not null auto_increment primary key,
	userID int,
	friendID int,
	foreign key (userID) references users(ID),
	foreign key (friendID) references users(ID)
);


create table friendRequest(
	ID int auto_increment not null primary key,
	fromID int,
	toID int,
	seen boolean,
	foreign key (fromID) references users(ID),
	foreign key (toID) references users(ID)

);

create table messages(
	ID int auto_increment not null primary key,
	fromID int,
	toID int,
	msg text,
	foreign key (fromID) references users(ID),
	foreign key (toID) references users(ID)
);

create table challenge(
	ID int auto_increment primary key,
	fromID int,
	toID int,
	msg text,
	quizID int,
	first_Score int,
	second_Score int,
	challenge_seen boolean,
	challenge_status boolean,
	foreign key (fromID) references users(ID),
	foreign key (toID) references users(ID),
	foreign key(quizID) references quizes(ID)
	
);