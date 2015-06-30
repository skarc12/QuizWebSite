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
		JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
		String action = obj.get("action").getAsString();
		if(action.equals("Unfriend")){
			user.removeFriend(obj.get("username").getAsString());
			DBHelper.unfriend(user, obj.get("username").getAsString());
			response.getOutputStream().print("Add Friend");
		} else if(action.equals("Add Friend")){
			user.addFriend(DBHelper.findUser(obj.get("username").getAsString()));
			DBHelper.addFriend(user,obj.get("username").getAsString());
			response.getOutputStream().print("Unfriend");
		} else if(action.equals("sendMessage")){
			System.out.println(obj.get("username").getAsString());
			System.out.println(obj.get("msg").getAsString());
			DBHelper.addMessage(user,obj.get("username").getAsString(), obj.get("msg").getAsString() );
		} else if(action.equals("sendChallenge")){
			obj.get("username").getAsString();
			obj.get("ownScore").getAsInt();
			obj.get("quizID").getAsInt();
			obj.get("message").getAsString();
			DBHelper.sendChallenge(user, obj.get("username").getAsString(), obj.get("ownScore").getAsInt(), obj.get("quizID").getAsInt(), obj.get("message").getAsString());
		}
	}

}
