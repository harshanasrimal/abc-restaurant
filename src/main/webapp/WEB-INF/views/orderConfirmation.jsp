<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="shortcut icon" href="assets/images/favicon.png" type="">

  <title>Order Confirmation - ABC Restaurant</title>

  <%@ include file="/WEB-INF/views/includes/head.jsp" %>

</head>

<body class="sub_page">

  <div class="hero_area">
    <div class="bg-box">
      <img src="assets/images/hero-bg.jpg" alt="">
    </div>

    <%@ include file="/WEB-INF/views/includes/header.jsp" %>
  </div>

  <!-- Order Confirmation Section -->
  <section class="confirmation_section layout_padding">
    <div class="container text-center">
      <div class="heading_container">
        <h2>Order Confirmation</h2>
      </div>

      <div class="row">
        <div class="col-md-8 offset-md-2">
          <div class="form_container">
            <c:if test="${not empty message}">
              <div class="alert alert-success">
                ${message}
              </div>
            </c:if>

            <h4>Order Details</h4>
            <p>Order ID: ${orderId}</p>
            <p>Total Amount: ${order.totalAmount}</p>
            <p>Order Type: ${order.type}</p>
            <p>Branch: ${order.branch_id}</p>

            <h5>Items Ordered:</h5>
            <ul>
              <c:forEach var="item" items="${order.items}">
                <li>Product ID: ${item.product_id}, Quantity: ${item.qty}, Total: ${item.total}</li>
              </c:forEach>
            </ul>

            <div class="btn_box">
              <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Go to Home</a>
              <a href="${pageContext.request.contextPath}/orders" class="btn btn-secondary">View Orders</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End Order Confirmation Section -->

  <%@ include file="/WEB-INF/views/includes/footer.jsp" %>

</body>

</html>
