package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDAO;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("index.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if(UserDAO.getInstance().passwordAndUsernameValidation(username, password)){
			User u = UserDAO.getInstance().getAllUsers().get(username);
			HttpSession session = req.getSession();
			session.setAttribute("user", u);
			session.setMaxInactiveInterval(3000);
			session.setAttribute("logged", true);
			resp.sendRedirect("profile.jsp"); //TODO: create profile.jsp
		}
		else{
			resp.sendRedirect("errorLogin.html"); //TODO: create such a page
		}
	}

}
