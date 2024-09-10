package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Category;

public class CategoryDAO{

	public void addCategory(Category category) throws SQLException {
	       String query = "INSERT INTO categories (name, description) VALUES ( ?, ?)";
	    	Connection connection = DBConnectionFactory.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, category.getName());
	        statement.setString(2, category.getDescription());
	        statement.executeUpdate();
	        
	        statement.close();
	        connection.close();
	}

	public void updateCategory(Category category) throws SQLException {
		String query = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
	    	Connection connection = DBConnectionFactory.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, category.getName());
	        statement.setString(2, category.getDescription());
	        statement.setInt(3, category.getId());
	        statement.executeUpdate();
	        
	        statement.close();
	        connection.close();
	}
	
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";

        Connection connection = DBConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) 
        {
        	int id = resultSet.getInt("id");
        	String name = resultSet.getString("name");
        	String desc = resultSet.getString("description");
        	categories.add(new Category(id, name, desc));
        }

        resultSet.close();
        statement.close();
        connection.close();
        return categories;
    }
    
    public Category getCategory(int id) throws SQLException {
        String query = "SELECT * FROM categories WHERE id = ?";
        Category category = null;

        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            category = new Category(id, name, description);
        }

        // Close resources
        resultSet.close();
        statement.close();
        connection.close();

        return category; 
    }
    
    public void deleteCategory(int id) throws SQLException {
        String query = "DELETE FROM categories WHERE id = ?";

        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setInt(1, id); 

        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Category deleted successfully.");
        } else {
            System.out.println("Category with ID " + id + " not found.");
        }

        // Close the resources
        statement.close();
        connection.close();
    }

	
}
