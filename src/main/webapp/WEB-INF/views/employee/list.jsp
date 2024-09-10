<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="shortcut icon" href="assets/images/favicon.png" type="">

  <title> Employees - ABC Restaurant </title>

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
      <div class="row">
        <div class="col-md-6">
          <div class="heading_container">
            <h2>
              Our Employees
            </h2>
          </div>
        </div>
        <div class="col-md-6">
          <a href="admin/employees/?action=create" class="btn btn-primary" style="float: right;"><i class="fas fa-plus"></i> New Employee</a>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <c:if test="${not empty error}">
              <div class="alert alert-danger">
                  ${error}
              </div>
          </c:if>
          <table class="table">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Name</th>
                <th scope="col">Email</th>
                <th scope="col">Branch</th>
                <th scope="col">Role</th>
                <th scope="col">Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${employees}" var="employee">
                <tr>
                  <th scope="row">${ employee.id }</th>
                  <td>${ employee.name }</td>
                  <td>${ employee.email }</td>
                  <td>${ employee.branch_id }</td>
                  <td>${ employee.role }</td>
                  <td>
                    <a href="admin/employees/?action=update&id=${ employee.id }"><i class="fas fa-edit"></i></a> | 
                    <a href="admin/employees/?action=delete&id=${ employee.id }"><i class="fas fa-trash"></i></a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </section>
  <!-- end section -->

  <%@ include file="/WEB-INF/views/includes/footer.jsp" %>

</body>

</html>
