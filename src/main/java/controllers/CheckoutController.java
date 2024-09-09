package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Branch;
import models.Customer;
import models.Order;
import models.OrderItem;
import models.Product;
import services.BranchServices;
import services.ProductService;
import utils.AuthUtil;

/**
 * Servlet implementation class CheckoutController
 */
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
        if (action.equals("branches")) {
            // Default action or handle missing action
            try {
				getBranches(request, response);
			} catch (IOException | ServletException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return;
        }else {
        	response.getWriter().append("Served new at: ").append(request.getContextPath());        	
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        if(!AuthUtil.checkAuthorization(request, response, "")) {
        	System.out.println("Unauthorized");
        	return;
        }
        
        String action = request.getParameter("action");
        
        switch (action) {
        case "payment":
        	response.getWriter().append("Served at: ").append(request.getContextPath()); // should implement this
            break;
        default:
        	response.getWriter().append("Served at: ").append(request.getContextPath());
            break;
    }
        
        
	}
	
    private void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String cartData = request.getParameter("cartData");
        int branch_id = 1;
        String order_type = "pickup";
        
        if (cartData != null && !cartData.isEmpty()) {
        	Customer customer = (Customer) request.getSession().getAttribute("loggedInUser");
            String[] items = cartData.split(",");
            List<OrderItem> orderItems = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;
            
            for (String item : items) {
            	String[] parts = item.split(":");
                int productId = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);      
                
                Product product = ProductService.getInstance().getProduct(productId);
                
                if (product != null) {
                    BigDecimal price = product.getPrice();
                    BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));
                    orderItems.add(new OrderItem(productId, quantity, price));
                    totalAmount = totalAmount.add(itemTotal);
                }
                
            }
            Order order = new Order(customer.getId(),branch_id, order_type, totalAmount);   
            order.setItems(orderItems);
            
            //OrderService.saveOrder(order);
            //write show UI part
	}
  }
    
    private void getBranches(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
    	 List<Branch> branches = BranchServices.getInstance().getAllBranches();
         Gson gson = new Gson();
         String json = gson.toJson(branches);
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");

         // Write the JSON to the response
         response.getWriter().write(json);
  }

}
