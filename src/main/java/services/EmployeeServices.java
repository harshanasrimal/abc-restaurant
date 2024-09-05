package services;

import java.sql.SQLException;
import java.util.List;

import dao.EmployeeDAO;
import models.Employee;

public class EmployeeServices {
    private static EmployeeServices instance;
    private EmployeeDAO employeeDAO;

    private EmployeeServices() {
        employeeDAO = new EmployeeDAO();
    }

    public static EmployeeServices getInstance() {
        if (instance == null) {
            synchronized (EmployeeServices.class) {
                if (instance == null) {
                    instance = new EmployeeServices();
                }
            }
        }
        return instance;
    }

    public void addEmployee(Employee employee) throws SQLException {
        employeeDAO.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) throws SQLException {
        employeeDAO.updateEmployee(employee);
    }

    public Employee getEmployee(int id) throws SQLException {
        return employeeDAO.getEmployee(id);
    }

    public void deleteEmployee(int id) throws SQLException {
        employeeDAO.deleteEmployee(id);
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }
}
