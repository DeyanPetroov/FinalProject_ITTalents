package people;

import shop.Demo;
import shop.Product;
import shop.Shop;

public class Administrator {

	private String nickname;
	private String password;
	private String username;

	public void setNickname(String nickname) {
		if (nickname != null && !nickname.isEmpty()) {
			this.nickname = nickname;
		}
	}

	public void setPassword(String password) {
		if (password != null && !password.isEmpty()) {
			this.password = password;
		}
	}
	
	public void setUsername(String username) {
		if (username != null && !username.isEmpty()) {
			this.username = username;
		}
	}
	
	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

//	boolean isAdmin() {
//		return true;
//	}
	
	public void addProduct() {
		// shte sa skriti na stranicata AKO !isAdmin()
		// Product shte byde abstract
		// nishto nqma da e random, taka sa za Demoto
		Product p = new Product(Product.getRandomCategory(), Demo.random(10, 100));
		if (Shop.getInstance().getProducts().containsKey(p)) {
			Shop.getInstance().getProducts().put(p, Shop.getInstance().getProducts().get(p) + Demo.random(1, 100));
		} else {
			Shop.getInstance().getProducts().put(p, Demo.random(1, 100));
		}
	}

	public void removeProduct(Product p) {
		if (p != null) {
			if (Shop.getInstance().getProducts().containsKey(p)) {
				Shop.getInstance().getProducts().remove(p);
			}
		}
	}
}