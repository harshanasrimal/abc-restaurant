package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Employee;

public class EmployeeDAO {

    // Method to add a new Employee
    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (name, email, password, branch_id, role) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setString(3, employee.getPassword());
            statement.setInt(4, employee.getBranch_id());
            statement.setString(5, employee.getRole());

            statement.executeUpdate();
        }
    }

    // Method to update an existing Employee
    public void updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE employees SET name = ?, email = ?, password = ?, branch_id = ?, role = ? WHERE id = ?";
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmail());
            statement.setString(3, employee.getPassword());
            statement.setInt(4, employee.getBranch_id());
            statement.setString(5, employee.getRole());
            statement.setInt(6, employee.getId());

            statement.executeUpdate();
        }
    }

    // Method to retrieve an Employee by ID
    public Employee getEmployee(int id) throws SQLException {
        String query = "SELECT * FROM employees WHERE id = ?";
        Employee employee = null;

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int branch_id = resultSet.getInt("branch_id");
                String role = resultSet.getString("role");

                employee = new Employee(id, name, email, password, branch_id, role);
            }
        }
        return employee;
    }
    
    // Method to retrieve an Employee by Email
    public Employee getEmployee(String email) throws SQLException {
        String query = "SELECT * FROM employees WHERE email = ?";
        Employee employee = null;

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                int branch_id = resultSet.getInt("branch_id");
                String role = resultSet.getString("role");

                employee = new Employee(id, name, email, password, branch_id, role);
            }
        }
        return employee;
    }

    // Method to retrieve all Employees
    public List<Employee> getAllEmployees() throws SQLException {
        String query = "SELECT * FROM employees";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
             
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int branch_id = resultSet.getInt("branch_id");
                String role = resultSet.getString("role");

                Employee employee = new Employee(id, name, email, password, branch_id, role);
                employees.add(employee);
            }
        }
        return employees;
    }

    // Method to delete an Employee by ID
    public void deleteEmployee(int id) throws SQLException {
        String query = "DELETE FROM employees WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
