package model;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Cart {

	private User user;
	private TreeMap<Product, Integer> products = new TreeMap<Product, Integer>((Product p1, Product p2) -> {
		return p1.getName().compareTo(p2.getName());
	});

	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(products);
	}

	public double getTotalCost() {
		return user.getOrder().getTotalCost();
	}

	public void addToCart(Product p, int quantity) {
		// TODO: validate (ako ima nalichnost)
		// TODO: fix
		if (this.products.containsKey(p)) {
			System.out.println("contains");
			this.products.put(p, products.get(p) + quantity);
		} 
		else {
			this.products.put(p, quantity);
		}
		user.getOrder().setTotalCost(user.getOrder().getTotalCost() + p.getPrice() * quantity);
		System.out.println("You successfully added " + quantity + " of " + p.getName() + " to your cart!");
	}

	public void removeFromCart(Product p, int quantity) {
		if (this.products.containsKey(p)) {
			if (products.get(p) > quantity) {
				this.products.put(p, products.get(p) - quantity);
			} else {
				this.products.remove(p);
			}
		}
	}
	
	public void emptyCart() {
		this.products.clear();
	}

	public void setUser(User user) {
		this.user = user;
	}
}