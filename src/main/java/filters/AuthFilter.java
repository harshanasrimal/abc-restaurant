package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Employee;

public class AuthFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String path = req.getRequestURI();
        String action = req.getParameter("action");

        Employee loggedInUser = (Employee) (session != null ? session.getAttribute("loggedInUser") : null);
        
        if (path.startsWith(req.getContextPath() + "/auth/staff") && ("login".equals(action) || "logout".equals(action))) {
            chain.doFilter(request, response); // Allow access to login and logout actions
            return;
        }

        // Redirect logic based on role and access path
        if (path.startsWith(req.getContextPath() + "/admin/")) {
            // Admin paths
            if (loggedInUser == null || !"admin".equals(loggedInUser.getRole())) {
                res.sendRedirect(req.getContextPath() + "/auth/staff/?action=login&error=admin_required");
                return;
            }
        } else if (path.startsWith(req.getContextPath() + "/staff/")) {
            // Staff paths
            if (loggedInUser == null || (!"staff".equals(loggedInUser.getRole()) && !"admin".equals(loggedInUser.getRole()))) {
                res.sendRedirect(req.getContextPath() + "/auth?action=login&error=staff_required");
                return;
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
