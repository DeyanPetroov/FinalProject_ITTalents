package model.dao;

import model.User;
import model.*;

public interface IOrderDAO {
	
	void addNewOrder(Order order) throws Exception;
	void removeOrder(long order_id) throws Exception;
	Order getOrderById(long order_id) throws Exception;
	void updateOrderStatus(User user, int status_id) throws Exception;
	User getUserByOrderId(long user_id) throws Exception;	
}
