package services;

import java.sql.SQLException;
import java.util.List;

import dao.CustomerDAO;
import models.Customer;

public class CustomerServices {
    private static CustomerServices instance;
    private CustomerDAO customerDAO;

    private CustomerServices() {
        customerDAO = new CustomerDAO();
    }

    public static CustomerServices getInstance() {
        if (instance == null) {
            synchronized (CustomerServices.class) {
                if (instance == null) {
                    instance = new CustomerServices();
                }
            }
        }
        return instance;
    }

    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.updateCustomer(customer);
    }

    public Customer getCustomer(int id) throws SQLException {
        return customerDAO.getCustomer(id);
    }

    public Customer getCustomer(String email) throws SQLException {
        return customerDAO.getCustomer(email);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerDAO.deleteCustomer(id);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }
}
