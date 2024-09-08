package services;

import java.sql.SQLException;
import java.util.List;

import dao.ProductDAO;
import models.Product;

public class ProductService {
    private static ProductService instance;
    private ProductDAO productDAO;

    // Private constructor for Singleton pattern
    private ProductService() {
        productDAO = new ProductDAO();
    }

    // Singleton pattern to ensure only one instance of ProductService
    public static ProductService getInstance() {
        if (instance == null) {
            synchronized (ProductService.class) {
                if (instance == null) {
                    instance = new ProductService();
                }
            }
        }
        return instance;
    }

    // Method to add a new product
    public void addProduct(Product product) throws SQLException {
        productDAO.addProduct(product);
    }

    // Method to update an existing product
    public void updateProduct(Product product) throws SQLException {
        productDAO.updateProduct(product);
    }

    // Method to retrieve a product by ID
    public Product getProduct(int id) throws SQLException {
        return productDAO.getProduct(id);
    }

    // Method to delete a product by ID
    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteProduct(id);
    }

    // Method to get a list of all products
    public List<Product> getAllProducts(String searchQuery) throws SQLException {
    		return productDAO.getAllProducts(searchQuery);    		
    }
    public List<Product> getProductsByCategory(int category_id,String searchQuery) throws SQLException {
    	return productDAO.getProductsByCategory(category_id,searchQuery);
    }
}
