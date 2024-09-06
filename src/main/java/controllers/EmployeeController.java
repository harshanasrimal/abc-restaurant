package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import services.BranchServices;
import services.EmployeeServices;
import utils.AuthUtil;
/**
 * Servlet implementation class EmployeeController
 */
public class EmployeeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeServices employeeServices;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeController() {
        super();
        employeeServices = EmployeeServices.getInstance();
    }

    public EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;  // Injected mock for testing
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(!AuthUtil.checkAuthorization(request, response, "admin")) {
        	System.out.println("Unauthorized");
        	return;
        }

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/employees/?action=list");
            return;
        }

        switch (action) {
            case "list":
                listEmployees(request, response);
                break;
            case "create":
                showCreateForm(request, response);
                break;
            case "update":
                showUpdateForm(request, response);
                break;
            case "delete":
                deleteEmployee(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                if (validateEmployeeData(request, response,false)) {
                    createEmployee(request, response);
                } else {
                    showCreateForm(request, response);
                }
                break;
                
            case "update":
                if (validateEmployeeData(request, response,true)) {
                	updateEmployee(request, response);
                } else {
                	showUpdateForm(request, response);
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("employees", employeeServices.getAllEmployees());
        } catch (Exception e) {
            request.setAttribute("error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employee/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	request.setAttribute("branches", BranchServices.getInstance().getAllBranches());
        } catch (Exception e) {
            request.setAttribute("error", "Something went wrong");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employee/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	request.setAttribute("branches", BranchServices.getInstance().getAllBranches());
            request.setAttribute("employee", employeeServices.getEmployee(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception e) {
            request.setAttribute("error", "Something went wrong");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employee/update.jsp");
        dispatcher.forward(request, response);
    }

    private void createEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int branchId = Integer.parseInt(request.getParameter("branch_id"));
        String role = request.getParameter("role");

        try {
            Employee employee = new Employee(0, name, email, "", branchId, role);
            employee.setPassword(password);
            employeeServices.addEmployee(employee);
            response.sendRedirect(request.getContextPath() + "/admin/employees/?action=list");
        } catch (Exception e) {
            request.setAttribute("error", e);
            showCreateForm(request, response);
        }
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int branchId = Integer.parseInt(request.getParameter("branch_id"));
        String role = request.getParameter("role");
        int id = Integer.parseInt(request.getParameter("id"));

        try {
        	Employee employee = employeeServices.getEmployee(id);
        	employee.setName(name);
        	employee.setEmail(email);
        	employee.setRole(role);
        	employee.setBranch_id(branchId);
        	// check if password empty
            if (password != null && !password.isEmpty()) {
                employee.setPassword(password);
            }
            employeeServices.updateEmployee(employee);
            response.sendRedirect(request.getContextPath() + "/admin/employees/?action=list");
        } catch (Exception e) {
            request.setAttribute("error", e);
            showUpdateForm(request, response);
        }
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            employeeServices.deleteEmployee(id);
        } catch (Exception e) {
            request.setAttribute("error", e);
        }
        response.sendRedirect(request.getContextPath() + "/admin/employees/?action=list");
    }

    private boolean validateEmployeeData(HttpServletRequest request, HttpServletResponse response, boolean isUpdate) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String branchId = request.getParameter("branch_id");
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        // Validate name
        if (name == null || name.isEmpty()) {
            request.setAttribute("error", "Name is required.");
            return false;
        }

        // Validate email
        if (email == null || email.isEmpty()) {
            request.setAttribute("error", "Email is required.");
            return false;
        }

        // Validate branch ID
        if (branchId == null || branchId.isEmpty()) {
            request.setAttribute("error", "Branch ID is required.");
            return false;
        }

        // Validate role
        if (role == null || role.isEmpty()) {
            request.setAttribute("error", "Role is required.");
            return false;
        }

        // Validate password only if it's a new employee creation, not update
        if (!isUpdate && (password == null || password.isEmpty())) {
            request.setAttribute("error", "Password is required.");
            return false;
        }

        return true;
    }

}
