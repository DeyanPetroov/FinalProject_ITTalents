package shop;

public class Product {

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

	//now is null because of brand and model ---> should be fixed
	public void setName() {
		this.name = this.brand + this.model;
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