package services;

import dao.DeliveryDAO;
import models.Delivery;

import java.sql.SQLException;

public class DeliveryServices {
    private static DeliveryServices instance;
    private DeliveryDAO deliveryDAO;

    // Private constructor for Singleton pattern
    private DeliveryServices() {
        deliveryDAO = new DeliveryDAO();
    }

    // Singleton pattern to get the instance of the service
    public static DeliveryServices getInstance() {
        if (instance == null) {
            synchronized (DeliveryServices.class) {
                if (instance == null) {
                    instance = new DeliveryServices();
                }
            }
        }
        return instance;
    }

    // Method to add a delivery and return the generated delivery ID
    public int addDelivery(Delivery delivery) throws SQLException {
        return deliveryDAO.addDelivery(delivery);
    }

    // Method to update the delivery status
    public void updateDeliveryStatus(int orderId, String status) throws SQLException {
        deliveryDAO.updateDeliveryStatus(orderId, status);
    }
}
