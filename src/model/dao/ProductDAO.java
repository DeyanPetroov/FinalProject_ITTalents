package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.Product;

public class ProductDAO implements IProductDAO {
	
	private static final String INSERT_PRODUCT = "INSERT INTO products VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GET_PRODUCT_BY_ID = 
			"SELECT p.product_id, p.brand, p.model, p.description, p.price, c.category_name FROM products AS p" + 
			"JOIN categories AS" + 
			"ON p.category_id = c.category_id";
	private static final String UPDATE_PRODUCT = "UPDATE products SET brand = ?, model = ?, description = ?, price = ?, category_id = ?";
	private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE product_id = ?";
	private static final String GET_ALL_BY_CATEGORY = "SELECT * FROM products WHERE category_id = ?";
	
	private static ProductDAO instance;
	private Connection connection;
	
	public static synchronized ProductDAO getInstance() {
		if(instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}
	
	private ProductDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addProduct(Product product) throws Exception {
		try(PreparedStatement p = connection.prepareStatement(INSERT_PRODUCT);) {
			p.setObject(1, null);
			p.setString(2, product.getBrand());
			p.setString(3, product.getModel());
			p.setString(4, product.getDescription());
			p.setDouble(4, product.getPrice());
			p.setInt(6, product.getCategory().getCategory_id());
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public void deleteProduct(int product_id) throws Exception {
		try(PreparedStatement p = connection.prepareStatement(DELETE_PRODUCT_BY_ID);){
			p.setInt(1, product_id);
			p.executeUpdate();
		}
		catch(Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		try(PreparedStatement p = connection.prepareStatement(UPDATE_PRODUCT);){
			p.setString(1, product.getBrand());
			p.setString(2, product.getModel());
			p.setString(3, product.getDescription());
			p.setDouble(4, product.getPrice());
			p.setInt(5, product.getCategory().getCategory_id());
			p.executeUpdate();
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}

	@Override
	public Product getProductById(int product_id) throws Exception {
		Product product = null;
		//TODO
		return product;
	}

	@Override
	public List<Product> getProductsByCategory(int category_id) throws Exception {
		List<Product> sameCategoryProducts = new ArrayList<>();
		try(PreparedStatement p = connection.prepareStatement(GET_ALL_BY_CATEGORY);){
			p.setInt(1, category_id);
			ResultSet result = p.executeQuery();
			while(result.next()) {
				//TODO get category
//				Product product = new Product(, result.getDouble("price"));
//				sameCategoryProducts.add(product);
			}
		}
		catch(SQLException e) {
			e.getMessage();
		}
		return sameCategoryProducts;
	}

}
