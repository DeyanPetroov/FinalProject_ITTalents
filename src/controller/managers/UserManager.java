package controller.managers;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

import model.Cart;
import model.Product;
import model.User;
import model.dao.UserDAO;

public class UserManager implements IUserManager {

	private static UserManager instance;
	private UserDAO userDAO;

	private UserManager() {
		this.userDAO = UserDAO.getInstance();
	}

	public synchronized static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	public User login(String username, String password) {
		User user = null;
		try {
			user = this.userDAO.getLoggedUser(username, password);
		}
		catch(SQLException e) {
			e.getMessage();
		}
		return user;
	}
	
	public synchronized boolean register(String first_name, String last_name, String email, int age, String username, String password) {
		User u = new User(username, password, first_name, last_name, email, age);
		try {
			this.userDAO.saveUser(u);
			u.setPassword(u.hashPassword());
			return true;
		} catch (SQLException e) {
			e.getMessage();
		}
		return false;
	}

	//maybe this will become one method --> press a button --> add, unpress it --> remove
	@Override
	public void addOrRemoveFavourite(User user, Product product) {
		HashSet<Product> favouriteProducts = user.getFavouriteProducts();
		if(!favouriteProducts.contains(product)) {
			user.addToFavourites(product);
			try {
				this.userDAO.addProductToFavourites(user, product);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			//already in favourites -- > remove it
			user.removeFromFavourites(product);
			try {
				this.userDAO.removeProductFromFavourites(user, product);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public void addToCart(User user, Product product, int quantity) {
//		Cart cart = user.getCart();
//		Map<Product, Integer> productsInCart = cart.getProducts();
//		boolean addToCart = true;
//		for(Product p : productsInCart.keySet()) {
//			if(p.equals(product)) {
//				addToCart = false;
//				break;
//			}
//		}
//		
//		if(addToCart) {
			user.addToCart(product, quantity);
//		}
//		else {
//			//products should have quantity
//			//add the product with the desired quantity + the previous one
//		}
	}

	@Override
	public void removeFromCart(User user, Product product) {
		if(user.getCart().getProducts().containsKey(product)) {
			user.removeFromCart(product, user.getCart().getProducts().get(product));	
		}
		else {
			System.out.println("No such product in cart.");
		}
	}

	@Override
	public void makeOrder(User user) {
		// TODO Auto-generated method stub
		
	}
}
