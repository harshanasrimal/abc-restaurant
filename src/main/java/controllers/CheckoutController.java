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
import models.Delivery;
import models.Order;
import models.OrderItem;
import models.Product;
import services.BranchServices;
import services.DeliveryServices;
import services.OrderServices;
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
        case "placeOrder":
				checkout(request, response);
            break;
        default:
        	response.getWriter().append("Served at: ").append(request.getContextPath());
            break;
    }
        
        
	}
	
	private void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String cartData = request.getParameter("cartData");
	    int branch_id = Integer.parseInt(request.getParameter("branch"));  // Get branch from request
	    String order_type = request.getParameter("orderType");  // Get order type (pickup or delivery)
	    String payment_type = request.getParameter("paymentType");  // Get payment type

	    try {
	        if (cartData != null && !cartData.isEmpty()) {
	            // Retrieve the logged-in customer from session
	            Customer customer = (Customer) request.getSession().getAttribute("loggedInUser");

	            // Split cart data to get individual items
	            String[] items = cartData.split(",");
	            List<OrderItem> orderItems = new ArrayList<>();
	            BigDecimal totalAmount = BigDecimal.ZERO;

	            // Loop through each item and calculate the total
	            for (String item : items) {
	                String[] parts = item.split(":");
	                int productId = Integer.parseInt(parts[0]);
	                int quantity = Integer.parseInt(parts[1]);

	                // Retrieve product details using ProductService
	                Product product = ProductService.getInstance().getProduct(productId);

	                if (product != null) {
	                    BigDecimal price = product.getPrice();
	                    BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(quantity));

	                    // Create an OrderItem and add it to the list
	                    orderItems.add(new OrderItem(productId, quantity, itemTotal));
						totalAmount = totalAmount.add(itemTotal);
	                } else {
	                    throw new Exception("Invalid product ID: " + productId);
	                }
	            }

	            // Create an Order object and set the order items
	            Order order = new Order(customer.getId(), branch_id, order_type, totalAmount);
				order.setItems(orderItems);

	            // Save the order using OrderServices and get the order ID
	            int orderId = OrderServices.getInstance().addOrder(order);

	            // Handle delivery details if the order type is delivery
	            Delivery delivery = null;
	            if ("delivery".equalsIgnoreCase(order_type)) {
	                String deliveryAddress = request.getParameter("address");
	                String contactNumber = request.getParameter("contactNumber");

	                if (deliveryAddress == null || deliveryAddress.isEmpty()) {
	                    throw new Exception("Delivery address is required for delivery orders.");
	                }

	                if (contactNumber == null || contactNumber.isEmpty()) {
	                    throw new Exception("Contact number is required for delivery orders.");
	                }

	                // Create a Delivery object and save the delivery details
	                delivery = new Delivery(orderId, deliveryAddress, contactNumber, "Pending");
	                DeliveryServices.getInstance().addDelivery(delivery);
	            }

	            // Redirect to confirmation page with total amount and delivery info
	            request.setAttribute("orderId", orderId);
	            request.setAttribute("totalAmount", totalAmount);  // Return total amount
	            request.setAttribute("orderType", order_type);  // Return order type
	            request.setAttribute("delivery", delivery);  // Return delivery details if applicable
	            request.setAttribute("message", "Your order has been successfully placed!");

	            request.getRequestDispatcher("/WEB-INF/views/orderConfirmation.jsp").forward(request, response);
	        } else {
	            throw new Exception("Your cart is empty. Please add items to your cart.");
	        }
	    } catch (Exception e) {
	        // Redirect to the error page with the error message
	        request.setAttribute("errorMessage", e.getMessage());
	        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
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
