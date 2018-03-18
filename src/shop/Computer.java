package shop;

public class Computer extends Product {

	public Computer(double price) {
		super(Category.COMPUTERS, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new Computer(quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}

	}

}
