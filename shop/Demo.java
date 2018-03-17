package shop;

import java.util.Random;

import people.Administrator;
import people.User;

public class Demo {

	public static int random(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}

	public static void main(String[] args) {

		Shop shop = Shop.getInstance("Emag");
			       
        shop.registrate();
        shop.logIn();
        User user = shop.getUsers().get(0);
        Administrator admin = new Administrator();
        admin.addProduct();
       
        //shte tyrsi izbran ot User-a produkt v magazina i shte go dobavq v kolichkata
        user.addToCart((Product) shop.getProducts().keySet().toArray()[0], 3);
        System.out.println(user.getCart().getTotalCost());
        user.order();

	}
}
