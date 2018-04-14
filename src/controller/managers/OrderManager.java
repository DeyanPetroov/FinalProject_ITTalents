package controller.managers;

import java.sql.SQLException;

import model.Order;
import model.User;
import model.dao.OrderDAO;

public class OrderManager implements IOrderManager {
	
	private static OrderManager instance;
	private OrderDAO orderDao;
	
	private OrderManager() {
		this.orderDao = OrderDAO.getInstance();
	}

	public static synchronized OrderManager getInstance() {
		if (instance == null) {
			instance = new OrderManager();
		}
		return instance;
	}

	@Override
	public Order createOrder(User user) {
		Order order = null;
		try {
			order = new Order(user);
			this.orderDao.addNewOrder(order);		
		}
		catch (SQLException e) {
			e.getMessage();
		}
		
		return order;
	}

}
