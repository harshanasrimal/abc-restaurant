<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <link rel="shortcut icon" href="assets/images/favicon.png" type="">

  <title> ABC Restaurant </title>

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
         Login
        </h2>
      </div>
      <div class="row">
              <div class="col-md-3">
        </div>
        <div class="col-md-6">
          <div class="form_container">
            <form action="" method="POST">
              <div>
                <input name="email" type="email" class="form-control" placeholder="Your Email" />
              </div>
              <div>
                <input name="password" type="password" class="form-control" placeholder="Enter the Password" />
              </div>
              <div class="btn_box">
                <button type="submit">
                  Login
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