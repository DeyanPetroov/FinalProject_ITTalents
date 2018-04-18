package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Order {
	
	private LocalDateTime date;
	private User user;
	private double totalCost;
	private int status = -1;
	private String statusDescription;
	private String deliveryAddress;
	private Map<Product, Integer> products;

	public Order(User user) {
		this.user = user;
		this.date = LocalDateTime.now();
		this.totalCost = 0;
		this.status = 0;
		this.products = new TreeMap<Product, Integer>();
		this.deliveryAddress = user.getAddress();
		setProducts(user.getCart().getProducts());
	}

	//==================GETTERS==================

	public LocalDateTime getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	public double getTotalCost() {
		return this.totalCost;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}


	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(this.products);
	}
	
	//==================SETTERS==================
	
	public void setStatusDescription() {
		switch (status) {
		case -1:
			this.statusDescription = "No order present.";
			break;
		case 0:
			this.statusDescription = "Order is awaiting confirmation";
			break;
		case 1:
			this.statusDescription = "Order has been confirmed and has been shipped.";
			break;
		case 2:
			this.statusDescription = "Order has been delivered";
			break;
		default:
			this.statusDescription = "Invalid status";
			break;
		}
	}

	public void setTotalCost(double totalCost) {
		if (totalCost >= 0) {
			this.totalCost = totalCost;
		}
	}
	
	public void setProducts(Map<Product, Integer> products) {
		this.products = products;
	}
}