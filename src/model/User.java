package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class User {

	private static final int MIN_PASSWORD_LENGTH = 5;
	private static final String PHONE_PATTERN = "^08[7-9][0-9]{7}$"; 
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	private static final String NAME_PATTERN = "^[a-zA-Z '.-]{3,31}$";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
											  + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private long user_id;
	private String first_name;
	private String last_name;	
	private String username;
	private String password;
	private String email;
	private String phone;
	private int age;
	
	private boolean logged;
	private Cart cart = new Cart();
	private Order order;
	private ArrayList<Order> orderHistory = new ArrayList<>();
	private HashSet<Product> favouriteProducts = new HashSet<>();
	
	public User(long id, String username, String password, String first_name, String last_name, String phone, int age) {		
		this(username, password, first_name, last_name, phone, age);
		this.user_id = id;
	}
	
	public User(String username, String password, String first_name, String last_name, String phone, int age) {
		setFirstName(first_name);
		setLastName(last_name);
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setAge(age);
		this.logged = false;
		cart.setUser(this);
	}

	// public boolean isAdmin() {
	// return false;
	// }
	
	//----------GETTERS-----------

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

	public String getEmail() {
		return email;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}

	public int getAge() {
		return age;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public long getId() {
		return user_id;
	}
	
	//-----------SETTERS-----------
	
	public void setFirstName(String first_name) {
		if (first_name.matches(NAME_PATTERN) && !first_name.isEmpty()) {
			this.first_name = first_name;
		}
	}

	public void setLastName(String last_name) {
		if (last_name.matches(NAME_PATTERN) && !last_name.isEmpty()) {
			this.last_name = last_name;
		}
	}
	
	public void setPhone(String phone) {
		if (phone.matches(PHONE_PATTERN)) {
			this.phone = phone;
		}
	}
	
	public void setEmail(String email) {
		if(email.matches(EMAIL_PATTERN) && !email.isEmpty()) {
			this.email = email;
		}
	}

	public void setAge(int age) {
		if (age > 0) {
			this.age = age;
		}
	}

	public void setUsername(String username) {
		if (username.matches(USERNAME_PATTERN) && !username.isEmpty()) {
			this.username = username;
		}
	}
	
	public boolean setPassword(String password) {
		if(password != null && !password.isEmpty() && password.length() >= MIN_PASSWORD_LENGTH) {
			this.password = password;
			return true;
		}
		return false;
	}
	
	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public void setName(String first_name, String last_name) {
		setFirstName(first_name);
		setLastName(last_name);
	}

	//-------------METHODS-----------

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
	
	public void order() {
		String answer = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you want to finalize the order?");
		answer = sc.next();
		sc.close();

		switch (answer) {
		case "yes":
			this.order = new Order();
			// move products from cart to order
			Map<Product, Integer> products = cart.getProducts();
			order.setProducts(products);
			// remove all products from cart
			cart.emptyCart();
			orderHistory.add(order);
		}
	}

	// public void viewProfile() {
	// if (this.logged == true) {
	// // TODO view profile
	// }
	// }
}