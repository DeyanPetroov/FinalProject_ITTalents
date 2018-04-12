package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import shop.Order;
import shop.Product;

public class OrderDAO implements IOrderDAO {
	
	private static final String UPDATE_ORDER_STATUS_FOR_USER = "UPDATE orders SET status_id = ? WHERE user_id = ?";
	private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
	private static final String NEW_ORDER = "INSERT INTO orders (user_id, date, total_cost, status_id) VALUES (?,?,?,?)"; 
	private static final String GET_ORDER_BY_ID = 
			"SELECT o.order_id, CONCAT(u.first_name, ' ', u.last_name) AS Name, o.date, o.total_cost, s.status_description" + 
			"FROM orders AS o" + 
			"JOIN users AS u" + 
			"ON o.user_id = u.user_id AND o.order_id  = ? " + 
			"JOIN statuses AS s" + 
			"ON s.status_id = o.status_id";
	
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
	public void addNewOrder(Order order) throws Exception {
		try(PreparedStatement insertOrder = connection.prepareStatement(NEW_ORDER);){
			insertOrder.setLong(1, order.getUser().getId());
			insertOrder.setObject(2, order.getDate());
			insertOrder.setDouble(3, order.getTotalCost());
			insertOrder.setString(4, order.getStatus());
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public Order getOrderById(long id) throws Exception {
		ResultSet resultSet = null;
		Order order = null;
		
		try(PreparedStatement getOrderById = connection.prepareStatement(GET_ORDER_BY_ID);) {
			getOrderById.setLong(1, id);
			resultSet = getOrderById.executeQuery();
			while (resultSet.next()) {
				//TODO maybe change the order constructor
				order = new Order();
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		
		return order;
	}

	@Override
	public void removeOrder(int order_id) throws Exception {
		try(PreparedStatement removeOrder = connection.prepareStatement(DELETE_ORDER_BY_ID);){
			removeOrder.setInt(1, order_id);
			removeOrder.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public void updateOrderStatus(User user, int status_id) throws Exception {
		try(PreparedStatement updateOrder = connection.prepareStatement(UPDATE_ORDER_STATUS_FOR_USER);){
			updateOrder.setInt(1, status_id);
			updateOrder.setLong(2, user.getId());
			updateOrder.executeUpdate();	
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}
}