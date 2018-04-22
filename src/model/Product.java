package model;

import java.util.Date;

public class Product {

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

	private long product_id;
	private Category category;
	private String name;
	private String brand;
	private String model;
	private String description;
	private String productImageURL;
	private double price;
	private int availability;
	private int discountPercent;
	private Date discountExpiration;

	// ==============BUILDER DESIGN PATTERN===================

	public Product withModel(String model) {
		if (model != null && !model.isEmpty()) {
			this.model = model;
		}
		return this;
	}

	public Product withBrand(String brand) {
		if (brand != null && !brand.isEmpty()) {
			this.brand = brand;
		}
		return this;
	}

	public Product withDescription(String description) {
		if (description != null && !description.isEmpty()) {
			this.description = description;
		}
		return this;
	}

	public Product withName(String name) {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		}
		return this;
	}
	
	public Product withProductId(long product_id) {
		if (product_id >= 0) {
			this.product_id = product_id;
		}
		return this;
	}

	public Product withCategory(Category category) {
		if (category != null) {
			this.category = category;
		}
		return this;
	}

	public Product withProductImageURL(String productImageURL) {
		if (productImageURL != null && !productImageURL.isEmpty()) {
			this.productImageURL = productImageURL;
		}
		return this;
	}

	public Product withPrice(double price) {
		if (price >= 0) {
			this.price = price;
		}
		return this;
	}

	public Product withAvailability(int availability) {
		if (availability >= 0) {
			this.availability = availability;
		}
		return this;
	}

	public Product withDiscountPercent(int discountPercent) {
		if (discountPercent >= 0) {
			this.discountPercent = discountPercent;
		}
		return this;
	}

	public Product withDiscountExpiration(Date discountExpiration) {
		this.discountExpiration = discountExpiration;
		return this;
	}

	// ==============GETTERS=====================

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
	
	public String getProductImageURL() {
		return productImageURL;
	}

	public int getAvailability() {
		return availability;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public Date getDiscountExpiration() {
		return discountExpiration;
	}

//	public static Category getCategoryById(int category_id) {
//		switch (category_id) {
//		case 1:
//			return Category.PHONES;
//		case 2:
//			return Category.COMPUTERS;
//		case 3:
//			return Category.TV;
//		case 4:
//			return Category.PHOTO;
//		}
//		return null;
//	}
}