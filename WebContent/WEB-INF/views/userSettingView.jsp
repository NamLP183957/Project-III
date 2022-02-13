<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Risk Assessment Admin</title>
<meta charset="UTF-8">
<meta name="viewport"
  content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<link rel="icon" type="image/png"
  href="${pageContext.request.contextPath}/assets/image/logo.png" />

<!-- Import lib -->
<!-- <link rel="stylesheet" type="text/css" href="assets/fontawesome-free/css/all.min.css"> -->
<link
  href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
  rel="stylesheet">
<script src="https://kit.fontawesome.com/aa8e5675e3.js"
  crossorigin="anonymous"></script>
<!-- End import lib -->

<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/assets/css/style.css">
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/assets/css/style_general.css">
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/assets/css/form.css">
</head>
<body class="overlay-scrollbar">

  <jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp"></jsp:include>

  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Cài đặt</p>
    <div class="row align-items-center">
      <div class="col-4 col-m-12 col-sm-12">
        <img class="img_description"
          src="${pageContext.request.contextPath}/assets/image/setting.svg">
      </div>
      <div class="col-8 col-m-12 col-sm-12">
        <h2 class="form-title">Thay đổi mật khẩu</h2>
        <form method="POST"
          action="${pageContext.request.contextPath}/user/setting">
          <p style="color: var(--danger-color);">
            <c:out value="${ errorMessage1}"></c:out>
          </p>
          <c:remove var="errorMessage1" scope="session" />
          <p style="color: var(--success-color);">
            <c:out value="${successMessage}"></c:out>
          </p>
          <c:remove var="successMessage" scope="session" />
          <div class="form-item">
            <label for="password">Mật khẩu cũ</label> <input
              type="password" name="old_password"
              pattern="[a-zA-Z0-9]{3,20}"
              title="Mật khẩu chỉ chứa chữ cái hoặc số; dài từ 3 đến 20 ký tự"
              placeholder="Nhập mật khẩu cũ" required>
          </div>

          <div class="form-item">
            <label for="new_password">Mật khẩu mới</label> <input
              type="password" name="new_password" id="password"
              oninput="check_pass()" pattern="[a-zA-Z0-9]{3,20}"
              title="Mật khẩu chỉ chứa chữ cái hoặc số; dài từ 3 đến 20 ký tự"
              placeholder="Nhập mật khẩu mới" required>
          </div>

          <div class="form-item">
            <label for="retype_password">Nhập lại</label> <input
              type="password" id="password-retype"
              oninput="check_pass()" placeholder="Nhập lại mật khẩu mới"
              required>
          </div>
           <p style="color: var(--danger-color);" id="error-mesg"></p>
          <div class="form-item">
            <button class="btn-type1" type="submit" name="action"
              id="submit"
              onclick="return confirm('Cập nhật tài khoản?')"
              value="update-password" disabled>Cập nhật</button>
          </div>
        </form>

        <h2 class="form-title">Xóa tài khoản</h2>
        <form method="POST"
          action="${pageContext.request.contextPath}/user/setting">
          <p>Tài khoản bị xóa sẽ không thể hoàn tác. Hãy cân nhắc
            chắc chắn.</p>
          <p>
            Nhập <span style="color: var(--danger-color);">"I
              want to delete my account"</span> và mật khẩu để xác nhận thực
            hiện.
          </p>
          <div class="form-item">
            <label for="text_confirm">Xác nhận</label> <input
              type="text" name="text_confirm"
              placeholder='Nhập "I want to delete my account"'
              title='Nhập "I want to delete my account"' required>
          </div>
          <div class="form-item">
            <label for="password_confirm">Mật khẩu</label> <input
              type="password" name="password_confirm"
              placeholder="Nhập mật khẩu để xác nhận thực hiện" required>
          </div>

          <p style="color: var(--danger-color);">
            <c:out value="${ errorMessage2}"></c:out>
          </p>
          <c:remove var="errorMessage2" scope="session" />

          <div class="form-item">
            <button class="btn-danger" type="submit" name="action"
              onclick="return confirm('Bạn chắc chắn muốn XÓA tài khoản?')"
              value="delete-account">Xóa tài khoản</button>
          </div>

        </form>
      </div>
    </div>
  </div>
  <!-- end main content -->
  <!-- import script -->
  <script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/login.js"></script>
  <!-- end import script -->
</body>

</html>