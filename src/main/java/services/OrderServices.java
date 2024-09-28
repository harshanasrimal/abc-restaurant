package services;

import java.sql.SQLException;
import java.util.List;

import dao.OrderDAO;
import models.Order;

public class OrderServices {
    private static OrderServices instance;
    private OrderDAO orderDAO;

    // Private constructor to implement Singleton pattern
    private OrderServices() {
        orderDAO = new OrderDAO();
    }

    // Method to get the Singleton instance of the OrderServices class
    public static OrderServices getInstance() {
        if (instance == null) {
            synchronized (OrderServices.class) {
                if (instance == null) {
                    instance = new OrderServices();
                }
            }
        }
        return instance;
    }

    // Method to add a new Order
    public int addOrder(Order order) throws SQLException {
        return orderDAO.addOrder(order);
    }

    // Method to update an existing Order
    public void updateOrder(Order order) throws SQLException {
        orderDAO.updateOrder(order);
    }

    // Method to retrieve an Order by ID
    public Order getOrder(int id) throws SQLException {
        return orderDAO.getOrder(id);
    }

    // Method to retrieve all Orders
    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    // Method to delete an Order by ID
    public void deleteOrder(int id) throws SQLException {
        orderDAO.deleteOrder(id);
    }
}
