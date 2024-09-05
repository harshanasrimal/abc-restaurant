package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Product;
import java.math.BigDecimal;

public class ProductDAO {

    // Method to add a product
    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (name, description, price, category_id, image) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getCategory_id());
            statement.setString(5, product.getImage());
            statement.executeUpdate();
        }
    }

    // Method to update a product
    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, category_id = ?, active = ?, image = ? WHERE id = ?";
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getCategory_id());
            statement.setBoolean(5, product.isActive());
            statement.setString(6, product.getImage());
            statement.setInt(7, product.getId());

            statement.executeUpdate();
        }
    }

    // Method to retrieve a product by ID
    public Product getProduct(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        Product product = null;
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String image = resultSet.getString("image");
                BigDecimal price = resultSet.getBigDecimal("price");
                int categoryId = resultSet.getInt("category_id");
                boolean active = resultSet.getBoolean("active");

                product = new Product(id, name, description, price, categoryId, image, active);
            }
        }
        return product;
    }

    // Method to retrieve all products
    public List<Product> getAllProducts() throws SQLException {
        String query = "SELECT p.id, p.name, p.description, p.price, p.category_id, c.name AS category_name, p.image, p.active " +
                       "FROM products p " +
                       "JOIN categories c ON p.category_id = c.id";
        List<Product> products = new ArrayList<>();
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
             
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String image = resultSet.getString("image");
                BigDecimal price = resultSet.getBigDecimal("price");
                int categoryId = resultSet.getInt("category_id");
                String categoryName = resultSet.getString("category_name");  // Get category name
                boolean active = resultSet.getBoolean("active");

                // Use the overloaded constructor with category name
                Product product = new Product(id, name, description, price, categoryId, image, active, categoryName);
                products.add(product);
            }
        }
        return products;
    }


    // Method to delete a product
    public void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
