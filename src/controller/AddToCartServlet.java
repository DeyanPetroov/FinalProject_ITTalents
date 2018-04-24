package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.managers.UserManager;
import model.Product;
import model.User;
import model.dao.ProductDAO;
import model.exceptions.ProductNotInStockException;

@WebServlet("/buy")
public class AddToCartServlet extends HttpServlet {
       
	private UserManager manager = UserManager.getInstance();
	private ProductDAO productDAO = ProductDAO.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			Integer productID = Integer.valueOf(request.getParameter("productID"));
			Integer quantity = Integer.valueOf(request.getParameter("quantity"));
			Product product = productDAO.getProductById(productID);
			
			if(product!=null && product.getAvailability() >= quantity) {
				manager.addToCart(user, product, quantity);
			}
			else {
				if(product.getAvailability() < quantity) {
					throw new ProductNotInStockException();
				}
			}
		}
		catch(ProductNotInStockException e) {
			e.getMessage();
		} catch (SQLException e) {
			// TODO Global handler
			e.printStackTrace();
		}
	}

}
