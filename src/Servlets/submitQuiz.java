package Servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FillTheGapsQuestion;
import model.MultipleChoiceQuestion;
import model.PictureQuizQuestion;
import model.QuestinAnswerQuestion;
import model.Question;
import model.Quiz;
import model.User;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class submitQuiz
 */
@WebServlet("/submitQuiz")
public class submitQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public submitQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		long time = System.currentTimeMillis();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(time);

		User user =(User) request.getSession().getAttribute("user");
		String json = addQuiz.readAll(request.getInputStream());
		System.out.println(json);
		JsonArray answers = new JsonParser().parse(json).getAsJsonArray();
		Quiz quiz = (Quiz)request.getSession().getAttribute("quiz");
		List<Question> questions = quiz.getQuestions();
		int correct = 0;
		for(int i=0; i<questions.size(); i++){
			correct += questions.get(i).checkAnswer(answers.get(i));
		}
//		int all = 0;
//		for(int i=0; i<questions.size(); i++){
//			switch(questions.get(i).getType()){
//				case  QUESTION_ANSWER:{
//					QuestinAnswerQuestion q = (QuestinAnswerQuestion)questions.get(i);
//					if(answers.get(i).getAsString().equalsIgnoreCase(q.getAnswer())){
//						correct++;
//					}
//					all++;
//					break;
//				}
//				case MULTIPLE_CHOICE:{
//					MultipleChoiceQuestion q = (MultipleChoiceQuestion)questions.get(i);
//					if(answers.get(i).getAsString().equalsIgnoreCase(q.getCorrectAnswer())){
//						correct++;
//					}
//					all++;
//					break;
//				}
//				case PICTURE_QUIZ:{
//					PictureQuizQuestion q = (PictureQuizQuestion)questions.get(i);
//					if(answers.get(i).getAsString().equalsIgnoreCase(q.getAnswer())){
//						correct++;
//					}
//					all++;
//					break;
//				}
//				case FILL_THE_GAPS:{
//					FillTheGapsQuestion q = (FillTheGapsQuestion)questions.get(i);
//					JsonArray innerAnswers = answers.get(i).getAsJsonArray();
//					for(int j=0; j<innerAnswers.size(); j++){
//						if(q.getAnswers()[j].equalsIgnoreCase(innerAnswers.get(j).getAsString())){
//							correct++;
//						}
//						all++;
//					}
//					break;
//				}
//			}
//		}
		DBHelper.playQuiz(user, quiz, correct, timestamp);
		response.getOutputStream().print("{\"value\": \"correct "+correct+"\", \"url\": \"home.jsp\"}");
		response.getOutputStream().flush();
	}

}
