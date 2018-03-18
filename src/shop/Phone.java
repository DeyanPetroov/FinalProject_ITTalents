package shop;

public class Phone extends Product {

	public Phone(Category category, double price) {
		super(category, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new Phone(Product.Category.PHONES, quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}

	}

}
