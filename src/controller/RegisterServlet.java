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
import model.User;
import model.dao.*;
import model.exceptions.ExistingUserException;
import model.exceptions.WrongUserDataException;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private UserManager manager = UserManager.getInstance();
	private UserDAO userDAO = UserDAO.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		resp.sendRedirect("register.html");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse respînse) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");

		Integer age = Integer.valueOf(request.getParameter("age"));

		try {
			String existingUserParameter = userDAO.userExists(username, email);
			if (existingUserParameter != null) {
				throw new ExistingUserException(existingUserParameter);
			} 
			else {
				User u = new User(first_name, last_name, username, password, email, age);
				manager.register(first_name, last_name, email, age, username, password);

				u = this.manager.login(username, password);
				if (u != null) {
					HttpSession session = request.getSession();
					session.setAttribute("user", u);
					session.setMaxInactiveInterval(3000);
					session.setAttribute("logged", true);
					respînse.sendRedirect("index.html");
				} 
				else {
					respînse.sendRedirect("errorLogin.html");
				}
			}
		}
		//TODO make an abstract exception handler
		catch(ExistingUserException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("register.jsp").forward(request, respînse);
		}
		catch(WrongUserDataException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, respînse);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
