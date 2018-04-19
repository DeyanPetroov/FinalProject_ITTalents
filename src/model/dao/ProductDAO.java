package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class ProductDAO implements IProductDAO {
	
	private static final String INSERT_PRODUCT = "INSERT INTO products VALUES (?, ?, ?, ?, ?)";
	private static final String GET_PRODUCT_BY_ID = 
			"SELECT p.product_id, p.brand, p.model, p.description, p.price, c.category_name FROM products AS p" + 
			"JOIN categories AS c" + 
			"ON p.product_id = ? AND p.category_id = c.category_id";
	private static final String UPDATE_PRODUCT = "UPDATE products SET brand = ?, model = ?, description = ?, price = ?, category_id = ?";
	private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE product_id = ?";
	private static final String GET_ALL_BY_CATEGORY = "SELECT product_id, brand, model, description, price, category_id, FROM products WHERE category_id = ?";
	
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
	public void addProduct(Product product) throws SQLException {
		try(PreparedStatement p = connection.prepareStatement(INSERT_PRODUCT);) {
			p.setString(1, product.getBrand());
			p.setString(2, product.getModel());
			p.setString(3, product.getDescription());
			p.setDouble(4, product.getPrice());
			p.setInt(5, product.getCategory().getCategory_id());
		}
	}

	@Override
	public void deleteProduct(int product_id) throws SQLException {
		try(PreparedStatement p = connection.prepareStatement(DELETE_PRODUCT_BY_ID);){
			p.setInt(1, product_id);
			p.executeUpdate();
		}
	}

	@Override
	public void updateProduct(Product product) throws SQLException {
		try(PreparedStatement p = connection.prepareStatement(UPDATE_PRODUCT);){
			p.setString(1, product.getBrand());
			p.setString(2, product.getModel());
			p.setString(3, product.getDescription());
			p.setDouble(4, product.getPrice());
			p.setInt(5, product.getCategory().getCategory_id());
			p.executeUpdate();
		}
	}

	@Override
	public Product getProductById(int product_id) throws SQLException {
		Product product = null;
		try(PreparedStatement p = connection.prepareStatement(GET_PRODUCT_BY_ID);){
			p.setInt(1, product_id);
			ResultSet resultSet = p.executeQuery();
			while(resultSet.next()) {
				product = new Product().withCategory(Product.Category.valueOf(resultSet.getString("category_name"))).withPrice(resultSet.getDouble("price"));
			}
		}
		return product;
	}

	@Override
	public List<Product> getProductsByCategory(int category_id) throws SQLException {
		List<Product> sameCategoryProducts = new ArrayList<>();
		try(PreparedStatement p = connection.prepareStatement(GET_ALL_BY_CATEGORY);){
			p.setInt(1, category_id);
			ResultSet resultSet = p.executeQuery();
			while(resultSet.next()) {
				Product product = new Product().withCategory(Product.Category.valueOf(resultSet.getString("category_name"))).withPrice(resultSet.getDouble("price"));
				sameCategoryProducts.add(product);
			}
		}
		return sameCategoryProducts;
	}
}