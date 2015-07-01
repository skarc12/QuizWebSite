package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class userManager
 */
@WebServlet("/userManager")
public class userManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userManager() {
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
		User user =(User) request.getSession().getAttribute("user");
		String json = addQuiz.readAll(request.getInputStream());
		System.out.println("es aris json string    "+json);
		JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
		String action = obj.get("action").getAsString();
		String username = obj.get("username").getAsString();
		if(action.equals("Unfriend")){
			user.removeFriend(obj.get("username").getAsString());
			DBHelper.unfriend(user, obj.get("username").getAsString());
			response.getOutputStream().print("Add Friend");
		} else if(action.equals("Add Friend")){
			user.addFriend(DBHelper.findUser(username));
			DBHelper.addFriend(user,username);
			response.getOutputStream().print("Unfriend");
		} else if(action.equals("sendMessage")){
			System.out.println(username);
			System.out.println(obj.get("msg").getAsString());
			DBHelper.addMessage(user,username, obj.get("msg").getAsString() );
		} else if(action.equals("sendChallenge")){
			obj.get("username").getAsString();
		//	obj.get("ownScore").getAsInt();
			obj.get("quizID").getAsInt();
			obj.get("message").getAsString();
			DBHelper.sendChallenge(user, username, /*obj.get("ownScore").getAsInt()*/2, obj.get("quizID").getAsInt(), obj.get("message").getAsString());
		}
	}

}
