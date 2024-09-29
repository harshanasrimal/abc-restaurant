package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Delivery;

public class DeliveryDAO {

    // Method to add a new delivery entry and return the generated delivery ID
    public int addDelivery(Delivery delivery) throws SQLException {
        String deliveryQuery = "INSERT INTO deliveries (order_id, address, contact_no, status) VALUES (?, ?, ?, ?)";
        int deliveryId = 0;

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(deliveryQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, delivery.getOrderId());
            statement.setString(2, delivery.getAddress());
            statement.setString(3, delivery.getContactNumber());
            statement.setString(4, delivery.getStatus());

            // Execute the delivery insertion
            statement.executeUpdate();

            // Retrieve the generated delivery ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    deliveryId = generatedKeys.getInt(1); // Get the generated delivery ID
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting delivery details: " + e.getMessage());
            throw e;
        }

        return deliveryId;
    }

    // Method to update the delivery status
    public void updateDeliveryStatus(int orderId, String status) throws SQLException {
        String updateQuery = "UPDATE deliveries SET status = ? WHERE order_id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, status);
            statement.setInt(2, orderId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating delivery status: " + e.getMessage());
            throw e;
        }
    }
}
