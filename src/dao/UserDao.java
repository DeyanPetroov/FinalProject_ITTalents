package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import model.User;

public class UserDao implements IUserDao {
	
	private static final String INSERT_USER_QUERY = "INSERT INTO users (first_name, last_name, email, age) VALUES (?,?,?,?)";
	private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
	private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, username, password, email, age FROM users";

	private static UserDao instance;
	private Connection connection;
	
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	private UserDao() {
		connection = DBManager.getInstance().getConnection();
	}

	@Override
	public User getByID(int id) throws SQLException {
		ResultSet resultSet = null;
		User user = null;
		try(PreparedStatement selectUserById = connection.prepareStatement(GET_USER_BY_ID);) {
			selectUserById.setInt(1, id);
			resultSet = selectUserById.executeQuery();
			while (resultSet.next()) {
				user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5), resultSet.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void saveUser(User u) throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_USER_QUERY);
		s.setString(1, u.getFirstName());
		s.setString(2, u.getLastName());
		s.setString(3, u.getEmail());
		s.setInt(4, u.getAge());
		s.executeUpdate();
	}

	@Override
	public void updateUser(User u) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<User> getAllUsers() throws SQLException {
		PreparedStatement s = connection.prepareStatement(GET_ALL_USERS);
		HashSet<User> users = new HashSet<>();
		ResultSet result = s.executeQuery();
		while(result.next()) {
			User u = new User(	result.getInt("id"),
								result.getString("first_name"),
								result.getString("last_name"),
								result.getString("username"),
								result.getString("password"),
								result.getString("email"),
								result.getInt("age"));
			users.add(u);
		}
		return users;
	}
}