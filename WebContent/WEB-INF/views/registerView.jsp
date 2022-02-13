<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>

<head>
  <title>Đăng ký | Risk Assessment Admin</title>
  <meta charset="UTF-8">
  <meta name="viewport"
    content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
  <link rel="icon" type="image/png" href="assets/image/logo.png" />

  <!-- Import lib -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Alfa+Slab+One&display=swap" rel="stylesheet">
  <script src="https://kit.fontawesome.com/aa8e5675e3.js" crossorigin="anonymous"></script>
  <!-- End import lib -->

  <link rel="stylesheet" type="text/css" href="assets/css/style.css">
  <link rel="stylesheet" type="text/css" href="assets/css/login.css">  
</head>

<body>
  <main class="main-content">
    <div id="particles-js"></div>
    <img src="assets/image/logo_side-2_line-white-crop.png" alt="logo" class="logo">
    <img src="assets/image/admin.svg" alt="login img" class="img-side">

    <form class="login-form" action="${pageContext.request.contextPath}/register" method="post">
      <h2>Đăng Ký</h2>
      <div class="login-container">
        <div class="label-container">
          <label class="label-item" for="username">Tên tài khoản</label>
          <span>Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></span>
        </div>

        <input type="text" name="username"
          placeholder="Nhập tên đăng nhập"
          value="${fn:escapeXml(uname)}"
          pattern="[a-zA-Z][a-zA-Z0-9_]{2,19}"
          title="Tên tài khoản chỉ chứa chữ cái, số hoặc dấu gạch dưới; phải bắt đầu bằng chữ cái; dài từ 3 đến 20 ký tự"
          required
        >
        
        <div class="label-container">
          <label class="label-item" for="email">Email</label>
        </div>
        <input type="email" name="email" value="${fn:escapeXml(email)}" placeholder="Nhập email" required>       
        
        <div class="label-container">
          <label class="label-item" for="password">Mật khẩu</label>
          <div class="show-hide-pwd" id="show-pwd" onclick="showPwd('hide-pwd', 'show-pwd', 'password')">
            <i class="fas fa-eye"></i>
            <span>Hiện</span>
          </div>
          <div class="show-hide-pwd hide" id="hide-pwd" onclick="hidePwd('hide-pwd', 'show-pwd', 'password')">
            <i class="fas fa-eye-slash"></i>
            <span>Ẩn</span>
          </div>
        </div>
        <input type="password" name="password" id="password" 
          placeholder="Nhập mật khẩu"
          pattern="[a-zA-Z0-9]{3,20}"
          value="${fn:escapeXml(pwd)}"
          title="Mật khẩu chỉ chứa chữ cái hoặc số; dài từ 3 đến 20 ký tự" 
          oninput="check_pass()"
          required
        >


        <div class="label-container">
          <label class="label-item" for="password">Nhập lại mật khẩu</label>
          <div class="show-hide-pwd" id="show-pwd2" onclick="showPwd('hide-pwd2', 'show-pwd2', 'password-retype')">
            <i class="fas fa-eye"></i>
            <span>Hiện</span>
          </div>
          <div class="show-hide-pwd hide" id="hide-pwd2" onclick="hidePwd('hide-pwd2', 'show-pwd2', 'password-retype')">
            <i class="fas fa-eye-slash"></i>
            <span>Ẩn</span>
          </div>
        </div>
        <input type="password" name="password-confirm" id="password-retype" 
        placeholder="Nhập lại mật khẩu"
        value="${fn:escapeXml(pwd)}"
        oninput="check_pass()" 
        required
        >
        <p style="color: var(--danger-color);" id="error-mesg"><c:out value="${ errorMessage1}"></c:out></p>
        <p style="color: var(--danger-color);"><c:out value="${ errorMessage2}"></c:out></p>
        <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
        <button type="submit" value="Register" id="submit" class="sign-up-btn">Đăng ký miễn phí</button>
        <p class="sign-up-notice">Bằng việc nhấn vào nút "Đăng ký miễn phí", bạn sẽ được tạo tài khoản, và chấp nhận 
          <a href="#">Điều khoản sử dụng</a> và <a href="#">Chính sách bảo mật</a> của chúng tôi
        </p>
      </div>
    </form>
  </main>

      
      




  <script src="http://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js"></script>
  <script src="assets/js/login_particle.js"></script>
  <script src="assets/js/login.js"></script>
</body>