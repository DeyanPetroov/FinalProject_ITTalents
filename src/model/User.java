package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import hashing.BCrypt;
import model.util.UserValidations;

public class User {

	private long user_id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String profilePictureURL;
	private String address;
	private int age;
	private boolean isAdmin;
	
	private Cart cart = new Cart();
	private Order order;
	
	private ArrayList<Order> orderHistory = new ArrayList<>();
	private HashSet<Product> favouriteProducts = new HashSet<>();

	public User(long id, String username, String password, String first_name, String last_name, String email, String phone, int age, String profilePicture, String address) {
		this(username, password, first_name, last_name, email, age);
		this.user_id = id;
		this.profilePictureURL = profilePicture;
		this.address = address;
	}

	//used for registration
	public User(String username, String password, String firstName, String lastName, String email, int age) {
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setAge(age);
		cart.setUser(this);
	}

	// ----------GETTERS-----------

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
		return firstName;
	}

	public String getLastName() {
		return lastName;
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
	
	public String getAddress() {
		return address;
	}
	
	public String getProfilePictureURL() {
		return profilePictureURL;
	}
	
	// -----------SETTERS-----------

	public void setFirstName(String firstName) {
		if (UserValidations.isValidName(firstName)) {
			this.firstName = firstName;
		}
		//TODO else throw exception
	}

	public void setLastName(String lastName) {
		if (UserValidations.isValidName(lastName)) {
			this.lastName = lastName;
		}
		//TODO else throw exception
	}

	public void setPhone(String phone) {
		if (UserValidations.isValidPhone(phone)) {
			this.phone = phone;
		}
		//TODO else throw exception

	}

	public void setEmail(String email) {
		if (UserValidations.isValidEmail(email)) {
			this.email = email;
		}
		//TODO else throw exception
	}

	public void setAge(int age) {
		if (age > 0) {
			this.age = age;
		}
	}

	public void setUsername(String username) {
		if (UserValidations.isValidUsername(username)) {
			this.username = username;
		}
		//TODO else throw exception
	}

	public boolean setPassword(String password) {
		if (UserValidations.isValidPassword(password)) {
			this.password = password;
			return true;
		}
		return false;
		// TODO else throw exception
	}
	
	public void setProfilePictureURL(String profilePictureURL) {
		try {
			if(UserValidations.isValidURL(profilePictureURL)) {
				this.profilePictureURL = profilePictureURL;
			}
		} catch (IOException e) {
			e.printStackTrace();
			//TODO else throw exception
		}
	}

	// -------------METHODS-----------

	public void addToCart(Product product, int quantity) {
		cart.addToCart(product, quantity);
	}

	public void removeFromCart(Product product, int quantity) {
		cart.removeFromCart(product, quantity);
	}
	
	public String hashPassword() {
		return BCrypt.hashpw(this.password, BCrypt.gensalt());
	}

//	public void changePassword() {
//		System.out.println("Input your password: ");
//		Scanner sc = new Scanner(System.in);
//		String oldPassword = sc.nextLine();
//
//		String newPassword = null;
//		if (oldPassword.equals(this.password)) {
//			System.out.println("Input a new password: ");
//			newPassword = sc.nextLine();
//			if (!newPassword.isEmpty()) {
//				setPassword(newPassword);
//			}
//		}
//	}

//	public void order() {
//		String answer = null;
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Do you want to finalize the order?");
//		answer = sc.next();
//		sc.close();
//
//		switch (answer) {
//		case "yes":
//			this.order = new Order();
//			// move products from cart to order
//			Map<Product, Integer> products = cart.getProducts();
//			order.setProducts(products);
//			// remove all products from cart
//			cart.emptyCart();
//			orderHistory.add(order);
//		}
//	}

	public void addToFavourites(Product product) {
		if (!this.favouriteProducts.contains(product)) {
			this.favouriteProducts.add(product);
		}
	}
	
	public void removeFromFavourites(Product product) {
		if (this.favouriteProducts.contains(product)) {
			this.favouriteProducts.remove(product);
		}
	}
	
	public HashSet<Product> getFavouriteProducts() {
		return (HashSet<Product>) Collections.unmodifiableSet(this.favouriteProducts);
	}
}