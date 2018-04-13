package controller;

import java.sql.SQLException;

import model.User;
import model.dao.UserDAO;

public class UserManager {

	private static UserManager instance;
	private UserDAO userDAO;

	private UserManager() {
		this.userDAO = UserDAO.getInstance();
	}

	public synchronized static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	public User login(String username, String password) {
		User user = null;
		try {
			user = this.userDAO.getLoggedUser(username, password);
		}
		catch(SQLException e) {
			//TODO
			e.getMessage();
		}
		return user;
	}
	
	public synchronized boolean register(String first_name, String last_name, String email, int age, String username, String password) {
		User u = new User(username, password, first_name, last_name, email, age);
		System.out.println(u.getUsername() + " " + u.getPassword() + " " + u.getFirstName() + " " + u.getLastName() + " " + u.getEmail() + " " + u.getAge());
		try {
			this.userDAO.saveUser(u);
			return true;
		} catch (SQLException e) {
			//TODO 
			e.getMessage();
		}
		return false;
	}
}
