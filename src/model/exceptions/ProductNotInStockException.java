package model.exceptions;

public class ProductNotInStockException extends Exception {

	@Override
	public String getMessage() {
		return "Sorry, this product is not available at the moment";
	}
}
