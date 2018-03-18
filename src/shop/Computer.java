package shop;

public class Computer extends Product {

	public Computer(Category category, double price) {
		super(category, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new Computer(Product.Category.COMPUTERS, quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}

	}

}
