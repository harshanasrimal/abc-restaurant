<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="shortcut icon" href="assets/images/favicon.png" type="">

  <title> New Category - ABC Restaurant </title>

<%@ include file="/WEB-INF/views/includes/head.jsp" %>

</head>

<body class="sub_page">

  <div class="hero_area">
    <div class="bg-box">
      <img src="assets/images/hero-bg.jpg" alt="">
    </div>
    
<%@ include file="/WEB-INF/views/includes/header.jsp" %>
</div>
  <!-- food section -->

  <section class="food_section layout_padding">
    <div class="container">
      <div class="heading_container heading_center">
        <h2>
          Our Menu
        </h2>
      </div>

<ul class="filters_menu">
    <a href="menu/"><li class="${empty param.cat ? 'active' : ''}">All</li></a>
    <c:forEach items="${categories}" var="category">
        <a href="menu/?cat=${category.id}">
            <li class="${param.cat == category.id ? 'active' : ''}">${category.name}</li>
        </a>
    </c:forEach>
</ul>


      <div class="filters-content">
        <div class="row grid">
<c:forEach items="${products}" var="product">
          <div class="col-sm-6 col-lg-4 all">
            <div class="box">
              <div>
                <div class="img-box">
                  <img src="${product.image}" alt="">
                </div>
                <div class="detail-box">
                  <h5>
                    ${product.name}
                  </h5>
                  <p>
                    ${product.description}
                  </p>
                  <div class="options">
                    <h6>
                      Rs.${product.price}
                    </h6>
                    <span class="add-to-cart" onclick="addToCart(${product.id},'${product.name}',${product.price},'${product.image}')">
                      <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
    </c:forEach>
        </div>
      </div>
      <div class="btn-box">
        <a href="">
          View More
        </a>
      </div>
    </div>
  </section>

  <!-- end food section -->

  <%@ include file="/WEB-INF/views/includes/footer.jsp" %>

</body>

</html>