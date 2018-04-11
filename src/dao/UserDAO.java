package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import model.User;

public class UserDAO implements IUserDAO {
	
	private static final String INSERT_USER = "INSERT INTO users (first_name, last_name, username, password, email, age) VALUES (?,?,?,?,?,?)";
	private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
	private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, username, password, email, age FROM users";
	private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, age = ?, WHERE id = ?";
	private static final String DELETE_USER_BY_ID = "DELETE from users WHERE id = ?";
	private static final String CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
	private static final String SEARCH_USER = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
	
	private static UserDAO instance;
	private Connection connection;
	private static final HashMap<String, User> allUsers = new HashMap<>();
	
	public static synchronized UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	private UserDAO() {
		connection = DBManager.getInstance().getConnection();
	}

	@Override
	public User getByID(long id) {
		ResultSet resultSet = null;
		User user = null;
		
		try(PreparedStatement selectUserById = connection.prepareStatement(GET_USER_BY_ID);) {
			selectUserById.setLong(1, id);
			resultSet = selectUserById.executeQuery();
			while (resultSet.next()) {
				user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5), resultSet.getInt(6));
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return user;
	}

	//maybe synchronized ?
	@Override
	public void saveUser(User u) {
		try (PreparedStatement s = connection.prepareStatement(INSERT_USER);) {
			s.setString(1, u.getFirstName());
			s.setString(2, u.getLastName());
			s.setString(3, u.getUsername());
			s.setString(4, u.getPassword());
			s.setString(5, u.getEmail());
			s.setInt(6, u.getAge());
			s.executeUpdate();
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public void updateUser(User u) throws SQLException {
		//not sure about this method
	/*	getAllUsers().remove(u);
		PreparedStatement s = connection.prepareStatement(UPDATE_USER);
		s.setString(1, u.getFirstName());
		s.setString(2, u.getLastName());
		s.setString(3, u.getEmail());
		s.setInt(4, u.getAge());
		s.setLong(5, u.getId());
		s.executeUpdate();
		getAllUsers().add(u);*/
	}

	@Override
	public HashMap<String, User> getAllUsers() {
		
		ResultSet result = null;

		try (PreparedStatement selectAllUsers = connection.prepareStatement(GET_ALL_USERS);) {
			result = selectAllUsers.executeQuery();
			while (result.next()) {
				User u = new User(
						result.getInt("id"), 
						result.getString("first_name"), 
						result.getString("last_name"),
						result.getString("username"),
						result.getString("password"),
						result.getString("email"),
						result.getInt("age"));
				allUsers.put(u.getUsername(), u);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return allUsers;
	}

	@Override
	public void changePassword(User u, String password) {
		if(passwordAndUsernameValidation(u.getUsername(), password)) {
			try(PreparedStatement changePass = connection.prepareStatement(CHANGE_PASSWORD);){
				changePass.setString(1, u.getUsername());
				changePass.setString(2, password);
				changePass.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteUserById(long id) {	
		try (PreparedStatement deleteUserById = connection.prepareStatement(DELETE_USER_BY_ID);) {
			deleteUserById.setLong(1, id);
			deleteUserById.executeUpdate();	
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public boolean passwordAndUsernameValidation(String username, String password) {
		try(PreparedStatement searchUser = connection.prepareStatement(SEARCH_USER);) {
			searchUser.setString(1, username);
			searchUser.setString(2, password);
			ResultSet result = searchUser.executeQuery();
			if(result.getInt(1) == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return false;
	}
}