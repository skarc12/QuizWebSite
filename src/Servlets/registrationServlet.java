package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class registrationServlet
 */
@WebServlet("/registrationServlet")
public class registrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.print("hom");
		
		 
		
		String name = request.getParameter("FirstName");
		String lastname = request.getParameter("LastName");
		String email = request.getParameter("Mail");
		String username = request.getParameter("UserName");
		String password = request.getParameter("Password");
		UserManager a =  new UserManager();
		User user = a.findUser(username);
		if(user == null){
			user = a.addUser(name, lastname, username, email, password);
			request.getSession().putValue("user", user);
			response.sendRedirect("home.jsp");
		}else{
			response.sendRedirect("nameInUse.html");
		}
	}

}
