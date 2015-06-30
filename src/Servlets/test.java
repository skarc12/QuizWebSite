package Servlets;

import model.Message;
import model.Paroli;
import model.Quiz;
import model.User;

public class test {
	public static void main(String[] args) {
//		DBHelper manager = new DBHelper();
//		User user= DBHelper.findUser("bidza");
//		System.out.println(user.getUserID());
//		manager.addFriend(user, "caleb");
		String k = "salomesalome";
		Paroli a = new Paroli();
		k = a.generateHashedPassword(k);
		System.out.println(k);
	}
}
