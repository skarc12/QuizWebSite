package Servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

import model.FillTheGapsQuestion;
import model.Message;
import model.MultipleChoiceQuestion;
import model.PictureQuizQuestion;
import model.QuestinAnswerQuestion;
import model.Question;
import model.Question.QuestionType;
import model.Quiz;
import model.User;



public class DBHelper {
	public static User addUser(String name, String lastname, String username,
			String email, String password){
		
		User user = null;
		
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call addUser(?,?,?,?,?)}");
			stm.setString(1,name);
			stm.setString(2,lastname);
			stm.setString(3,username);
			stm.setString(4,email);
			stm.setString(5,password);
			stm.execute();
			stm.close();
			/*String update = "Insert into users(first_name, last_name, username, email, password) values("
					+ "'"
					+ name
					+ "','"
					+ lastname
					+ "','"
					+ username
					+ "','"
					+ email + "','" + password + "');";

			stmt.executeUpdate(update);*/
			CallableStatement stmt = con.prepareCall("{call getUserIDByUsername(?)}");
			stmt.setString(1,username);
			ResultSet set= stmt.executeQuery();
			int userID = 0;
			if(set.next()){
				userID = set.getInt("ID");
			}
			user = new User(name, lastname, email, username, password);
			user.setUserID(userID);
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
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
	 * This functions return true if the user alredy exists in database, and false if it is not.
	 * For that we have mysql function which searches in the database for that username.
	 */
	public static boolean exists(String username){
		Boolean outputValue = false;
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{? = call nameInUse(?)}");
			stm.registerOutParameter(1,Types.BOOLEAN);
			stm.setString(2,username);
			stm.execute();
			outputValue = stm.getBoolean(1);
			stm.close();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			//Statement stmt = con.createStatement();
		//result = con.prepareCall("call nameInUse(username)");
		System.out.println("booleanis pasuxia: " +outputValue);
		return outputValue;
	}
	public static User findUser(String username){
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			Statement stmt = con.createStatement();
			String select = "Select * from users where username='"+ username + "'";
			ResultSet set= stmt.executeQuery(select);
			if(set.next()){
				System.out.println("into if");
				int id = set.getInt("ID");
				String firstname =  set.getString("first_name");
				String lastname = set.getString("last_name");
				String email = set.getString("email");
				String password = set.getString("password");
				User user = new User(firstname, lastname, email, username, password,id);
				stmt.close();
				return user;
			}else{
				System.out.println("not found: "+username);
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
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
		
	}
	// ese igi aq unda daabrunos qizebis masivi, romelic yvelaze metma userma gaaketa
	public  static Quiz[] getPopularQuizes(){
		Connection con;
		Quiz[] quiz = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call getPopularQuizes()}");
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
	//aq unda daabrunos tavisi sheqmnili quizebi
	public static Quiz[] getRecentlyCreatedQuizes(){
		
		return null;
	}
	//aq unda daabrunot tavisi gaketebuli quizebi
	public static Quiz[] getRecentQuizActivities(){
		
		return null;
	}
	//aq unda daabrunos tavisi sheqmnili quizebi, romelic vigacam gaiara bolos
	public static Quiz[] getUserPlayedQuizes(){
		return null;
	}
	//aq unda daabrunos message, romelic ger ar waukitxavs.. 
	public static Message[] getUserUnreadMessages(int userid){
		Message [] messages = null;
		List<Message> mess = new ArrayList<>();
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call getUnreadMessages(?)}");
			stm.setInt(1,userid);
			ResultSet set = stm.executeQuery();
			Message msg = null;
			User sender = null;
			User reciever = null;
			while(set.next()){
				int from = set.getInt("fromID");
				int to = userid;
				String text = set.getString("msg");
				sender = generateUser(from);
				reciever = generateUser(to);
				msg = new Message(sender, reciever, text, false);
				mess.add(msg);
			}
		}catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		messages = new Message [mess.size()];
		return mess.toArray(messages);
	}
	private static Quiz[] makeQuizObject(ResultSet res){
		List<Quiz> questslist = new ArrayList<Quiz>();
		try {
			while(res.next()){
				int userID = res.getInt("creatorID");
				User user = generateUser(userID);
				int id =  res.getInt("ID");
				String name = res.getString("quiz_name");
				String description = res.getString("description");
				boolean isOnePage = res.getBoolean("isOnePage");
				boolean feedback = res.getBoolean("feedback");
				boolean random = res.getBoolean("random");
				Date date = res.getDate("quiz_date");
				ArrayList<Question> quests = getQuestions(id);
				Quiz quiz = new Quiz(name, date);
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
	private static ArrayList<Question> getQuestions(int id) throws Exception{
		ArrayList<Question> quests = new ArrayList<Question>();
		Connection con = DBConnection.initConnection();
		CallableStatement stm = con.prepareCall("{call getQuestionIDs(?)}");
		stm.setInt(1,id);
		ResultSet set = stm.executeQuery();
		while(set.next()){
			int questionID = set.getInt("ID");
			int categoryID = set.getInt("question_categoryID");
			Question quest = getQuestion(questionID,categoryID);
			quests.add(quest);
		}
		con.close();
		stm.close();
		return quests;
	}
	private static Question getQuestion(int questionID, int categoryID) throws Exception {
		Connection con = DBConnection.initConnection();
		CallableStatement stm = con.prepareCall("{call getQuestion(?,?)}");
		stm.setInt(1,questionID);
		stm.setInt(2,categoryID);
		ResultSet set = stm.executeQuery();
		Question quest = null;
		while(set.next()){
			if(categoryID == 1){
				quest = initializeFillTheGaps(set, categoryID);
			}else if(categoryID ==2){
				quest = initializeMulChoice(set, categoryID);
			}else if(categoryID == 3){
				quest = initializePictureQuiz(set, categoryID);
			}else if(categoryID == 4){
				quest = initializeSimpleQuiz(set, categoryID);
			}
		}
		return quest;
	}
	private static Question initializeSimpleQuiz(ResultSet set, int categoryID) throws SQLException {
		String answer = set.getString("answer");
		int score = set.getInt("score");
		String questionText = set.getString("question");
		Question question = new QuestinAnswerQuestion(questionText, answer, score);
		return question;
	}
	private static Question initializePictureQuiz(ResultSet set, int categoryID) throws SQLException {
		String answer = set.getString("answer");
		int score = set.getInt("score");
		String url = set.getString("url");
		Question question = new PictureQuizQuestion(url,answer,score);
		return question;
	}
	private static Question initializeMulChoice(ResultSet set, int categoryID) throws SQLException {
		String corr_answer = set.getString("correct_answer");
		int score = set.getInt("score");
		String questionText = set.getString("question");
		String ans1 = set.getString("answer1");
		String ans2 = set.getString("answer2");
		String ans3 = set.getString("answer3");
		String ans4 = set.getString("answer4");
		Question question = new MultipleChoiceQuestion(questionText, new String[]{ ans1, ans2, ans3, ans4}, corr_answer, score);
		return question;
	}
	private static Question initializeFillTheGaps(ResultSet set, int categoryID) throws SQLException {
		String answer = set.getString("answer");
		int score = set.getInt("score");
		String questionText = set.getString("question");
		int numOfAnswers = set.getInt("num_of_answers");
		List<String> a = new ArrayList<>();
		String [] answers = new String[numOfAnswers];
		int fromIndex = 0, endIndex = 0;
		String ans;
		while(numOfAnswers!=0){
			endIndex=answer.indexOf("#", fromIndex);
			a.add(answer.substring(fromIndex, endIndex+1));
			fromIndex = endIndex;
			numOfAnswers--;
		}
		Question question = new FillTheGapsQuestion(questionText, a.toArray(answers), score);
		return question;
	}
	private static User generateUser(int userID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		User user = null;
		Connection con = DBConnection.initConnection();
		CallableStatement stm = con.prepareCall("{call getUser(?)}");
		stm.setInt(1,userID);
		ResultSet set = stm.executeQuery();
		while(set.next()){
			int id = set.getInt("ID");
			String firstname =  set.getString("first_name");
			String lastname = set.getString("last_name");
			String username = set.getString("username");
			String email = set.getString("email");
			String password = set.getString("password");
			user = new User(firstname, lastname, email, username, password,id);
		}
		con.close();
		stm.close();
		return user;
	}
	
}
