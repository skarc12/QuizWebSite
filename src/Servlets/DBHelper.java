package Servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;



import model.Challenge;
import model.FillTheGapsQuestion;
import model.FriendRequest;
import model.Message;
import model.MultipleChoiceQuestion;
import model.PictureQuizQuestion;
import model.QuestinAnswerQuestion;
import model.Question;
import model.Question.QuestionType;
import model.Quiz.QuizHandle;
import model.Quiz;
import model.User;

public class DBHelper {
	public static User addUser(String name, String lastname, String username,
			String email, String password) {

		User user = null;

		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call addUser(?,?,?,?,?)}");
			stm.setString(1, name);
			stm.setString(2, lastname);
			stm.setString(3, username);
			stm.setString(4, email);
			stm.setString(5, password);
			stm.execute();
			stm.close();
			/*
			 * String update =
			 * "Insert into users(first_name, last_name, username, email, password) values("
			 * + "'" + name + "','" + lastname + "','" + username + "','" +
			 * email + "','" + password + "');";
			 * 
			 * stmt.executeUpdate(update);
			 */
			CallableStatement stmt = con
					.prepareCall("{call getUserByUsername(?)}");
			stmt.setString(1, username);
			ResultSet set = stmt.executeQuery();
			int userID = 0;
			if (set.next()) {
				userID = set.getInt("ID");
			}
			user = new User(name, lastname, email, username, password);
			user.setUserID(userID);

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return user;
	}

	/*
	 * This functions return true if the user alredy exists in database, and
	 * false if it is not. For that we have mysql function which searches in the
	 * database for that username.
	 */
	public static boolean exists(String username) {
		Boolean outputValue = false;
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{? = call nameInUse(?)}");
			stm.registerOutParameter(1, Types.BOOLEAN);
			stm.setString(2, username);
			stm.execute();
			outputValue = stm.getBoolean(1);
			stm.close();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Statement stmt = con.createStatement();
		// result = con.prepareCall("call nameInUse(username)");
		System.out.println("booleanis pasuxia: " + outputValue);
		return outputValue;
	}

	public static User findUser(String username) {
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			Statement stmt = con.createStatement();
			String select = "Select * from users where username='" + username
					+ "'";
			ResultSet set = stmt.executeQuery(select);
			if (set.next()) {
				System.out.println("into if");
				int id = set.getInt("ID");
				String firstname = set.getString("first_name");
				String lastname = set.getString("last_name");
				String email = set.getString("email");
				String password = set.getString("password");
				User user = new User(firstname, lastname, email, username,
						password, id);
				stmt.close();
				return user;
			} else {
				System.out.println("not found: " + username);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

	public static void markMessageAsSeen(int msg) {
		Connection con = null;
		CallableStatement stm;
		try {
			con = DBConnection.initConnection();
			stm = con.prepareCall("{call changeSeen(?)}");
			stm.setInt(1, msg);
			stm.execute();
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void markChallengeAsSeen(int challenge) {
		Connection con = null;
		CallableStatement stm;
		try {
			con = DBConnection.initConnection();
			stm = con.prepareCall("{call changeSeenInChallenges(?)}");
			stm.setInt(1, challenge);
			stm.execute();
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void markRequestAsSeen(int req) {
		Connection con = null;
		CallableStatement stm;
		try {
			con = DBConnection.initConnection();
			stm = con.prepareCall("{call changeSeenInRequests(?)}");
			stm.setInt(1, req);
			stm.execute();
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void sendChallenge(User user, String username, int score,
			int quizID, String msg) {
		int from = user.getUserID();
		int to = findUser(username).getUserID();
		Connection con = null;
		CallableStatement stm = null;
		try {
			con = DBConnection.initConnection();
			stm = con.prepareCall("{call insertChallenge(?,?,?,?,?)}");
			stm.setInt(1, from);
			stm.setInt(2, to);
			stm.setInt(3, quizID);
			stm.setString(4, msg);
			stm.setInt(5, score);
			stm.execute();
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// ese igi aq unda daabrunos qizebis masivi, romelic yvelaze metma userma
	// gaaketa
	public static Quiz[] getPopularQuizes() {
		Connection con;
		Quiz[] quiz = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call getPopularQuizes()}");
			ResultSet result = stm.executeQuery();
			quiz = makeQuizObject(result);
			stm.close();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return quiz;
	}

	// aq unda daabrunos tavisi sheqmnili quizebi
	public static Quiz[] getRecentlyCreatedQuizes(User user) {
		Quiz[] quizes = null;
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call recentCreatedQuizes(?)}");
			stm.setInt(1, user.getUserID());
			ResultSet result = stm.executeQuery();
			quizes = makeQuizObject(result);
			stm.close();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return quizes;
	}

	// aq unda daabrunot tavisi natamashebi quizebi
	public static Quiz[] getRecentQuizActivities(User user) {
		Quiz[] quizes = null;
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm;
			stm = con.prepareCall("{call RecPlaydQuizesByUser(?)}");
			stm.setInt(1, user.getUserID());
			ResultSet set = stm.executeQuery();
			quizes = makeQuizObject(set);
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return quizes;
	}

	// aq unda daabrunos tavisi sheqmnili quizebi, romelic vigacam gaiara bolos
	public static Quiz[] getUserPlayedQuizes(User user) {
		Quiz[] quizes = null;
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm;
			stm = con.prepareCall("{call recentlyPlayedQuizes(?)}");
			stm.setInt(1, user.getUserID());
			ResultSet set = stm.executeQuery();
			quizes = makeQuizObject(set);
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return quizes;
	}

	public static ArrayList<User> getFriends(User user) {
		ArrayList<User> friends = new ArrayList<>();
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm;
			stm = con.prepareCall("{call findFriends(?)}");
			stm.setInt(1, user.getUserID());
			ResultSet set = stm.executeQuery();
			User friend = null;
			while (set.next()) {
				int userID = set.getInt("friendID");
				friend = generateUser(userID);
				friends.add(friend);
			}
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return friends;
	}

	public static Challenge[] getUnseenChallenges(User user) {
		Challenge[] challenges = null;
		List<Challenge> chall = new ArrayList<>();
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call getUnseenChallanges(?)}");
			stm.setInt(1, user.getUserID());
			ResultSet set = stm.executeQuery();
			Challenge ch = null;
			User sender = null;
			User reciever = null;
			while (set.next()) {
				int challengeID = set.getInt("ID");
				int from = set.getInt("fromID");
				int to = user.getUserID();
				String text = set.getString("msg");
				int quizID = set.getInt("quizID");
				int firstScore = set.getInt("first_Score");
				int secondScore = set.getInt("second_Score");
				boolean seen = set.getBoolean("challenge_seen");
				sender = generateUser(from);
				reciever = generateUser(to);
				ch = new Challenge(challengeID, sender, reciever, firstScore,
						secondScore, text, seen, quizID);
				chall.add(ch);
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		challenges = new Challenge[chall.size()];
		return chall.toArray(challenges);
	}

	public static FriendRequest[] getUnseenFriendRequest(User user) {
		FriendRequest[] requests = null;
		List<FriendRequest> req = new ArrayList<>();
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call getUnseenFriendRequests(?)}");
			stm.setInt(1, user.getUserID());
			ResultSet set = stm.executeQuery();
			FriendRequest r = null;
			User sender = null;
			User reciever = null;
			while (set.next()) {
				int id = set.getInt("ID");
				int from = set.getInt("fromID");
				int to = user.getUserID();
				boolean seen = set.getBoolean("seen");
				sender = generateUser(from);
				reciever = generateUser(to);
				r = new FriendRequest(id, sender, reciever, seen);
				req.add(r);
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		requests = new FriendRequest[req.size()];
		return req.toArray(requests);
	}

	/*
	 * friend this two users.
	 */
	public static void addFriend(User user, String username) {
		int first = user.getUserID();
		System.out.println(first);
		int second = findUser(username).getUserID();
		System.out.println(second);
		makeFriends(first, second);
		sendRequest(first, second);

	}

	private static void sendRequest(int first, int second) {
		Connection con = null;
		CallableStatement stm;
		try {
			con = DBConnection.initConnection();
			stm = con.prepareCall("{call insertRequest(?,?)}");
			stm.setInt(1, first);
			stm.setInt(2, second);
			stm.execute();
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void makeFriends(int first, int second) {
		Connection con = null;
		CallableStatement stm;
		try {
			con = DBConnection.initConnection();
			stm = con.prepareCall("{call insertFriend(?,?)}");
			System.out.println(first);
			System.out.println(second);
			stm.setInt(1, first);
			stm.setInt(2, second);
			stm.execute();
			System.out.println(first);
			System.out.println(second);
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/*
	 * unfriend two users.
	 */
	public static void unfriend(User user, String username) {
		Connection con = null;
		int second = findUser(username).getUserID();
		CallableStatement stm;
		try {
			con = DBConnection.initConnection();
			stm = con.prepareCall("{call unfriend(?,?)}");
			stm.setInt(1, user.getUserID());
			stm.setInt(2, second);
			stm.execute();
		} catch (SQLException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// aq unda daabrunos message, romelic ger ar waukitxavs..
	public static Message[] getUserUnreadMessages(int userid) {
		Message[] messages = null;
		List<Message> mess = new ArrayList<>();
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call getUnreadMessages(?)}");
			stm.setInt(1, userid);
			ResultSet set = stm.executeQuery();
			Message msg = null;
			User sender = null;
			User reciever = null;
			while (set.next()) {
				int msgID = set.getInt("ID");
				int from = set.getInt("fromID");
				int to = userid;
				String text = set.getString("msg");
				sender = generateUser(from);
				reciever = generateUser(to);
				msg = new Message(msgID, sender, reciever, text, false);
				mess.add(msg);
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		messages = new Message[mess.size()];
		return mess.toArray(messages);
	}

	/*
	 * return all quizes that the user playd already, but not the array of quiz
	 * objects. it returns an array\ of object wich has quiz id, name and the
	 * point the user took on this quiz.
	 */
	public static QuizHandle[] getAllQuizes(User user) {
		QuizHandle[] info = null;
		List<QuizHandle> quizes = new ArrayList<>();
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call getAllPlayedQuizes(?)}");
			stm.setInt(1, user.getUserID());
			ResultSet set = stm.executeQuery();
			QuizHandle quiz = null;
			while (set.next()) {
				int id = set.getInt("ID");
				String name = set.getString("quiz_name");
				int score = set.getInt("score");
				quiz = new QuizHandle(id, name, score);
				quizes.add(quiz);

			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		info = new QuizHandle[quizes.size()];
		return quizes.toArray(info);
	}

	public static Quiz getQuizByID(int quizID) {
		Quiz result = null;
		Quiz[] quiz = null;
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call getQuizByID(?)}");
			stm.setInt(1, quizID);
			ResultSet set = stm.executeQuery();
			quiz = makeQuizObject(set);
			if (quiz.length == 1) {
				result = quiz[0];
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private static Quiz[] makeQuizObject(ResultSet res) {
		List<Quiz> questslist = new ArrayList<Quiz>();
		try {
			while (res.next()) {
				int userID = res.getInt("creatorID");
				User user = generateUser(userID);
				int id = res.getInt("ID");
				String name = res.getString("quiz_name");
				String description = res.getString("description");
				boolean isOnePage = res.getBoolean("isOnePage");
				boolean feedback = res.getBoolean("feedback");
				boolean random = res.getBoolean("random");
				Date date = res.getDate("quiz_date");
				ArrayList<Question> quests = getQuestions(id);
				Quiz quiz = new Quiz(id, name, date);
				quiz.setDescription(description);
				quiz.setFeedback(feedback);
				quiz.setOnePage(isOnePage);
				quiz.setOwnes(user);
				quiz.setQuestions(quests);
				quiz.setRandom(random);
				questslist.add(quiz);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Quiz[] result = new Quiz[questslist.size()];
		return questslist.toArray(result);
	}

	public static ArrayList<Question> getQuestions(int id) throws Exception {
		ArrayList<Question> quests = new ArrayList<Question>();
		Connection con = DBConnection.initConnection();
		CallableStatement stm = con.prepareCall("{call getQuestionIDs(?)}");
		stm.setInt(1, id);
		ResultSet set = stm.executeQuery();
		while (set.next()) {
			int questionID = set.getInt("ID");
			int categoryID = set.getInt("question_categoryID");
			Question quest = getQuestion(questionID, categoryID);
			quests.add(quest);
		}
		con.close();
		stm.close();
		return quests;
	}

	private static Question getQuestion(int questionID, int categoryID)
			throws Exception {
		Connection con = DBConnection.initConnection();
		CallableStatement stm = con.prepareCall("{call getQuestion(?,?)}");
		stm.setInt(1, questionID);
		stm.setInt(2, categoryID);
		ResultSet set = stm.executeQuery();
		Question quest = null;
		while (set.next()) {
			if (categoryID == 1) {
				quest = initializeFillTheGaps(set, categoryID);
			} else if (categoryID == 2) {
				quest = initializeMulChoice(set, categoryID);
			} else if (categoryID == 3) {
				quest = initializePictureQuiz(set, categoryID);
			} else if (categoryID == 4) {
				quest = initializeSimpleQuiz(set, categoryID);
			}
		}
		return quest;
	}

	private static Question initializeSimpleQuiz(ResultSet set, int categoryID)
			throws SQLException {
		String answer = set.getString("answer");
		int score = set.getInt("score");
		String questionText = set.getString("question");
		Question question = new QuestinAnswerQuestion(questionText, answer,
				score);
		return question;
	}

	private static Question initializePictureQuiz(ResultSet set, int categoryID)
			throws SQLException {
		String answer = set.getString("answer");
		int score = set.getInt("score");
		String url = set.getString("url");
		Question question = new PictureQuizQuestion(url, answer, score);
		return question;
	}

	private static Question initializeMulChoice(ResultSet set, int categoryID)
			throws SQLException {
		String corr_answer = set.getString("correct_answer");
		int score = set.getInt("score");
		String questionText = set.getString("question");
		String ans1 = set.getString("answer1");
		String ans2 = set.getString("answer2");
		String ans3 = set.getString("answer3");
		String ans4 = set.getString("answer4");
		Question question = new MultipleChoiceQuestion(questionText,
				new String[] { ans1, ans2, ans3, ans4 }, corr_answer, score);
		return question;
	}

	private static Question initializeFillTheGaps(ResultSet set, int categoryID)
			throws SQLException {
		String answer = set.getString("answer");
		int score = set.getInt("score");
		String questionText = set.getString("question");
		int numOfAnswers = set.getInt("num_of_answers");
		List<String> a = new ArrayList<>();
		String[] answers = new String[numOfAnswers];
		int fromIndex = 0, size = 0;
		while (numOfAnswers != 0) {
			size = answer.indexOf("#", fromIndex)+1;
			a.add(answer.substring(fromIndex, size));
			fromIndex = size;
			numOfAnswers--;
		}
		Question question = new FillTheGapsQuestion(questionText,
				a.toArray(answers), score);
		return question;
	}

	private static User generateUser(int userID) throws SQLException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		User user = null;
		Connection con = DBConnection.initConnection();
		CallableStatement stm = con.prepareCall("{call getUser(?)}");
		stm.setInt(1, userID);
		ResultSet set = stm.executeQuery();
		while (set.next()) {
			int id = set.getInt("ID");
			String firstname = set.getString("first_name");
			String lastname = set.getString("last_name");
			String username = set.getString("username");
			String email = set.getString("email");
			String password = set.getString("password");
			user = new User(firstname, lastname, email, username, password, id);
		}
		con.close();
		stm.close();
		return user;
	}

	public static void addQuizIntoDatabase(Quiz quiz) {
		long time = System.currentTimeMillis();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
		Date createTime = quiz.getDate();
		String descrpt = quiz.getDescription();
		User owner = quiz.getOwnes();
		ArrayList<Question> questions = quiz.getQuestions();
		String name = quiz.getQuizName();
		Connection con;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con
					.prepareCall("{call insertQuiz(?,?,?,?)}");
			stm.setInt(1, owner.getUserID());
			stm.setString(2, name);
			stm.setString(3, descrpt);
			stm.setTimestamp(4, timestamp);
			int quizid = 0;
			int questionID = 0;
			ResultSet set = stm.executeQuery();
			while (set.next()) {
				quizid = set.getInt("ID");
				System.out.println("quiz id"+quizid);
				QuestionType type;
				for (int i = 0; i < questions.size(); i++) {
					type = questions.get(i).getType();
					if (type == QuestionType.MULTIPLE_CHOICE) {
						MultipleChoiceQuestion q;
						q = (MultipleChoiceQuestion) questions.get(i);
						stm = con
								.prepareCall("{call insertIntoQuestions(?,?)}");
						stm.setInt(1, 2);
						stm.setInt(2, quizid);
						set = stm.executeQuery();
						while (set.next()) {
							questionID = set.getInt("ID");
						}
						stm = con
								.prepareCall("{call insertIntoMultChoice(?,?,?,?,?,?,?,?)}");
						stm.setString(1, q.getQuestion());
						stm.setInt(2, quizid);
						stm.setString(3, q.getAnswer1());
						stm.setString(4, q.getAnswer2());
						stm.setString(5, q.getAnswer3());
						stm.setString(6, q.getAnswer4());
						stm.setString(7, q.getCorrectAnswer());
						stm.setInt(8, questionID);
						stm.execute();
					} else if (type == QuestionType.PICTURE_QUIZ) {
						PictureQuizQuestion q;
						q = (PictureQuizQuestion) questions.get(i);
						stm = con
								.prepareCall("{call insertIntoQuestions(?,?)}");
						stm.setInt(1, 3);
						stm.setInt(2, quizid);
						set = stm.executeQuery();
						while (set.next()) {
							questionID = set.getInt("ID");
						}
						stm = con
								.prepareCall("{call insertIntoPictQuez(?,?,?,?)}");
						stm.setString(1, q.getUrl());
						stm.setInt(3, quizid);
						stm.setString(2, q.getAnswer());
						stm.setInt(4, questionID);
						stm.execute();
					} else if (type == QuestionType.QUESTION_ANSWER) {
						QuestinAnswerQuestion q;
						q = (QuestinAnswerQuestion) questions.get(i);
						stm = con
								.prepareCall("{call insertIntoQuestions(?,?)}");
						stm.setInt(1, 4);
						stm.setInt(2, quizid);
						set = stm.executeQuery();
						while (set.next()) {
							questionID = set.getInt("ID");
						}
						stm = con
								.prepareCall("{call insertIntoQuestAnswer(?,?,?,?)}");
						stm.setString(1, q.getQuestion());
						stm.setInt(3, quizid);
						stm.setString(2, q.getAnswer());
						stm.setInt(4, questionID);
						stm.execute();
					} else if (type == QuestionType.FILL_THE_GAPS) {
						FillTheGapsQuestion q;
						q = (FillTheGapsQuestion) questions.get(i);
						stm = con
								.prepareCall("{call insertIntoQuestions(?,?)}");
						stm.setInt(1, 1);
						stm.setInt(2, quizid);
						set = stm.executeQuery();
						while (set.next()) {
							questionID = set.getInt("ID");
						}
						stm = con
								.prepareCall("{call insertIntoFillTheGaps(?,?,?,?,?)}");
						stm.setString(1, q.getQuestion());
						stm.setInt(4, quizid);
						stm.setInt(5, questionID);
						stm.setString(2, q.getAnswers().toString());
						stm.setInt(3, q.getAnswers().length);
						stm.execute();
					}
				}
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void addMessage(User from, String to, String msg){
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call sendMessage(?,?,?)}");
			stm.setInt(1, from.getUserID());
			System.out.println(from.getUserID());
			stm.setInt(2, findUser(to).getUserID());
			System.out.println(findUser(to).getUserID());
			stm.setString(3, msg);
			stm.execute();

		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static User [] searchUsers(String text){
		Connection con = null;
		ArrayList<User> users = new ArrayList<>();
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call search(?)}");
			stm.setString(1, text);
			ResultSet res= stm.executeQuery();
			int userID;
			User user;
			while(res.next()){
				userID = res.getInt("ID");
				user = generateUser(userID);
				users.add(user);
			}

		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User [] answer = new User[users.size()];
		return users.toArray(answer);
	}
	public static void playQuiz(User user, Quiz q, int point, java.sql.Timestamp took_time){
		System.out.println(took_time);
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			System.out.println("shemovida1");
			CallableStatement stm = con.prepareCall("{call inserIntoQuiz_take(?,?,?,?)}");
			
			stm.setInt(1, q.getID());
			System.out.println("shemovida2");
			stm.setInt(2, user.getUserID());
			System.out.println(user.getUserID());
			
			System.out.println("shemovida4");
			stm.setInt(3, point);;
			System.out.println("shemovida5");
			stm.setTimestamp(4, took_time);
			System.out.println("shemovida6");
			stm.execute();

		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Quiz [] getAllQsCreatedByUser(User user){
		Quiz[] q = null;
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call getAllQuizesCreatedByUser(?)}");
			stm.setInt(1, user.getUserID());
			
			ResultSet res = stm.executeQuery();
			if(res!=null){
				q = makeQuizObject(res);
			}
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return q;
	}
}

