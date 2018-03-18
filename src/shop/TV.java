package shop;

public class TV extends Product {

	public TV(Category category, double price) {
		super(category, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new TV(Product.Category.TV, quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}

	}

}
