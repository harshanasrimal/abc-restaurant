package utils;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import models.Customer;
import models.Employee;

public class AuthUtilTest {

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNoSession() throws IOException {
        // Simulate no session
        when(mockRequest.getSession(false)).thenReturn(null);

        // Call checkAuthorization with no session
        boolean result = AuthUtil.checkAuthorization(mockRequest, mockResponse, "admin");

        // Verify that the user is redirected to the login page
        verify(mockResponse).sendRedirect(anyString());
        assertFalse(result);  // Authorization should fail
    }

    @Test
    public void testNoUserInSession() throws IOException {
        // Simulate an empty session with no logged-in user
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("loggedInUser")).thenReturn(null);

        // Call checkAuthorization with no user in the session
        boolean result = AuthUtil.checkAuthorization(mockRequest, mockResponse, "admin");

        // Verify that the user is redirected to the staff login page
        verify(mockResponse).sendRedirect(contains("/auth/staff/?action=login"));
        assertFalse(result);  // Authorization should fail
    }

    @Test
    public void testStaffWithoutRequiredRole() throws IOException {
        // Simulate a session with a logged-in user who doesn't have the required role
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        Employee employee = new Employee(1, "John Doe", "john@example.com", "", 1, "staff");
        when(mockSession.getAttribute("loggedInUser")).thenReturn(employee);

        // Call checkAuthorization with a user who doesn't have the required role
        boolean result = AuthUtil.checkAuthorization(mockRequest, mockResponse, "admin");

        // No redirection is expected in this case
        assertFalse(result);  // Authorization should fail
    }

    @Test
    public void testStaffWithRequiredRole() throws IOException {
        // Simulate a session with a logged-in user who has the required role
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        Employee employee = new Employee(1, "John Doe", "john@example.com", "", 1, "admin");
        when(mockSession.getAttribute("loggedInUser")).thenReturn(employee);

        // Call checkAuthorization with a user who has the required role
        boolean result = AuthUtil.checkAuthorization(mockRequest, mockResponse, "admin");

        // No redirection is expected in this case
        verify(mockResponse, never()).sendRedirect(anyString());
        assertTrue(result);  // Authorization should succeed
    }

    @Test
    public void testCustomerAuthorizationWithNoRoleRequired() throws IOException {
        // Simulate a session with a customer
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        Customer customer = new Customer(1, "John Doe", "john@example.com", "", "0770841808","kandy");;  // A non-employee user
        when(mockSession.getAttribute("loggedInUser")).thenReturn(customer);

        // Call checkAuthorization with no role required (e.g., "" for customers)
        boolean result = AuthUtil.checkAuthorization(mockRequest, mockResponse, "");

        // No redirection is expected in this case
        verify(mockResponse, never()).sendRedirect(anyString());
        assertTrue(result);  // Authorization should succeed for a customer
    }
}
