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
	private static final String NEW_ORDER = "INSERT INTO orders (date, total_cost, delivery_address, user_id, status_id) VALUES (?,?,?,?,?)"; 
	private static final String GET_ORDER_BY_ID = 
			"SELECT o.order_id, o.date, o.total_cost, o.delivery_address, CONCAT(u.first_name, ' ', u.last_name) AS Name, s.status_description" + 
			"FROM orders AS o" +  
			"JOIN statuses AS s" + 
			"ON s.status_id = o.status_id" + 
			"JOIN users AS u" + 
			"ON o.user_id = u.user_id AND o.order_id  = ?";
	private static final String GET_USER_BY_ORDER_ID = 
			"SELECT u.username, u.password, u.first_name, u.last_name, u.email, u.age " + 
			"FROM users as u" + 
			"JOIN orders as o" + 
			"WHERE o.order_id = ?";
	
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
			insertOrder.setObject(1, order.getDate());
			insertOrder.setDouble(2, order.getTotalCost());
			insertOrder.setString(3, order.getDeliveryAddress());
			insertOrder.setLong(4, order.getUser().getId());
			insertOrder.setInt(5, order.getStatus());
		}
	}

	@Override
	public Order getOrderById(long id) throws SQLException {
		ResultSet resultSet = null;
		Order order = null;
		
		try(PreparedStatement getOrderById = connection.prepareStatement(GET_ORDER_BY_ID);) {
			getOrderById.setLong(1, id);
			resultSet = getOrderById.executeQuery();
			while (resultSet.next()) {
				User user = getUserByOrderId(id);
				order = new Order(user);
			}
		}
		
		return order;
	}

	@Override
	public void removeOrder(long order_id) throws SQLException {
		try(PreparedStatement removeOrder = connection.prepareStatement(DELETE_ORDER_BY_ID);){
			removeOrder.setLong(1, order_id);
			removeOrder.executeUpdate();
		}
	}

	@Override
	public void updateOrderStatus(User user, int status_id) throws SQLException {
		try (PreparedStatement updateOrder = connection.prepareStatement(UPDATE_ORDER_STATUS_FOR_USER);) {
			updateOrder.setInt(1, status_id);
			updateOrder.setLong(2, user.getId());
			updateOrder.executeUpdate();
		}
	}
	
	@Override
	public User getUserByOrderId(long order_id) throws SQLException {
		User user = null;
		try (PreparedStatement getUser = connection.prepareStatement(GET_USER_BY_ORDER_ID);) {
			getUser.setLong(1, order_id);
			try (ResultSet result = getUser.executeQuery();) {

				while (result.next()) {
					user = new User(
							result.getString("username"), 
							result.getString("column"),
							result.getString("first_name"), 
							result.getString("last_name"), 
							result.getString("email"),
							result.getInt("age"));
				}
			}
		}
		return user;
	}
}