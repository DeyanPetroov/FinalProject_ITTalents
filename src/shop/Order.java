package shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import model.User;

public class Order {

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private LocalDate date;
	private User user;
	private double totalCost;
	private int status = -1;	
	private Map<Product, Integer> products;
	
	public Order() {
		this.date = LocalDate.now();
		this.totalCost=0;
		this.status = 0;
		this.products = new TreeMap<Product, Integer>();
	}

	public void getStatus() {			
			switch(status) {
			case -1: System.out.println("No order present."); break;
			case 0: System.out.println("Your order is awaiting confirmation"); break;
			case 1: System.out.println("Your order has been confirmed and has been shipped."); break;
			}			
	}

	public LocalDate getDate() {
		return date;
	}	
	
	public User getUser() {
		return user;
	}
	

	public double getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(this.products);
	}

	public void setProducts(Map<Product, Integer> products) {
		this.products = products;
	}	
}