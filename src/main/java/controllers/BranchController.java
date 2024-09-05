package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Branch;
import services.BranchServices;

/**
 * Servlet implementation class BranchController
 */
public class BranchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BranchServices branchServices;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BranchController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
        branchServices = BranchServices.getInstance(); // Initialize BranchServices
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Default action or handle missing action
            response.sendRedirect(request.getContextPath() + "/branches/?action=list");
            return;
        }

        switch (action) {
            case "list":
                // Handle the 'read' operation (list all branches)
                listBranches(request, response);
                break;
            case "create":
                // Show the form for creating a new branch
                showCreateForm(request, response);
                break;
            case "update":
                // Show the form for updating an existing branch
                showUpdateForm(request, response);
                break;
            case "delete":
                // Delete a branch
                deleteBranch(request, response);
                break;
            default:
                // Handle invalid action
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
                // Validate and handle the creation process
                if (validateBranchData(request, response)) {
                    createBranch(request, response);
                } else {
                	showCreateForm(request, response); // Show form again with error message
                }
                break;
            case "update":
                // Handle the 'update' operation
                updateBranch(request, response);
                break;
            default:
                // Handle invalid action
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
	}
	
    // Method to list all branches (READ operation)
    private void listBranches(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to retrieve branches from database
    	try {
    		request.setAttribute("branches", branchServices.getAllBranches());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/branch/list.jsp");
        dispatcher.forward(request, response);
    }
    
    // Method to show create form
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/branch/create.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		request.setAttribute("branch", branchServices.getBranch(Integer.parseInt(request.getParameter("id"))));
		} catch (Exception e) {
			request.setAttribute("error", "Something Went Wrong");
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/branch/update.jsp");
        dispatcher.forward(request, response);
    }
    
    // Method to handle branch creation (CREATE operation)
    private void createBranch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Logic to create a new branch (get form data and save to DB)
        String branchLocation = request.getParameter("location");
        String description = request.getParameter("description");
        // Insert the branch into the database
        try {
        	branchServices.addBranch(new Branch(branchLocation,description));
        	response.sendRedirect(request.getContextPath() + "/branches/?action=list");			
		} catch (Exception e) {
			request.setAttribute("error", e);
			showCreateForm(request, response);
		}
    }
    
    private void deleteBranch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Logic to create a new branch (get form data and save to DB)
    	int id = Integer.parseInt(request.getParameter("id"));
        // Insert the branch into the database
        try {
        	branchServices.deleteBranch(id);		
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
        response.sendRedirect(request.getContextPath() + "/branches/?action=list");	
    }
    
    private void updateBranch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Logic to create a new branch (get form data and save to DB)
        String branchLocation = request.getParameter("location");
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));
        // Insert the branch into the database
        try {
        	branchServices.updateBranch(new Branch(id,branchLocation,description));
        	response.sendRedirect(request.getContextPath() + "/branches/?action=list");			
		} catch (Exception e) {
			request.setAttribute("error", e);
			showCreateForm(request, response);
		}
    }
    
 // Validation Method
    private boolean validateBranchData(HttpServletRequest request, HttpServletResponse response) {
        String branchLocation = request.getParameter("location");
        
        // Simple validation
        if (branchLocation == null || branchLocation.isEmpty()) {
            request.setAttribute("error", "Branch Location is required.");
            return false;
        }
        
        return true; // Valid input
    }

}
