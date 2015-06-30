package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Paroli;
import model.Quiz;
import model.User;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Paroli p = new Paroli();
		String username = request.getParameter("login");
		String password = request.getParameter("password");
		password = p.generateHashedPassword(password);
		DBHelper manager = new DBHelper();
		User user = manager.findUser(username);
		if(user == null){
			response.sendRedirect("noSuchUser.html");
		}else{
			if(password.equals(user.getPassword())){
				user.setFriends(DBHelper.getFriends(user));
				System.out.println("user id: "+user.getUserID());
				request.getSession().setAttribute("user", user);
				response.sendRedirect("home.jsp");
			}else{
				response.sendRedirect("incorrectPassword.html");
			}
		}
	}

}
