package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hashing.BCrypt;
import model.*;
import model.exceptions.WrongUserDataException;

public class UserDAO implements IUserDAO {

	private static final String GET_EXISTING_USER_BY_EMAIL = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE email = ?";
	private static final String GET_EXISTING_USER_BY_USERNAME = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE username = ?";
	private static final String GET_USER_BY_USERNAME = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE username = ?";
	private static final String INSERT_USER = "INSERT INTO users (username, password, first_name, last_name, email, age) VALUES (?,?,?,?,?,?)";
	private static final String GET_USER_BY_ID = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE user_id = ?";
	private static final String GET_ALL_USERS = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users";
	private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone = ?, age = ?, profile_picture = ?, address = ? WHERE user_id = ?";
	private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
	private static final String CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE user_id = ?";
	private static final String GET_ORDERS_FOR_USER = "SELECT date, total_cost, status_id FROM orders WHERE user_id = ?";
	private static final String INSERT_PRODUCT_INTO_FAVOURITES = "INSERT INTO favouriteproducts (user_id, product_id) VALUES (?,?)";
	private static final String REMOVE_FROM_FAVOURITES = "DELETE FROM favouriteproducts WHERE user_id = ? AND product_id = ?";

	private static UserDAO instance;
	private Connection connection;
	private static final HashMap<String, User> allUsers = new HashMap<>();

	public static synchronized UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	private UserDAO() {
		connection = DBManager.getInstance().getConnection();
	}

	@Override
	public User getByID(long id) throws SQLException {
		ResultSet resultSet = null;
		User user = null;

		try (PreparedStatement selectUserById = connection.prepareStatement(GET_USER_BY_ID);) {
			selectUserById.setLong(1, id);
			resultSet = selectUserById.executeQuery();
			while (resultSet.next()) {
				user = new User(
						resultSet.getLong("user_id"),
						resultSet.getString("username"), 
						resultSet.getString("password"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						resultSet.getString("phone"), 
						resultSet.getInt("age"),
						resultSet.getString("profile_picture"),
						resultSet.getString("address"));
			}
		}
		resultSet.close();
		return user;
	}

	// insert user in the database -------> maybe synchronized ?
	@Override
	public void saveUser(User u) throws SQLException {
		try (PreparedStatement s = connection.prepareStatement(INSERT_USER);) {
			s.setString(1, u.getUsername());
			s.setString(2, u.hashPassword());
			s.setString(3, u.getFirstName());
			s.setString(4, u.getLastName());
			s.setString(5, u.getEmail());
			s.setInt(6, u.getAge());
			s.executeUpdate();
		}
	}

	// update user in the database
	@Override
	public void updateUser(User u) throws SQLException {
		try (PreparedStatement update = connection.prepareStatement(UPDATE_USER);) {
			update.setString(1, u.getFirstName());
			update.setString(2, u.getLastName());
			update.setString(3, u.getEmail());
			update.setString(4, u.getPhone());
			update.setInt(5, u.getAge());
			update.setString(6, u.getProfilePictureURL());
			update.setString(7, u.getAddress());
			update.setLong(8, u.getId());
			update.executeUpdate();
		}
	}

	// get all orders for one user by user_id
	@Override
	public List<Order> getAllUserOrders(int user_id) throws SQLException {
		List<Order> userOrders = new ArrayList<>();
		try (PreparedStatement getOrders = connection.prepareStatement(GET_ORDERS_FOR_USER);) {
			getOrders.setInt(1, user_id);
			ResultSet result = getOrders.executeQuery();
			while (result.next()) {
				User user = getByID(user_id);
				Order order = new Order(user);
				userOrders.add(order);
			}
		}
		return userOrders;
	}

	//get hashmap of all users
	@Override
	public HashMap<String, User> getAllUsers() throws SQLException {
		ResultSet resultSet = null;
		if (allUsers.isEmpty()) {
			try (PreparedStatement selectAllUsers = connection.prepareStatement(GET_ALL_USERS);) {
				resultSet = selectAllUsers.executeQuery();
				while (resultSet.next()) {
					User u = new User(
							resultSet.getLong("user_id"),
							resultSet.getString("username"), 
							resultSet.getString("password"),
							resultSet.getString("first_name"),
							resultSet.getString("last_name"),
							resultSet.getString("email"),
							resultSet.getString("phone"), 
							resultSet.getInt("age"),
							resultSet.getString("profile_picture"),
							resultSet.getString("address"));
					allUsers.put(u.getUsername(), u);
				}
			}
		}
		resultSet.close();
		return allUsers;
	}

	//change password of user
	@Override
	public void changePassword(User u, String password) throws SQLException, WrongUserDataException {
		if (getExistingUser(u.getUsername(), password) != null) {
			try (PreparedStatement changePass = connection.prepareStatement(CHANGE_PASSWORD);) {
				changePass.setString(1, u.getUsername());
				changePass.setString(2, password);
				changePass.executeUpdate();
			}
		}
	}

	//delete user from the database
	@Override
	public void deleteUserById(User user) throws SQLException {
		try (PreparedStatement deleteUserById = connection.prepareStatement(DELETE_USER_BY_ID);) {
			deleteUserById.setLong(1, user.getId());
			deleteUserById.executeUpdate();
		}
	}

	//get User after validating password and username
	@Override
	public User getExistingUser(String username, String password) throws SQLException, WrongUserDataException {
		User user = null;
		try (PreparedStatement ps = connection.prepareStatement(GET_USER_BY_USERNAME);) {
			ps.setString(1, username);
			try (ResultSet result = ps.executeQuery()) {
				if (result.next()) {
					String hashedPassword = result.getString("password");
					if(BCrypt.checkpw(password, hashedPassword)) {
						user = getByID(result.getInt("user_id"));
					}
				}
			}
		}
		return user;
	}

	//search for an existing user in the database
	@Override
	public String userExists(String username, String email) throws SQLException {
		try(PreparedStatement usernameExists = connection.prepareStatement(GET_EXISTING_USER_BY_USERNAME);){
			usernameExists.setString(1, username);
			try(ResultSet result = usernameExists.executeQuery()){
				if(result.next()) {
					return username;
				}
			}
		}
		try(PreparedStatement emailExists = connection.prepareStatement(GET_EXISTING_USER_BY_EMAIL);){
			emailExists.setString(1, email);
			try(ResultSet result = emailExists.executeQuery()){
				if(result.next()) {
					return email;
				}
			}
		}
		return null;
	}

	// add product to favourites in the database
	@Override
	public void addProductToFavourites(User user, Product product) throws SQLException {
		try (PreparedStatement addToFav = connection.prepareStatement(INSERT_PRODUCT_INTO_FAVOURITES);) {
			addToFav.setLong(1, user.getId());
			addToFav.setLong(1, product.getProduct_id());
			addToFav.executeUpdate();
		}
	}

	//remove product from favourites
	@Override
	public void removeProductFromFavourites(User user, Product product) throws SQLException {
		try(PreparedStatement removeFromFav= connection.prepareStatement(REMOVE_FROM_FAVOURITES);){
			removeFromFav.setLong(1, user.getId());
			removeFromFav.setLong(2, product.getProduct_id());
			removeFromFav.executeUpdate();
		}
	}
}