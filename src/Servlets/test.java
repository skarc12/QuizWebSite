package Servlets;

import model.Message;
import model.Quiz;
import model.User;

public class test {
	public static void main(String[] args) {
		DBHelper manager = new DBHelper();
		User user= DBHelper.findUser("bidza");
		System.out.println(user.getUserID());
		manager.addFriend(user, "caleb");
	}
}
