<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="shortcut icon" href="assets/images/favicon.png" type="">

  <title> New Product - ABC Restaurant </title>

<%@ include file="/WEB-INF/views/includes/head.jsp" %>

</head>

<body class="sub_page">

  <div class="hero_area">
    <div class="bg-box">
      <img src="assets/images/hero-bg.jpg" alt="">
    </div>
    
<%@ include file="/WEB-INF/views/includes/header.jsp" %>
</div>
    <!-- start section -->
  <section class="book_section layout_padding">
    <div class="container text-center">
      <div class="heading_container" style="align-items:center">
        <h2>
         New Product
        </h2>
      </div>
      <div class="row">
              <div class="col-md-3">
        </div>
        <div class="col-md-6">
<c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
</c:if>
<div class="form_container">
  <form action="" method="POST">
      <div class="form-group">
          <label for="name">Product Name</label>
          <input name="name" type="text" class="form-control" placeholder="Product Name" />
      </div>

      <div class="form-group">
          <label for="description">Description</label>
          <textarea name="description" class="form-control" rows="3" style="height:unset"></textarea>
      </div>
     
      <div class="form-group">
          <label for="image">Image</label>
          <input name="image" type="text" class="form-control" placeholder="Product Image URL" />
      </div>

      <div class="form-group">
          <label for="price">Price</label>
          <input name="price" type="text" class="form-control" placeholder="Price" />
      </div>

      <div class="form-group">
          <label for="category_id">Category</label>
          <select name="category_id" class="form-control">
              <!-- Dynamically render the list of categories from the backend -->
              <c:forEach var="category" items="${categories}">
                  <option value="${category.id}">${category.name}</option>
              </c:forEach>
          </select>
      </div>

      <div class="btn_box">
          <button type="submit" class="btn btn-primary">Create</button>
      </div>
  </form>
</div>

        </div>
        <div class="col-md-3">
        </div>
      </div>
    </div>
  </section>
  <!-- end section -->

  <%@ include file="/WEB-INF/views/includes/footer.jsp" %>

</body>

</html>