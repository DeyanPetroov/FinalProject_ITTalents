package model.dao;

import java.util.List;

import model.User;
import shop.Order;

public interface IOrderDAO {
	
	void addNewOrder(Order order) throws Exception;
	void removeOrder(int order_id) throws Exception;
	Order getOrderById(long id) throws Exception;
	void updateOrderStatus(User user, int status_id) throws Exception;
	
}
