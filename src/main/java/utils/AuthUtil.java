package utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Employee;
import models.User;

public final class AuthUtil {
	
	
    public static boolean checkAuthorization(HttpServletRequest request, HttpServletResponse response, String requiredRole) throws IOException {
        HttpSession session = request.getSession(false);
        User loggedInUser = (User) (session != null ? session.getAttribute("loggedInUser") : null);

        // If no user is logged in, redirect to login page
        if(loggedInUser == null) {
            if (requiredRole == "") {
                response.sendRedirect(request.getContextPath() + "/auth/login");
            } else {
                response.sendRedirect(request.getContextPath() + "/auth/staff/?action=login");
            }
        }else if(loggedInUser instanceof Employee) {
        	Employee employee = (Employee) loggedInUser;
        	if(requiredRole.equals(employee.getRole())) {
        		return true; // accept if authorized role
        	}

        }else {
        	if(requiredRole == "") {
        		return true; // accept if only customer auth needed
        	}
        }
        return false; //reject authorization
    }
}
