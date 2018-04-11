package dao;
import java.util.Collection;
import java.util.HashMap;

import model.User;

public interface IUserDAO {

	User getByID(long id) throws Exception;
	void saveUser(User u) throws Exception;
	void updateUser(User u) throws Exception;
	void changePassword(User u, String password) throws Exception;
	void deleteUserById(long id) throws Exception;
	boolean passwordAndUsernameValidation(String username, String password) throws Exception;
	HashMap<String, User> getAllUsers() throws Exception;
}
