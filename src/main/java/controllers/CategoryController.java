package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Category;
import services.CategoryServices;

/**
 * Servlet implementation class CategoryController
 */
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryServices categoryServices;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
        categoryServices = CategoryServices.getInstance(); // Initialize CategoryServices
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Default action or handle missing action
            response.sendRedirect(request.getContextPath() + "/admin/categories/?action=list");
            return;
        }

        switch (action) {
            case "list":
                // Handle the 'read' operation (list all categories)
                listCategories(request, response);
                break;
            case "create":
                // Show the form for creating a new category
                showCreateForm(request, response);
                break;
            case "update":
                // Show the form for updating an existing category
                showUpdateForm(request, response);
                break;
            case "delete":
                // Delete a category
                deleteCategory(request, response);
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
                if (validateCategoryData(request, response)) {
                    createCategory(request, response);
                } else {
                	showCreateForm(request, response); // Show form again with error message
                }
                break;
            case "update":
                // Handle the 'update' operation
                updateCategory(request, response);
                break;
            default:
                // Handle invalid action
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
	}
	
    // Method to list all categories (READ operation)
    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logic to retrieve categories from database
    	try {
    		request.setAttribute("categories", categoryServices.getAllCategories());
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/category/list.jsp");
        dispatcher.forward(request, response);
    }
    
    // Method to show create form
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/category/create.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		request.setAttribute("category", categoryServices.getCategory(Integer.parseInt(request.getParameter("id"))));
		} catch (Exception e) {
			request.setAttribute("error", "Something Went Wrong");
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/category/update.jsp");
        dispatcher.forward(request, response);
    }
    
    // Method to handle category creation (CREATE operation)
    private void createCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Logic to create a new category (get form data and save to DB)
        String categoryName = request.getParameter("name");
        String description = request.getParameter("description");
        // Insert the category into the database
        try {
        	categoryServices.addCategory(new Category(categoryName,description));
        	response.sendRedirect(request.getContextPath() + "/admin/categories/?action=list");			
		} catch (Exception e) {
			request.setAttribute("error", e);
			showCreateForm(request, response);
		}
    }
    
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Logic to create a new category (get form data and save to DB)
    	int id = Integer.parseInt(request.getParameter("id"));
        // Insert the category into the database
        try {
        	categoryServices.deleteCategory(id);		
		} catch (Exception e) {
			request.setAttribute("error", e);
		}
        response.sendRedirect(request.getContextPath() + "/admin/categories/?action=list");	
    }
    
    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Logic to create a new category (get form data and save to DB)
        String categoryName = request.getParameter("name");
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));
        // Insert the category into the database
        try {
        	categoryServices.updateCategory(new Category(id,categoryName,description));
        	response.sendRedirect(request.getContextPath() + "/admin/categories/?action=list");			
		} catch (Exception e) {
			request.setAttribute("error", e);
			showCreateForm(request, response);
		}
    }
    
 // Validation Method
    private boolean validateCategoryData(HttpServletRequest request, HttpServletResponse response) {
        String categoryName = request.getParameter("name");
        
        // Simple validation
        if (categoryName == null || categoryName.isEmpty()) {
            request.setAttribute("error", "Category Name is required.");
            return false;
        }
        
        return true; // Valid input
    }

}
