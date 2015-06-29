package Servlets;

import model.Message;

public class test {
	public static void main(String[] args) {
		DBHelper manager = new DBHelper();
		Message [] m = manager.getUserUnreadMessages(10);
		for(int i = 0; i<m.length; i++){
			System.out.println("gamomgzavni aris: " + m[i].getSender().getUserID());
		}
	}
}
