package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Customer;
import services.CustomerServices;

/**
 * Servlet implementation class AuthController
 */
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerServices customerServices;

    @Override
    public void init() throws ServletException {
        customerServices = CustomerServices.getInstance(); // Initialize CustomerServices
    }

    /**
     * Handle GET requests
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/auth/?action=login");
            return;
        }

        switch (action) {
            case "login":
                showLoginForm(request, response);
                break;
            case "register":
                showRegisterForm(request, response);
                break;
            case "logout":
                logout(request, response);
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
            authenticate(request, response);
        } else if ("register".equals(action)) {
            register(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    // Display the login form
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp");
        dispatcher.forward(request, response);
    }

    // Display the registration form
    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
        dispatcher.forward(request, response);
    }

    // Handle customer authentication (login)
    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Customer customer = customerServices.getCustomer(email);
            if (customer != null && customer.checkPassword(password)) {
                // Valid login
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", customer);
                session.setAttribute("isStaff", false);
                response.sendRedirect(request.getContextPath() + "/");
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

    // Handle customer registration
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String contactNo = request.getParameter("contactNo");
        String address = request.getParameter("address");

        // Validate input fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || contactNo.isEmpty() || address.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            showRegisterForm(request, response);
            return;
        }

        try {
            // Check if customer already exists
            Customer existingCustomer = customerServices.getCustomer(email);
            if (existingCustomer != null) {
                request.setAttribute("error", "Customer with this email already exists.");
                showRegisterForm(request, response);
                return;
            }

            // Create new customer
            Customer newCustomer = new Customer(0, name, email, "", contactNo, address);
            newCustomer.setPassword(password);
            customerServices.addCustomer(newCustomer);

            // Automatically log in the customer after registration
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", newCustomer);
            session.setAttribute("isStaff", false);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during registration");
            showRegisterForm(request, response);
        }
    }

    // Handle logout
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Get session, but don't create one if it doesn't exist
        if (session != null) {
            session.invalidate(); // Invalidate session to log out
        }
        response.sendRedirect(request.getContextPath() + "/auth/?action=login"); // Redirect to login page
    }
}
