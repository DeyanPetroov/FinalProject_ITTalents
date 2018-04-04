package controller;


import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import model.User;
import dao.UserDao;

public class UserManager {
	
	private static UserManager instance;

	public static synchronized UserManager getInstance() {
		if(instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	private UserManager() {
		
	}
	
	public Collection<User> getAll(){
		try {
			return UserDao.getInstance().getAllUsers();
		} catch (SQLException e) {
			return Collections.EMPTY_LIST;
		}
	}
	
}
