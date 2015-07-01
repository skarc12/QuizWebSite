package Tests;

import static org.junit.Assert.*;

import java.util.*;

import model.Quiz;
import model.User;

import org.junit.*;

import Servlets.DBHelper;


public class RecordTest {
	User stanford;
	User google;
	User facebook;

	
	@Test
	public void testTakenRecord() {
		DBHelper help =  new DBHelper();
		Quiz quiz = help.getQuizByID(12);
		assertEquals(quiz.getOwnes().getUserID(), 1);
		assertEquals(quiz.getQuizName(), "ege");
}

}
