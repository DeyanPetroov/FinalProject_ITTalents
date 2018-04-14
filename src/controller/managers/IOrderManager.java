package controller.managers;

import model.Order;
import model.User;

public interface IOrderManager {

	Order createOrder(User user);
}
