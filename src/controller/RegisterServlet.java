package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		resp.sendRedirect("register.html");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String first_name = req.getParameter("first_name");
		String last_name = req.getParameter("last_name");
		Integer age = Integer.valueOf(req.getParameter("age"));
		
		if(UserDAO.getInstance().getAllUsers().containsKey(username)) {
			resp.sendRedirect("error.jsp"); //some error page
		}
		else {
			User u = new User(first_name, last_name, username, password, email, age);
			UserDAO.getInstance().saveUser(u);
			if(UserDAO.getInstance().passwordAndUsernameValidation(username, password)){				
				u = UserDAO.getInstance().getAllUsers().get(username);
				HttpSession session = req.getSession();
				session.setAttribute("user", u);
				session.setMaxInactiveInterval(3000);
				session.setAttribute("logged", true);
				resp.sendRedirect("profile.jsp");
			}
			else{
				resp.sendRedirect("errorLogin.html");
			}
		}		
		
	}

}
