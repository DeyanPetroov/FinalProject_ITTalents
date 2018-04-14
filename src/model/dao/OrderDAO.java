package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class OrderDAO implements IOrderDAO {
	
	private static final String UPDATE_ORDER_STATUS_FOR_USER = "UPDATE orders SET status_id = ? WHERE user_id = ?";
	private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
	private static final String NEW_ORDER = "INSERT INTO orders (user_id, date, total_cost, status_id) VALUES (?,?,?,?)"; 
	private static final String GET_ORDER_BY_ID = 
			"SELECT o.order_id, o.user_id , o.date, o.total_cost, s.status_description" + 
			"FROM orders AS o" +  
			"JOIN statuses AS s" + 
			"ON s.status_id = o.status_id AND o.order_id  = ? ";
	private static final String GET_USER_BY_ORDER_ID = 
			"SELECT u.username, u.password, u.first_name, u.last_name, u.email, u.age " + 
			"FROM users as u" + 
			"JOIN orders as o" + 
			"ON o.user_id = u.user_id where o.user_id = ?";
	
	private static OrderDAO instance;
	private Connection connection;
	
	public static synchronized OrderDAO getInstance() {
		if(instance == null) {
			instance = new OrderDAO();
		}
		return instance;
	}
	
	private OrderDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addNewOrder(Order order) throws SQLException {
		try(PreparedStatement insertOrder = connection.prepareStatement(NEW_ORDER);){
			insertOrder.setLong(1, order.getUser().getId());
			insertOrder.setObject(2, order.getDate());
			insertOrder.setDouble(3, order.getTotalCost());
			insertOrder.setString(4, order.getStatus());
		}
	}

	@Override
	public Order getOrderById(int id) throws SQLException {
		ResultSet resultSet = null;
		Order order = null;
		
		try(PreparedStatement getOrderById = connection.prepareStatement(GET_ORDER_BY_ID);) {
			getOrderById.setLong(1, id);
			resultSet = getOrderById.executeQuery();
			while (resultSet.next()) {
				User user = getByOrderId(id);
				order = new Order(user);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		
		return order;
	}

	@Override
	public void removeOrder(int order_id) throws SQLException {
		try(PreparedStatement removeOrder = connection.prepareStatement(DELETE_ORDER_BY_ID);){
			removeOrder.setInt(1, order_id);
			removeOrder.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public void updateOrderStatus(User user, int status_id) throws SQLException {
		PreparedStatement updateOrder = connection.prepareStatement(UPDATE_ORDER_STATUS_FOR_USER);
		updateOrder.setInt(1, status_id);
		updateOrder.setLong(2, user.getId());
		updateOrder.executeUpdate();
	}
	
	@Override
	public User getByOrderId(int user_id) throws SQLException {
		PreparedStatement getUser = connection.prepareStatement(GET_USER_BY_ORDER_ID);
		getUser.setInt(1, user_id);
		ResultSet result = getUser.executeQuery();
		
		User user = null;
		while(result.next()) {
			user = new User(result.getString("username"), 
							result.getString("column"),
							result.getString("first_name"),
							result.getString("last_name"),
							result.getString("email"),
							result.getInt("age"));
		}
		return user;
	}
}