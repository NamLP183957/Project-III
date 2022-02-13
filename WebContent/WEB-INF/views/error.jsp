<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<title>Risk Assessment Admin</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="icon" type="image/png" href="assets/image/logo.png"/>

	<!-- Import lib -->
	<!-- <link rel="stylesheet" type="text/css" href="assets/fontawesome-free/css/all.min.css"> -->
	<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/aa8e5675e3.js" crossorigin="anonymous"></script>
	<!-- End import lib -->

	<link rel="stylesheet" type="text/css" href="assets/css/style.css">
  <link rel="stylesheet" type="text/css" href="assets/css/style_general.css">
</head>
<body class="overlay-scrollbar">
	<jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp">
    <jsp:param value="true" name="report_active"/>
  </jsp:include>
  
	<!-- main content -->
	<div class="wrapper">
		<div class="row justify-content-center">
			<div class="col-6 col-m-12 col-sm-12">
				<p class="notice">Đã có lỗi xảy ra! Mời bạn đăng nhập lại!</p>
				<img class="img_description" src="assets/image/error.svg">
        
				<a class="link_btn" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
			</div>
		
		</div>
	</div>
	<!-- end main content -->
	<!-- import script -->
	<script src="assets/js/index.js"></script>
	<!-- end import script -->
</body>
</html>