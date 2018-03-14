package shop;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import people.User;

public class Cart {

	private TreeMap<Product, Integer> products = new TreeMap<Product, Integer>((Product p1, Product p2) -> {
		return p1.getName().compareTo(p2.getName());
	});

	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(products);
	}

	private User user;

	public double getTotalCost() {
		return user.getOrder().getTotalCost();
	}

	public void addToCart(Product p, int quantity) {
		// TODO: validate (ako ima nalichnost)
		this.products.put(p, quantity); // TODO: fix
		user.getOrder().setTotalCost(user.getOrder().getTotalCost() + p.getPrice() * quantity);
		System.out.println("You successfully added " + quantity + " of " + p.getName() + " to your cart!");
	}

	public void removeFromCart(Product p, int quantity) {
		if (this.products.containsKey(p)) {
			if (products.get(p) > quantity) {
				this.products.put(p, products.get(p) - quantity);
			} 
			else {
				this.products.remove(p);
			}
		}
	}

	public void setUser(User user) {
		this.user = user;
	}
}