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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User u) throws SQLException {
		PreparedStatement s = connection.prepareStatement("INSERT INTO users (first_name, last_name, email, age) VALUES (?,?,?,?)");
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
		PreparedStatement s = connection.prepareStatement("SELECT id, first_name, last_name, username, password, email, age FROM users");
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

