<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="shortcut icon" href="assets/images/favicon.png" type="">

  <title> New Employee - ABC Restaurant </title>

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
         New Employee
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
                <input name="name" type="text" class="form-control" placeholder="Employee Name" />
              </div>
              <div class="form-group">
                <input name="email" type="email" class="form-control" placeholder="Employee Email" />
              </div>
              <div class="form-group">
                <input name="password" type="password" class="form-control" placeholder="Password" />
              </div>
              <div class="form-group">
                <select name="branch_id" class="form-control">
                  <option value="">Select Branch</option>
                  <c:forEach var="branch" items="${branches}">
                    <option value="${branch.id}">
                      ${branch.location}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="form-group">
                <select name="role" class="form-control">
                  <option value="">Select Role</option>
                  <option value="admin">Admin</option>
                  <option value="staff">Staff</option>
                </select>
              </div>
              <div class="btn_box">
                <button type="submit">
                  Create
                </button>
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
