package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.managers.UserManager;
import model.dao.UserDAO;
import model.exceptions.WrongUserDataException;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private UserManager manager = UserManager.getInstance();
	private UserDAO userDAO = UserDAO.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("index.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = null;
		try {
			user = manager.login(username, password);
			
			if(user!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("logged", true);
				session.setMaxInactiveInterval(60*60);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}	
			else {
				throw new WrongUserDataException();
			}
		}
		catch(WrongUserDataException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
}
}