package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.CategoryServices;
import services.ProductService;

/**
 * Servlet implementation class MenuController
 */
public class MenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cat = request.getParameter("cat");
	    String searchQuery = request.getParameter("search");

	    // If searchQuery is null, set it to an empty string
	    if (searchQuery == null) {
	        searchQuery = "";
	    }
		if (cat != null && !cat.isEmpty()) {
			listFilteredProducts(request, response,searchQuery);
		} else {
			listAllProducts(request, response,searchQuery);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
    // Method to list all products (READ operation)
    private void listAllProducts(HttpServletRequest request, HttpServletResponse response, String searchQuery) throws ServletException, IOException {
        try {
            request.setAttribute("products", ProductService.getInstance().getAllProducts(searchQuery));
            request.setAttribute("categories", CategoryServices.getInstance().getAllCategories());
        } catch (Exception e) {
            request.setAttribute("error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/menu.jsp");
        dispatcher.forward(request, response);
    }
    
    private void listFilteredProducts(HttpServletRequest request, HttpServletResponse response, String searchQuery) throws ServletException, IOException {
        try {
        	int cat = Integer.parseInt(request.getParameter("cat"));
            request.setAttribute("products", ProductService.getInstance().getProductsByCategory(cat,searchQuery));
            request.setAttribute("categories", CategoryServices.getInstance().getAllCategories());
        } catch (Exception e) {
            request.setAttribute("error", e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/menu.jsp");
        dispatcher.forward(request, response);
    }

}
