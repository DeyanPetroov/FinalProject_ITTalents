package shop;

public class TV extends Product {

	public TV(double price) {
		super(Category.TV, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new TV(quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}

	}

}
