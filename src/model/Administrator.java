package model;

import shop.Demo;
import shop.Product;
import shop.Shop;

public class Administrator extends User {

	private String nickname;

	public Administrator(String nickname, String username, String password, String email, int age) {
		super(nickname, null, username, password, email, age);	
	}

	public void setNickname(String nickname) {
		if (nickname != null && !nickname.isEmpty()) {
			this.nickname = nickname;
		}
	}

//	boolean isAdmin() {
//		return true;
//	}
	
	public void addProduct(Product p) {
		// shte sa skriti na stranicata AKO !isAdmin()
		// Product shte byde abstract
		// nishto nqma da e random, taka sa za Demoto
		p.addProduct(Demo.random(10, 100));
		
	}

	public void removeProduct(Product p) {
		if (p != null) {
			if (Shop.getInstance().getProducts().containsKey(p)) {
				Shop.getInstance().getProducts().remove(p);
			}
		}
	}
}