package dao;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import models.Employee;

@RunWith(PowerMockRunner.class)  // Use PowerMockRunner to enable PowerMockito
@PrepareForTest(DBConnectionFactory.class)  // Prepare DBConnectionFactory for mocking
public class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        // Mock the static method DBConnectionFactory.getConnection()
        PowerMockito.mockStatic(DBConnectionFactory.class);
        when(DBConnectionFactory.getConnection()).thenReturn(mockConnection);

        // Mock the behavior of the connection and prepared statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Mock the behavior of the result set
        mockResultSet = mock(ResultSet.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        employeeDAO = new EmployeeDAO();  // Use the real EmployeeDAO
    }

    @Test
    public void testAddEmployee() throws SQLException {
        // Create a sample Employee object
        Employee employee = new Employee(1, "John Doe", "john@example.com", "", 1, "admin");

        // The setPassword method handles the password hashing internally
        employee.setPassword("123456");  // This will hash the password

        // Simulate executeUpdate() returning 1 (successful update)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        employeeDAO.addEmployee(employee);

        // Verify that the PreparedStatement was invoked with correct parameters
        verify(mockPreparedStatement).setString(1, "John Doe");
        verify(mockPreparedStatement).setString(2, "john@example.com");
        verify(mockPreparedStatement).setString(3, employee.getPassword());  // Verify hashed password is passed
        verify(mockPreparedStatement).setInt(4, 1);
        verify(mockPreparedStatement).setString(5, "admin");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateEmployee() throws SQLException {
        // Create a sample Employee object
        Employee employee = new Employee(1, "John Doe", "john@example.com", "", 1, "admin");
        employee.setPassword("123456");

        // Mock the behavior of the update operation
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        employeeDAO.updateEmployee(employee);

        // Verify that the PreparedStatement was invoked with correct parameters
        verify(mockPreparedStatement).setString(1, "John Doe");
        verify(mockPreparedStatement).setString(2, "john@example.com");
        verify(mockPreparedStatement).setString(3, employee.getPassword());
        verify(mockPreparedStatement).setInt(4, 1);
        verify(mockPreparedStatement).setString(5, "admin");
        verify(mockPreparedStatement).setInt(6, employee.getId());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetEmployeeById() throws SQLException {
        // Mock the result set and its behavior
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("password")).thenReturn("123456");
        when(mockResultSet.getInt("branch_id")).thenReturn(1);
        when(mockResultSet.getString("role")).thenReturn("admin");

        // Call the method under test
        Employee employee = employeeDAO.getEmployee(1);

        // Verify that the PreparedStatement was invoked with the correct parameter
        verify(mockPreparedStatement).setInt(1, 1);

        // Check that the returned employee is correct
        assertNotNull(employee);
        assertEquals(1, employee.getId());
        assertEquals("John Doe", employee.getName());
        assertEquals("john@example.com", employee.getEmail());
        assertEquals("123456", employee.getPassword());
        assertEquals(1, employee.getBranch_id());
        assertEquals("admin", employee.getRole());
    }

    @Test
    public void testDeleteEmployee() throws SQLException {
        // Mock the behavior of the delete operation
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        employeeDAO.deleteEmployee(1);

        // Verify that the PreparedStatement was invoked with the correct parameter
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllEmployees() throws SQLException {
        // Mock the result set and its behavior
        when(mockResultSet.next()).thenReturn(true, true, false); // Simulate two rows
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("John Doe", "Jane Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com", "jane@example.com");
        when(mockResultSet.getString("password")).thenReturn("123456", "password456");
        when(mockResultSet.getInt("branch_id")).thenReturn(1, 2);
        when(mockResultSet.getString("role")).thenReturn("admin", "staff");

        // Call the method under test
        List<Employee> employees = employeeDAO.getAllEmployees();

        // Verify that two employees were returned
        assertNotNull(employees);
        assertEquals(2, employees.size());

        // Check that the details of the first employee are correct
        assertEquals(1, employees.get(0).getId());
        assertEquals("John Doe", employees.get(0).getName());
        assertEquals("john@example.com", employees.get(0).getEmail());

        // Check that the details of the second employee are correct
        assertEquals(2, employees.get(1).getId());
        assertEquals("Jane Doe", employees.get(1).getName());
        assertEquals("jane@example.com", employees.get(1).getEmail());
    }
}
