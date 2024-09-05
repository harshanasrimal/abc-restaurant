package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Customer;

public class CustomerDAO {

    // Method to add a new Customer
    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, email, password, contact_no, address) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPassword());
            statement.setString(4, customer.getContactNumber());
            statement.setString(5, customer.getAddress());

            statement.executeUpdate();
        }
    }

    // Method to update an existing Customer
    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET name = ?, email = ?, password = ?, contact_no = ?, address = ? WHERE id = ?";
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPassword());
            statement.setString(4, customer.getContactNumber());
            statement.setString(5, customer.getAddress());
            statement.setInt(6, customer.getId());

            statement.executeUpdate();
        }
    }

    // Method to retrieve a Customer by ID
    public Customer getCustomer(int id) throws SQLException {
        String query = "SELECT * FROM customers WHERE id = ?";
        Customer customer = null;

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String contactNumber = resultSet.getString("contact_no");
                String address = resultSet.getString("address");

                customer = new Customer(id, name, email, password, contactNumber, address);
            }
        }
        return customer;
    }

    // Method to retrieve a Customer by Email
    public Customer getCustomer(String email) throws SQLException {
        String query = "SELECT * FROM customers WHERE email = ?";
        Customer customer = null;

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String contactNumber = resultSet.getString("contact_no");
                String address = resultSet.getString("address");

                customer = new Customer(id, name, email, password, contactNumber, address);
            }
        }
        return customer;
    }

    // Method to retrieve all Customers
    public List<Customer> getAllCustomers() throws SQLException {
        String query = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
             
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String contactNumber = resultSet.getString("contact_no");
                String address = resultSet.getString("address");

                Customer customer = new Customer(id, name, email, password, contactNumber, address);
                customers.add(customer);
            }
        }
        return customers;
    }

    // Method to delete a Customer by ID
    public void deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
