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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(manager.login(username, password) != null){
			User u = null;
			try {
				u = userDAO.getByUsername(username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session = req.getSession();
			session.setAttribute("user", u);
			session.setMaxInactiveInterval(3000);
			session.setAttribute("logged", true);
			session.setMaxInactiveInterval(60*60);
			resp.sendRedirect("loggedPage.html"); //TODO: create profile.jsp
		}
		else{
			req.setAttribute("error","Invalid Username or Password");
			req.getRequestDispatcher("login.html").forward(req, resp);  
			resp.sendRedirect("errorLogin.html"); //TODO: create such a page
		}
	}

}