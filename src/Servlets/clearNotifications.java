package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class clearNotifications
 */
@WebServlet("/clearNotifications")
public class clearNotifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public clearNotifications() {
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
		String json = addQuiz.readAll(request.getInputStream());
		JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
		String content = obj.get("action").getAsString();
		switch(content){
			case "messages":{
				JsonArray arr = obj.get("ids").getAsJsonArray();
				for(int i=0; i<arr.size(); i++){
					DBHelper.markMessageAsSeen(arr.get(i).getAsInt());
					
				}
			}
			case "challenges":{
				JsonArray arr = obj.get("ids").getAsJsonArray();
				System.out.println(arr);
				for(int i=0; i<arr.size(); i++){
					DBHelper.markChallengeAsSeen(arr.get(i).getAsInt());
				}
			}
			case "friendRequests":{
				JsonArray arr = obj.get("ids").getAsJsonArray();
				for(int i=0; i<arr.size(); i++){
					DBHelper.markRequestAsSeen(arr.get(i).getAsInt());
				}
			}
		}
	}

}
