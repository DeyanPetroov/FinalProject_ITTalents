package model.dao;

import model.User;
import model.*;

public interface IOrderDAO {
	
	void addNewOrder(Order order) throws Exception;
	void removeOrder(int order_id) throws Exception;
	Order getOrderById(int order_id) throws Exception;
	void updateOrderStatus(User user, int status_id) throws Exception;
	User getByOrderId(int user_id) throws Exception;	
}
