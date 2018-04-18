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
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private UserManager manager = UserManager.getInstance();
	private UserDAO userDAO = UserDAO.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		resp.sendRedirect("register.html");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String first_name = req.getParameter("first_name");
		String last_name = req.getParameter("last_name");
		String email = req.getParameter("email");

		Integer age = Integer.valueOf(req.getParameter("age"));

		try {
			if (userDAO.getByUsername(username) != null ) {
				resp.sendRedirect("error.jsp"); // some error page
			} 
			else {
				User u = new User(first_name, last_name, username, password, email, age);
				manager.register(first_name, last_name, email, age, username, password);

				u = this.manager.login(username, password);
				if (u != null) {
					HttpSession session = req.getSession();
					session.setAttribute("user", u);
					session.setMaxInactiveInterval(3000);
					session.setAttribute("logged", true);
					resp.sendRedirect("index.html");
				} 
				else {
					resp.sendRedirect("errorLogin.html");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
