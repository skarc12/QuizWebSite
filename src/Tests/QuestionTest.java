package Tests;

import static org.junit.Assert.*;

import java.util.*;

import model.Question;
import model.Quiz;

import org.junit.*;

import Servlets.DBHelper;

public class QuestionTest {

	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQuestion2() throws Exception {
		DBHelper hlp = new DBHelper();
		Quiz quiz = hlp.getQuizByID(9);
		ArrayList<Question> questions = hlp.getQuestions(quiz.getID());
		System.out.println(questions.size());
		assertEquals(questions.size(), 1);
		for (int i = 0; i < questions.size(); i++) {
			Question question = questions.get(i);
		}
	}

}
