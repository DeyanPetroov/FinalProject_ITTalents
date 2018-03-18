package shop;

public class Camera extends Product {

	public Camera(Category category, double price) {
		super(category, price);
	}

	@Override
	public void addProduct(int quantity) {
		Product p = new Camera(Product.Category.PHOTO, quantity);
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + quantity);
		} else {
			Shop.getInstance().getProducts().put(p, quantity);
		}

	}

}
