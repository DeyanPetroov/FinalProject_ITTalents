package people;

import java.util.ArrayList;
import java.util.Scanner;

import shop.Cart;
import shop.Order;
import shop.Product;

public class User {

	private String name;
	private int age;
	private String phone;
	private String username;
	private String password;
	private Cart cart=new Cart();
	private boolean logged;
	private Order order;
	private ArrayList<Order> orders = new ArrayList<>();

	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
		this.logged = false;
		cart.setUser(this);
	}

	public void order() {
		System.out.println("Do you want to finalize the order?");
		// case yes
		// move products from cart to order
		order = new Order();
		// remove products from cart
		orders.add(order);
	}

	// public boolean isAdmin() {
	// return false;
	// }

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public Cart getCart() {
		return cart;
	}

	public void setPhone(String phone) {
		if (phone != null && !phone.isEmpty()) {
			this.phone = phone;
		}
	}

	public void setAge(int age) {
		if (age > 0) {
			this.age = age;
		}
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public void setName(String name) {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		}
	}

	public void setUsername(String username) {
		if (username != null && !username.isEmpty()) {
			this.username = username;
		}
	}

	public void setPassword(String password) {
		if (password != null && !password.isEmpty()) {
			this.password = password;
		}
	}

	public void addToCart(Product product, int quantity) {
		if (this.logged == true) {
			cart.addToCart(product, quantity);
		} else {
			System.out.println("Please log in.");
		}
	}

	public void removeFromCart(Product product, int quantity) {
		if (this.logged == true) {
			cart.removeFromCart(product, quantity);
		} else {
			System.out.println("Please log in.");
		}
	}

	public void changePassword() {
		System.out.println("Input your password: ");
		Scanner sc = new Scanner(System.in);
		String oldPassword = sc.nextLine();

		String newPassword = null;
		if (oldPassword.equals(this.password)) {
			System.out.println("Input a new password: ");
			newPassword = sc.nextLine();
			if (!newPassword.isEmpty()) {
				setPassword(newPassword);
			}
		}
	}

	// public void viewProfile() {
	// if (this.logged == true) {
	// // TODO view profile
	// }
	// }
}