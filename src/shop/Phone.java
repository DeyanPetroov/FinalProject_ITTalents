package shop;

public class Phone extends Product {

	public Phone(double price) {
		super(Category.PHONES, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new Phone(quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}

	}

}
