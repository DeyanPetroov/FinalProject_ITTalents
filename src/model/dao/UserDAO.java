package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import model.*;

public class UserDAO implements IUserDAO {
	
	private static final String INSERT_USER = "INSERT INTO users (username, password, first_name, last_name, email, age) VALUES (?,?,?,?,?,?)";
	private static final String GET_USER_BY_ID = "SELECT user_id, username, password, first_name, last_name, email, age FROM users WHERE user_id = ?";
	private static final String GET_ALL_USERS = "SELECT user_id, username, password, first_name, last_name, email, age FROM users";
	private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, age = ?, WHERE id = ?";
	private static final String DELETE_USER_BY_ID = "DELETE from users WHERE user_id = ?";
	private static final String CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE user_id = ?";
	private static final String SEARCH_USER = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
	private static final String GET_ORDERS_FOR_USER = "SELECT date, total_cost, status_id FROM orders WHERE user_id = ?";
	
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
				user = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("phone"), resultSet.getInt("age"));
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
			s.setString(1, u.getUsername());
			s.setString(2, u.getPassword());
			s.setString(3, u.getFirstName());
			s.setString(4, u.getFirstName());
			s.setString(5, u.getEmail());
			s.setInt(6, u.getAge());
			s.executeUpdate();
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public void updateUser(User u) throws SQLException {
		PreparedStatement update = connection.prepareStatement(UPDATE_USER);
		update.setString(1, u.getFirstName());
		update.setString(2, u.getLastName());
		update.setString(3, u.getEmail());
		update.setInt(4, u.getAge());
		update.setLong(5, u.getId());
		update.executeUpdate();
	}

	@Override
	public List<Order> getAllUserOrders(int user_id) throws Exception {
		List<Order> userOrders = new ArrayList<>();
		try(PreparedStatement getOrders = connection.prepareStatement(GET_ORDERS_FOR_USER);){
			getOrders.setInt(1, user_id);
			ResultSet result = getOrders.executeQuery();
			while(result.next()) {
				Order order = new Order();
				userOrders.add(order);
			}
		}
		catch(SQLException e) {
			e.getMessage();
		}
		return userOrders;	
	}
	
	@Override
	public HashMap<String, User> getAllUsers() {
		
		ResultSet result = null;
		if(allUsers.isEmpty()){
			try (PreparedStatement selectAllUsers = connection.prepareStatement(GET_ALL_USERS);) {
				result = selectAllUsers.executeQuery();
				while (result.next()) {
					User u = new User(
							result.getInt("id"), 
							result.getString("username"), 
							result.getString("password"),
							result.getString("first_name"),
							result.getString("last_name"),
							result.getString("email"),
							result.getInt("age"));
					allUsers.put(u.getUsername(), u);
				}
			} catch (SQLException e) {
				e.getMessage();
			}
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