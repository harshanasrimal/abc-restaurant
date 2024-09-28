<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <!-- header section starts -->
  <header class="header_section">
    <div class="container">
      <nav class="navbar navbar-expand-lg custom_nav-container ">
        <a class="navbar-brand" href="index.html">
          <span>
            ABC Restaurant
          </span>
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class=""> </span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav  mx-auto ">
<c:choose>
    <c:when test="${isStaff}">
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories/?action=list">Categories</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/admin/products/?action=list">Products</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/admin/branches/?action=list">Branches</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${pageContext.request.contextPath}/admin/employees/?action=list">Employees</a>
            </li>
    </c:when>
    <c:otherwise>
            <li class="nav-item">
              <a class="nav-link" href="http://localhost:8080/abc-restaurant/">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="menu/">Menu</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="about.html">About</a>
            </li>
    </c:otherwise>
</c:choose>
          </ul>
          <div class="user_option">
            <div class="dropdown">
              <c:if test="${not empty loggedInUser}">
                <a href="#" class="user_link dropdown-toggle" id="userDropdown" data-toggle="dropdown"
                  aria-haspopup="true" aria-expanded="false">
                  <i class="fa fa-user" aria-hidden="true"></i> ${loggedInUser.name }
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                  <a class="dropdown-item" href="profile.html">Profile</a>
                  <a class="dropdown-item" href="orders.html">Orders</a>
<c:choose>
    <c:when test="${isStaff}">
        <a class="dropdown-item" href="${pageContext.request.contextPath}/auth/staff/?action=logout">Logout</a>
    </c:when>
    <c:otherwise>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/auth/?action=logout">Logout</a>
    </c:otherwise>
</c:choose>
                </div>
              </c:if>
              <c:if test="${empty loggedInUser}">
                <a href="#" class="user_link dropdown-toggle" id="userDropdown" data-toggle="dropdown"
                  aria-haspopup="true" aria-expanded="false">
                  <i class="fa fa-sign-in" aria-hidden="true"></i> Login / Register
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                  <a class="dropdown-item" href="${pageContext.request.contextPath}/auth/?action=login">Login</a>
                  <a class="dropdown-item" href="${pageContext.request.contextPath}/auth/?action=register">Register</a>
                  <a class="dropdown-item" href="${pageContext.request.contextPath}/auth/staff/?action=login">Staff Login</a>
                </div>
              </c:if>
            </div>
            <c:if test="${not isStaff}">
            <button class="order_online" type="button" data-toggle="modal" data-target="#cartDrawer">
              <i class="fas fa-shopping-cart"></i> Cart
            </button>
            </c:if>
          </div>
        </div>
      </nav>
    </div>
  </header>
  <!-- end header section -->