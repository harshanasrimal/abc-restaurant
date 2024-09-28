<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="shortcut icon" href="assets/images/favicon.png" type="">

  <title>Error - ABC Restaurant</title>

  <%@ include file="/WEB-INF/views/includes/head.jsp" %>

</head>

<body class="sub_page">

  <div class="hero_area">
    <div class="bg-box">
      <img src="assets/images/hero-bg.jpg" alt="">
    </div>

    <%@ include file="/WEB-INF/views/includes/header.jsp" %>
  </div>

  <!-- Error Section -->
  <section class="error_section layout_padding">
    <div class="container text-center">
      <div class="heading_container">
        <h2>Error</h2>
      </div>

      <div class="row">
        <div class="col-md-8 offset-md-2">
          <div class="form_container">
            <div class="alert alert-danger">
              <h4>Oops! Something went wrong.</h4>
              <p>${errorMessage}</p>
            </div>

            <div class="btn_box">
              <a href="${pageContext.request.contextPath}/cart" class="btn btn-primary">Go back to Cart</a>
              <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Go to Home</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- End Error Section -->

  <%@ include file="/WEB-INF/views/includes/footer.jsp" %>

</body>

</html>
