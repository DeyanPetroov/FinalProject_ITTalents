package model.dao;

import java.util.List;

import model.Product;

public interface IProductDAO {
	
	void addProduct(Product product) throws Exception;
	void deleteProduct(int product_id) throws Exception;
	void updateProduct(Product product) throws Exception;
	Product getProductById(int product_id) throws Exception;
	List<Product> getProductsByCategory(int category_id) throws Exception;
}
