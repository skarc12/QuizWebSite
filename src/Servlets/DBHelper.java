package Servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import model.Message;
import model.Question;
import model.Quiz;
import model.User;



public class DBHelper {
	public User addUser(String name, String lastname, String username,
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
			user = new User(name, lastname, email, username, password);
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
	public boolean exists(String username){
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
	public User findUser(String username){
		Connection con = null;
		try {
			con = DBConnection.initConnection();
			Statement stmt = con.createStatement();
			String select = "Select * from users where username='"+ username + "'";
			ResultSet set= stmt.executeQuery(select);
			if(set.next()){
				System.out.println("into if");
				String firstname =  set.getString("first_name");
				String lastname = set.getString("last_name");
				String email = set.getString("email");
				String password = set.getString("password");
				User user = new User(firstname, lastname, email, username, password);
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
	public  Quiz[] getPopularQuizes(){
		Connection con;
		try {
			con = DBConnection.initConnection();
			CallableStatement stm = con.prepareCall("{call getPopularQuizes()}");
			ResultSet result = stm.executeQuery();
			
			stm.close();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//aq unda daabrunos tavisi sheqmnili quizebi
	public Quiz[] getRecentlyCreatedQuizes(){
		
		return null;
	}
	//aq unda daabrunot tavisi gaketebuli quizebi
	public Quiz[] getRecentQuizActivities(){
		
		return null;
	}
	//aq unda daabrunos tavisi sheqmnili quizebi, romelic vigacam gaiara bolos
	public Quiz[] getUserPlayedQuizes(){
		return null;
	}
	//aq unda daabrunos message, romelic ger ar waukitxavs.. 
	public Message[] getUserUnreadMessages(){
		return null;
	}
	private Quiz[] makeQuizObject(ResultSet res){
		Quiz[] result = null;
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
				Question [] quests;
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	private User generateUser(int userID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		User user = null;
		Connection con = DBConnection.initConnection();
		CallableStatement stm = con.prepareCall("{call getUser(?)}");
		stm.setInt(1,userID);
		ResultSet set = stm.executeQuery();
		while(set.next()){
			String firstname =  set.getString("first_name");
			String lastname = set.getString("last_name");
			String username = set.getString("username");
			String email = set.getString("email");
			String password = set.getString("password");
			user = new User(firstname, lastname, email, username, password);
		}
		return user;
	}
	
}
