package shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TreeMap;

public class Order {

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private LocalDate date;
	private double totalCost;
	private int status = -1;	
	private TreeMap<Product, Integer> products;
	
	public void finalize() {	
		//TODO
	}
	
	
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

	public double getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}


	public TreeMap<Product, Integer> getProducts() {
		return products;
	}


	public void setProducts(TreeMap<Product, Integer> products) {
		this.products = products;
	}
	

	
	
	
}