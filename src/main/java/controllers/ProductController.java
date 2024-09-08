package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Product;
import services.CategoryServices;
import services.ProductService;
import utils.AuthUtil;

/**
 * Servlet implementation class ProductController
 */
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
    }

    @Override
    public void init() throws ServletException {
        productService = ProductService.getInstance(); // Initialize ProductService
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
            response.sendRedirect(request.getContextPath() + "/admin/products/?action=list");
            return;
        }

        switch (action) {
            case "list":
                listProducts(request, response);
                break;
            case "create":
                showCreateForm(request, response);
                break;
            case "update":
                showUpdateForm(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
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
                if (validateProductData(request, response)) {
                    createProduct(request, response);
                } else {
                    showCreateForm(request, response); // Show form again with error message
                }
                break;
            case "update":
                updateProduct(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }
    
    // Method to list all products (READ operation)
    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("products", productService.getAllProducts(""));
        } catch (Exception e) {
            request.setAttribute("error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/product/list.jsp");
        dispatcher.forward(request, response);
    }

    // Method to show create form
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		request.setAttribute("categories", CategoryServices.getInstance().getAllCategories());
		} catch (Exception e) {
			request.setAttribute("error", "Unable to load Categories");
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/product/create.jsp");
        dispatcher.forward(request, response);
    }
    
    // Method to show update form
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("product", productService.getProduct(id));
            request.setAttribute("categories", CategoryServices.getInstance().getAllCategories());
        } catch (Exception e) {
            request.setAttribute("error", "Something went wrong");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/product/update.jsp");
        dispatcher.forward(request, response);
    }

    // Method to handle product creation (CREATE operation)
    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        boolean active = Boolean.parseBoolean(request.getParameter("active"));

        try {
            productService.addProduct(new Product(name, description, price, categoryId, image, active));
            response.sendRedirect(request.getContextPath() + "/admin/products/?action=list");
        } catch (Exception e) {
            request.setAttribute("error", e);
            showCreateForm(request, response);
        }
    }

    // Method to delete a product (DELETE operation)
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            request.setAttribute("error", e);
        }
        response.sendRedirect(request.getContextPath() + "/admin/products/?action=list");
    }

    // Method to handle product update (UPDATE operation)
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        boolean active = "1".equals(request.getParameter("active"));
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            productService.updateProduct(new Product(id, name, description, price, categoryId, image, active));
            response.sendRedirect(request.getContextPath() + "/admin/products/?action=list");
        } catch (Exception e) {
            request.setAttribute("error", e);
            showUpdateForm(request, response);
        }
    }

    // Validation Method for product creation/update
    private boolean validateProductData(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");

        // Simple validation
        if (name == null || name.isEmpty()) {
            request.setAttribute("error", "Product name is required.");
            return false;
        }

        return true;
    }
}
