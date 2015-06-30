package Tests;


import static org.junit.Assert.*;

import java.util.*;

import model.Quiz;
import model.Quiz.QuizHandle;
import model.User;

import org.junit.*;

import Servlets.DBHelper;

public class UserTest {

	
	
	@Test
	public void testUser1() {
		DBHelper man = new DBHelper();
		User sal = man.findUser("salome");
		ArrayList<User> manFriends = man.getFriends(sal);
		
		assertEquals(manFriends.size(), 0);
		
		QuizHandle[] takenRecords = man.getAllQuizes(sal);
		assertEquals(takenRecords.length, 0);
		
		Quiz[] q = man.getAllQsCreatedByUser(sal);
		System.out.println(q.length);
		assertEquals(q.length, 12);
		
		User tamta = man.findUser("tamta");
		
		man.addFriend(tamta,sal.getUsername());
		manFriends = man.getFriends(tamta);
		assertEquals(manFriends.size(), 1);
		
		man.unfriend(sal, tamta.getUsername());
		ArrayList<User> googleFriends = man.getFriends(sal);
		assertEquals(googleFriends.size(), 0);
	
	}

}
