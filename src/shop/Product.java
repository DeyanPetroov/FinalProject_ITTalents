package shop;

import java.util.Scanner;

public abstract class Product {

	public enum Category {
		PHONES, COMPUTERS, TV, PHOTO
	}

	private Category category;
	private String name;
	private String brand;
	private String model;
	private String description;
	private double price;

	public Product(Category category, double price) {
		setName();
		if (price > 0) {
			this.price = price;
		}
		this.category = category;
	}

	public abstract void addProduct(int quantity);
	
	public void setModel(String model) {
		if (model != null && !model.isEmpty()) {
			this.model = model;
		}
	}

	public void setBrand(String brand) {
		if (brand != null && !brand.isEmpty()) {
			this.brand = brand;
		}
	}
	
	public void setName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Admin must enter brand and model: ");
		this.brand=sc.nextLine();
		this.model=sc.nextLine();
		this.name = this.brand + this.model;
		sc.close();
	}

	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public static Category getRandomCategory() {
		Category[] categories = Category.values();
		return categories[Demo.random(0, categories.length - 1)];
	}
}