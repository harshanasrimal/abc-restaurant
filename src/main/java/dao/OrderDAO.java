package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Order;
import models.OrderItem;

import java.math.BigDecimal;

public class OrderDAO {

    // Method to add a new Order and return the generated order ID
    public int addOrder(Order order) throws SQLException {
        String orderQuery = "INSERT INTO orders (customer_id, branch_id, date, status, type, total, service_charge, delivery_charge) VALUES (?, ?, NOW(), ?, ?, ?, ?, ?)";
        int orderId = 0;

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the order parameters
            statement.setInt(1, order.getCustomer_id());
            statement.setInt(2, order.getBranch_id());
            statement.setString(3, order.getStatus());
            statement.setString(4, order.getType());
            statement.setBigDecimal(5, order.getTotal());
            statement.setBigDecimal(6, order.getService_charge());
            statement.setBigDecimal(7, order.getDelivery_charge());

            // Execute the order insertion
            statement.executeUpdate();

            // Get the generated order ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1); // Get the newly generated order ID
                }
            }

            // After successfully inserting the order, insert order items
            insertOrderItems(orderId, order.getItems(), connection);
        }

        return orderId;
    }
   
    // Method to insert order items into the order_product table
    private void insertOrderItems(int orderId, List<OrderItem> items, Connection connection) throws SQLException {
    	 System.out.println("Function called");
        String orderItemQuery = "INSERT INTO order_product (order_id, product_id, qty) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(orderItemQuery)) {
            for (OrderItem item : items) {
                statement.setInt(1, orderId);
                statement.setInt(2, item.getProduct_id());
                statement.setInt(3, item.getQty());

                // Insert the order item
                statement.addBatch(); // Add to batch for optimized insertion
            }

            // Execute the batch of inserts
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error inserting order items: " + e.getMessage());
            throw e;  // Rethrow the exception to handle it at a higher level if needed
        }
    }

    // Method to update an existing Order
    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE orders SET customer_id = ?, branch_id = ?, date = ?, status = ?, type = ?, total = ?, service_charge = ?, delivery_charge = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getCustomer_id());
            statement.setInt(2, order.getBranch_id());
            statement.setObject(3, order.getDate());
            statement.setString(4, order.getStatus());
            statement.setString(5, order.getType());
            statement.setBigDecimal(6, order.getTotal());
            statement.setBigDecimal(7, order.getService_charge());
            statement.setBigDecimal(8, order.getDelivery_charge());
            statement.setInt(9, order.getId());

            statement.executeUpdate();
        }
    }

    // Method to retrieve an Order by ID
    public Order getOrder(int id) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ?";
        Order order = null;

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                int branchId = resultSet.getInt("branch_id");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                BigDecimal total = resultSet.getBigDecimal("total");
                BigDecimal serviceCharge = resultSet.getBigDecimal("service_charge");
                BigDecimal deliveryCharge = resultSet.getBigDecimal("delivery_charge");

                order = new Order(id, customerId, branchId, date, status, type, total, serviceCharge, deliveryCharge, 0);
            }
        }
        return order;
    }

    // Method to retrieve all Orders
    public List<Order> getAllOrders() throws SQLException {
        String query = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int customerId = resultSet.getInt("customer_id");
                int branchId = resultSet.getInt("branch_id");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                BigDecimal total = resultSet.getBigDecimal("total");
                BigDecimal serviceCharge = resultSet.getBigDecimal("service_charge");
                BigDecimal deliveryCharge = resultSet.getBigDecimal("delivery_charge");

                Order order = new Order(id, customerId, branchId, date, status, type, total, serviceCharge, deliveryCharge, 0);
                orders.add(order);
            }
        }
        return orders;
    }

    // Method to delete an Order by ID
    public void deleteOrder(int id) throws SQLException {
        String query = "DELETE FROM orders WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
