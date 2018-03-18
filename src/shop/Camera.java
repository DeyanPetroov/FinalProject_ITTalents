package shop;

public class Camera extends Product {

	public Camera(double price) {
		super(Category.PHOTO, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new Camera(quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}
	}
}
