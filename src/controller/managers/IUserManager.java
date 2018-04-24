package controller.managers;

import model.Product;
import model.User;

public interface IUserManager {

	User login(String username, String password) throws Exception;
	public boolean register(String firstNname, String lastNname, String email, int age, String username, String password);
	void addOrRemoveFavourite(User user, Product product);
	void addToCart(User user, Product product, int quantity);
	void removeFromCart(User user, Product product);
	void makeOrder(User user);
}
