package shop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import people.User;

public class Shop {

	private String name;
	private TreeMap<Product, Integer> products;
	private ArrayList<User> users = new ArrayList<>();

	private static Shop instance;

	// Singleton
	private Shop(String name) {
		setName(name);
		this.products = new TreeMap<>((Product p1, Product p2) -> {
			if (p1.getName().equals(p2.getName())) {
				return 1;
			}
			return p1.getName().compareTo(p2.getName());
		});
	}

	public static Shop getInstance(String name) {
		if (Shop.instance == null) {
			Shop.instance = new Shop(name);
		}
		return Shop.instance;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public static Shop getInstance() {
		return instance;
	}

	public TreeMap<Product, Integer> getProducts() {
		return this.products;
	}

	public void setProducts(TreeMap<Product, Integer> products) {
		this.products = products;
	}

	public static void setInstance(Shop instance) {
		Shop.instance = instance;
	}

	public void logIn() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Username: ");
		String username = sc.next();
		System.out.println("Password: ");
		String password = sc.next();

		boolean logged = false;
		for (User user : this.users) {
			if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
				logged = true;
				user.setLogged(true);
			}
		}
		
		if(!logged) {
			System.out.println("Wrong username or password!");
		}
	}
	
	public void logOut(User user) {
		user.setLogged(false);
	}

	public void registrate() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Input your full name: ");
		String name = sc.nextLine();
		System.out.println("Input your phone: ");
		String phone = sc.nextLine();
		System.out.println("Input your age: ");
		int age = sc.nextInt();

		System.out.println("Username: ");
		String username = sc.next();
		System.out.println("Password(must be more than 5 symbols): ");
		String password = sc.next();

		User user = new User(username, password);
		while(!user.isValidPassword(password)) {
			System.out.println("Password(must be more than 5 symbols): ");
			password = sc.next();
		}		
		user.setPassword(password);
		
		if (!this.users.contains(user)) {
			user.setAge(age);
			user.setName(name);
			user.setPhone(phone);
			this.users.add(user);
		} 
		else {
			System.out.println("This user already exists. Please log in.");
		}
	}
	
	public void setName(String name) {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		} 
		else {
			this.name = "Emag";
		}
	}

	//TODO PRINT ALL BY CATEGORY??
	public void printAll() {
		for (Entry<Product, Integer> entry : products.entrySet()) {
			System.out.println("Product: " + entry.getKey().getName() + ", quantity: " + entry.getValue());
		}
	}
}