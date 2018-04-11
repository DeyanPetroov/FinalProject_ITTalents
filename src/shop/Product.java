package shop;

import java.util.Scanner;

public abstract class Product {
	
	private static int UNIQUE_ID = 1;

	public enum Category {
		PHONES(1), COMPUTERS(2), TV(3), PHOTO(4);
		
		private int category_id;
		
		private Category(int id) {
			this.category_id = id;
		}
		
		public int getCategory_id() {
			return category_id;
		}
	}
	
	private final long product_id;
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
		this.product_id = UNIQUE_ID++;
	}

	public abstract void addProduct(int quantity);
	
	//==============SETTERS===================
	
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
	
	public void setDescription(String description) {
		if (description != null && !description.isEmpty()) {
			this.description = description;
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
	
	//==============GETTERS=====================
	
	public String getModel() {
		return model;
	}
	
	public String getBrand() {
		return brand;
	}

	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}
	
	public long getProduct_id() {
		return product_id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public static Category getCategoryById(int category_id) {
		switch (category_id) {
		case 1:
			return Category.PHONES;
		case 2:
			return Category.COMPUTERS;
		case 3:
			return Category.TV;
		case 4:
			return Category.PHOTO;
		}
		return null;
	}

	public static Category getRandomCategory() {
		Category[] categories = Category.values();
		return categories[Demo.random(0, categories.length - 1)];
	}
}