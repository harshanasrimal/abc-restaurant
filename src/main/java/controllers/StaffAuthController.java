package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;
import services.EmployeeServices;

/**
 * Servlet implementation class StaffAuthController
 */
public class StaffAuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeServices employeeServices;

    @Override
    public void init() throws ServletException {
        employeeServices = EmployeeServices.getInstance(); // Initialize services
    }

    /**
     * Handle GET requests
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
        	response.sendRedirect(request.getContextPath() + "/auth/staff/?action=login");
        }

        switch (action) {
            case "login":
                showLoginForm(request, response);
                break;
            case "logout":
                logoutStaff(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    /**
     * Handle POST requests
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            authenticateStaff(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    // Display the login form
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp");
        dispatcher.forward(request, response);
    }

    // Handle staff authentication
    private void authenticateStaff(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Employee employee = employeeServices.getEmployee(email);
            if (employee != null && employee.checkPassword(password)) {
                // Valid staff login
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", employee);
                session.setAttribute("isStaff", true);
                response.sendRedirect(request.getContextPath() + "/"+employee.getRole()+"/dashboard"); // Redirect to dashboard
            } else {
                // Invalid login
                request.setAttribute("error", "Invalid email or password");
                showLoginForm(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during authentication");
            showLoginForm(request, response);
        }
    }

    // Handle staff logout
    private void logoutStaff(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Get session, but don't create one if it doesn't exist
        if (session != null) {
            session.invalidate(); // Invalidate session to log out
        }
        response.sendRedirect(request.getContextPath() + "/auth/staff/?action=login"); // Redirect to login page
    }
}
