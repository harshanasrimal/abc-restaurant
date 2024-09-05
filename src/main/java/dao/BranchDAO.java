package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Branch;

public class BranchDAO {

	public void addBranch(Branch branch) throws SQLException {
	       String query = "INSERT INTO branches (location, description) VALUES ( ?, ?)";
	    	Connection connection = DBConnectionFactory.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, branch.getlocation());
	        statement.setString(2, branch.getDescription());
	        statement.executeUpdate();
	        
	        statement.close();
	        connection.close();
	}

	public void updateBranch(Branch branch) throws SQLException {
		String query = "UPDATE branches SET location = ?, description = ? WHERE id = ?";
	    	Connection connection = DBConnectionFactory.getConnection();
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, branch.getlocation());
	        statement.setString(2, branch.getDescription());
	        statement.setInt(3, branch.getId());
	        statement.executeUpdate();
	        
	        statement.close();
	        connection.close();
	}
	
    public List<Branch> getAllBranches() throws SQLException {
        List<Branch> branches = new ArrayList<>();
        String query = "SELECT * FROM branches";

        Connection connection = DBConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) 
        {
        	int id = resultSet.getInt("id");
        	String location = resultSet.getString("location");
        	String desc = resultSet.getString("description");
        	branches.add(new Branch(id, location, desc));
        }

        resultSet.close();
        statement.close();
        connection.close();
        return branches;
    }
    
    public Branch getBranch(int id) throws SQLException {
        String query = "SELECT * FROM branches WHERE id = ?";
        Branch branch = null;

        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String location = resultSet.getString("location");
            String description = resultSet.getString("description");
            branch = new Branch(id, location, description);
        }

        // Close resources
        resultSet.close();
        statement.close();
        connection.close();

        return branch; 
    }
    
    public void deleteBranch(int id) throws SQLException {
        String query = "DELETE FROM branches WHERE id = ?";

        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setInt(1, id); 

        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Branch deleted successfully.");
        } else {
            System.out.println("Branch with ID " + id + " not found.");
        }

        // Close the resources
        statement.close();
        connection.close();
    }

	
}
