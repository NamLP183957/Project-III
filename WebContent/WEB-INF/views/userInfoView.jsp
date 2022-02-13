<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
	<title>Risk Assessment Admin</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/image/logo.png"/>

	<!-- Import lib -->
	<!-- <link rel="stylesheet" type="text/css" href="assets/fontawesome-free/css/all.min.css"> -->
	<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/aa8e5675e3.js" crossorigin="anonymous"></script>
	<!-- End import lib -->

	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style_general.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/form.css">
</head>
<body class="overlay-scrollbar">
	
  <jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp"></jsp:include>
  
	<!-- main content -->
	<div class="wrapper">
		<p class="link_mark">Home > Thông tin tài khoản</p>
		<div class="row align-items-center">
			<div class="col-4 col-m-12 col-sm-12">
				<img class="img_description" src="${pageContext.request.contextPath}/assets/image/user_info.svg">
			</div>
      <div class="col-8 col-m-12 col-sm-12">
				<h2 class="form-title">Thông tin tài khoản</h2>
        <form method="POST" action="${pageContext.request.contextPath}/user/info">
          <div class="form-item">
            <label for="name">Họ tên</label>
            <input type="text" name="name" value="${fn:escapeXml(user.name)}">
          </div>
  
          <div class="form-item">
            <label for="tel">Số điện thoại</label>
            <input type="tel" name="phone" value="${fn:escapeXml(user.phone)}"
						pattern="[0-9]{10}"
						title="Số điện thoại dài 10 chữ số" >
          </div>
  
          <div class="form-item">
            <label for="email">Email</label>
            <input type="email" name="email" value="${fn:escapeXml(user.email)}" readonly>
          </div>
  
          <div class="form-item">
            <label for="company">Tổ chức</label>
            <input type="text" name="organization" value="${fn:escapeXml(user.organization)}">
          </div>
          <div class="form-item">
            <label for="position">Vị trí công việc</label>
            <input type="text" name="position" value="${fn:escapeXml(user.position)}">
          </div>
           <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
          <div class="form-item">
            <button type="submit" class="btn-type1" name="action" 
						onclick="return confirm('Cập nhật thông tin?')"
						value="update-info">Cập nhật thông tin</button>
          </div>
        </form>
			</div>
		</div>
	</div>
	<!-- end main content -->
	<!-- import script -->
	<script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
	<!-- end import script -->
</body>
</html>