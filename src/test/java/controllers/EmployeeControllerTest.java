package controllers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import models.Employee;
import services.EmployeeServices;

public class EmployeeControllerTest {

    private EmployeeController employeeController;
    private AutoCloseable closeable;

    @Mock
    private EmployeeServices mockEmployeeServices;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Mock
    private RequestDispatcher mockDispatcher;

    @Before
    public void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(mockEmployeeServices);

        // Mock session creation and logged-in user
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        Employee loggedInEmployee = new Employee(1, "Admin User", "admin@example.com", "", 1, "admin");
        when(mockSession.getAttribute("loggedInUser")).thenReturn(loggedInEmployee);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testListEmployees() throws ServletException, IOException, SQLException {
        // Mock the request parameters and services
        when(mockRequest.getParameter("action")).thenReturn("list");
        when(mockRequest.getRequestDispatcher("/WEB-INF/views/employee/list.jsp")).thenReturn(mockDispatcher);

        // Call the method
        employeeController.doGet(mockRequest, mockResponse);

        // Verify that the listEmployees method was called and forwarded to the correct JSP
        verify(mockDispatcher).forward(mockRequest, mockResponse);
        verify(mockEmployeeServices).getAllEmployees();
    }

    @Test
    public void testCreateEmployee() throws ServletException, IOException, SQLException {
        // Mock the request parameters for creating a new employee
        when(mockRequest.getParameter("action")).thenReturn("create");
        when(mockRequest.getParameter("name")).thenReturn("John Doe");
        when(mockRequest.getParameter("email")).thenReturn("john@example.com");
        when(mockRequest.getParameter("password")).thenReturn("password123");
        when(mockRequest.getParameter("branch_id")).thenReturn("1");
        when(mockRequest.getParameter("role")).thenReturn("admin");

        // Mock redirect
        doNothing().when(mockResponse).sendRedirect(anyString());

        // Call the method
        employeeController.doPost(mockRequest, mockResponse);

        // Capture the created Employee object
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(mockEmployeeServices).addEmployee(employeeCaptor.capture());
        Employee createdEmployee = employeeCaptor.getValue();

        // Verify that the employee was created with correct data
        assertEquals("John Doe", createdEmployee.getName());
        assertEquals("john@example.com", createdEmployee.getEmail());
        assertEquals("admin", createdEmployee.getRole());
        assertEquals(1, createdEmployee.getBranch_id());
        assertTrue(createdEmployee.getPassword().length() > 0); // Ensure password is hashed
        verify(mockResponse).sendRedirect(anyString());
    }

    @Test
    public void testShowCreateForm() throws ServletException, IOException {
        // Mock the request and services
        when(mockRequest.getParameter("action")).thenReturn("create");
        when(mockRequest.getRequestDispatcher("/WEB-INF/views/employee/create.jsp")).thenReturn(mockDispatcher);

        // Call the method
        employeeController.doGet(mockRequest, mockResponse);

        // Verify that it forwards to the create form
        verify(mockDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testUpdateEmployee() throws ServletException, IOException, SQLException {
        // Mock the request parameters for updating an existing employee
        when(mockRequest.getParameter("action")).thenReturn("update");
        when(mockRequest.getParameter("id")).thenReturn("1");
        when(mockRequest.getParameter("name")).thenReturn("Jane Doe");
        when(mockRequest.getParameter("email")).thenReturn("jane@example.com");
        when(mockRequest.getParameter("password")).thenReturn("newpassword");
        when(mockRequest.getParameter("branch_id")).thenReturn("1");
        when(mockRequest.getParameter("role")).thenReturn("admin");

        // Mock the existing employee
        Employee existingEmployee = new Employee(1, "John Doe", "john@example.com", "oldpassword", 1, "admin");
        when(mockEmployeeServices.getEmployee(1)).thenReturn(existingEmployee);

        // Call the method
        employeeController.doPost(mockRequest, mockResponse);

        // Verify that the employee was updated
        verify(mockEmployeeServices).updateEmployee(existingEmployee);
        assertEquals("Jane Doe", existingEmployee.getName());
        assertEquals("jane@example.com", existingEmployee.getEmail());
        assertEquals("admin", existingEmployee.getRole());
        assertTrue(existingEmployee.getPassword().length() > 0);  // Ensure password is updated
        verify(mockResponse).sendRedirect(anyString());
    }

    @Test
    public void testDeleteEmployee() throws ServletException, IOException, SQLException {
        // Mock the request parameters for deleting an employee
        when(mockRequest.getParameter("action")).thenReturn("delete");
        when(mockRequest.getParameter("id")).thenReturn("1");

        // Call the method
        employeeController.doGet(mockRequest, mockResponse);

        // Verify that the employee was deleted
        verify(mockEmployeeServices).deleteEmployee(1);
        verify(mockResponse).sendRedirect(anyString());
    }
}
