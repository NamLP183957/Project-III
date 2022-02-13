<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<head>
  <title>Đăng nhập | Risk Assessment Admin</title>
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

    <form class="login-form" action="${pageContext.request.contextPath}/login" method="post">
      <h2>Đăng nhập</h2>
      <div class="login-container">
        <div class="label-container">
          <label class="label-item" for="username">Tên đăng nhập</label>
          <span>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></span>
        </div>
        <input type="text" placeholder="Nhập tên đăng nhập" name="username" value="${fn:escapeXml(uname)}" required>

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
        <input type="password" placeholder="Nhập mật khẩu" name="password" value="${fn:escapeXml(pwd)}" id="password" required>
        
<!--         <label><input type="checkbox" style="transform: scale(1.5);" checked="checked" name="remember">Lưu thông tin đăng nhập</label> -->
        <p style="color: var(--danger-color);"><c:out value="${errorMessage}"></c:out></p>
        <input type="submit" value="Đăng Nhập">
      </div>
    </form>
  </main>

  <script src="http://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js"></script>
  <script src="assets/js/login_particle.js"></script>
  <script src="assets/js/login.js"></script>
</body>