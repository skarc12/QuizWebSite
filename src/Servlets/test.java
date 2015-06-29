package Servlets;

import model.Message;
import model.Quiz;
import model.User;

public class test {
	public static void main(String[] args) {
		DBHelper manager = new DBHelper();
		User user= DBHelper.findUser("bidza");
		Quiz[] m = DBHelper.getUserPlayedQuizes(user);
		for(int i = 0; i<m.length; i++){
			System.out.println("gamomgzavni aris: " + m[i].getID());
		}
	}
}
