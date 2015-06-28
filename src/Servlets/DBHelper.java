package Servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Message;
import model.Quiz;
import model.User;



public class DBHelper {
	public User addUser(String name, String lastname, String username,
			String email, String password){
		
		User user = null;
		
		Connection con;
		try {
			con = DBConnection.initConnection();
			Statement stmt = con.createStatement();

			String update = "Insert into users(first_name, last_name, username, email, password) values("
					+ "'"
					+ name
					+ "','"
					+ lastname
					+ "','"
					+ username
					+ "','"
					+ email + "','" + password + "');";

			stmt.executeUpdate(update);
			con.close();
			user = new User(name, lastname, email, username, password);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	public User findUser(String username){
		Connection con;
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
		}
		return null;
		
		
	}
	// ese igi aq unda daabrunos qizebis masivi, romelic yvelaze metma userma gaaketa
	public  Quiz[] getPopularQuizes(){
		
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
	
}
