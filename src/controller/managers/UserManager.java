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
			System.out.println("vliza");
		}
		catch(SQLException e) {
			//TODO
			System.out.println("vliza ama losho");
			e.getMessage();
		}
		return user;
	}
	
	public synchronized boolean register(String first_name, String last_name, String email, int age, String username, String password) {
		User u = new User(username, password, first_name, last_name, email, age);
		System.out.println(u.getUsername() + " " + u.getPassword() + " " + u.getFirstName() + " " + u.getLastName() + " " + u.getEmail() + " " + u.getAge());
		try {
			this.userDAO.saveUser(u);
			return true;
		} catch (SQLException e) {
			e.getMessage();
		}
		return false;
	}

	//maybe this will become one method --> press a button --> add, unpress it --> remove
	@Override
	public void addToFavourites(User user, Product product) {
		HashSet<Product> favouriteProducts = user.getFavouriteProducts();
		if(!favouriteProducts.contains(product)) {
			user.addToFavourites(product);
			//this.userDao.addToFavourites(product) -- > add to db 
		}
		else {
			//already in favourites print a message
		}
	}

	@Override
	public void removeFromFavourites(User user, Product product) {
		HashSet<Product> favouriteProducts = user.getFavouriteProducts();
		if(favouriteProducts.contains(product)) {
			user.removeFromFavourites(product);
			//this.userDao.removeFromFavourites(product) -- > add to db 
		}
		else {
			//not in favourites print a message
		}
	}

	@Override
	public void addToCart(User user, Product product, int quantity) {
		Cart cart = user.getCart();
		Map<Product, Integer> productsInCart = cart.getProducts();
		boolean addToCart = true;
		for(Product p : productsInCart.keySet()) {
			if(p.equals(product)) {
				addToCart = false;
				break;
			}
		}
		
		if(addToCart) {
			user.addToCart(product, quantity);
		}
		else {
			//products should have quantity
			//add the product with the desired quantity + the previous one
		}
	}

	@Override
	public void removeFromCart(User user, Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeOrder(User user) {
		// TODO Auto-generated method stub
		
	}
}
