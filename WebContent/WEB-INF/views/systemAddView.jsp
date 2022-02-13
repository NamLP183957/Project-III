<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
  
    <jsp:include page="_sidebar.jsp">
      <jsp:param name="system_active" value="true" />
    </jsp:include>

	<!-- main content -->
	<div class="wrapper">
		<p class="link_mark">Home > Tổng quan hệ thống > Tạo hệ thống</p>
		<div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">
		<h2 class="form-title">Tạo hệ thống mới</h2>
        <form method="POST" action="${pageContext.request.contextPath}/system/add" enctype="multipart/form-data">
          <div class="form-item">
            <label for="name">Tên hệ thống <span>(*)</span></label>
            <input type="text" name="name" value="" required>
          </div>

          <div class="form-item">
            <label for="image">Hình ảnh</label>
            <input type="file" accept="image/*" name="image" id="file" onchange="loadFile(event)" multiple style="display: none;">
			<label for="file" id="btn-upload-file" class="btn-file">
				<i class="fas fa-upload"></i>
				<span>Tải ảnh lên</span>
			</label>
			<label id="btn-clear-file" class="btn-file" onclick="resetFile()">
				<i class="fas fa-trash"></i>
				<span>Reset</span>
			</label>
					
          </div>

          <div class="form-item">
						<label>Preview ảnh</label>
            <div id="img-container"></div>
            <script>
              var loadFile = function (event) {
                var container = document.getElementById("img-container");
								container.innerHTML = "";
                // var image = document.getElementById('output');
                // image.src = URL.createObjectURL(event.target.files[0]);
                for(var i = 0; i < event.target.files.length; ++i) {
                  var img = document.createElement("img");
                  img.classList.add("img-item");
                  img.src = URL.createObjectURL(event.target.files[i]);
                  container.appendChild(img);
                } 
              };

			var resetFile = function() {
				var input = document.getElementById('file');
				input.value = "";
				var container = document.getElementById("img-container");
				container.innerHTML = "";
			};
            </script>
          </div>

          <div class="form-item">
            <label for="description">Mô tả <span>(*)</span></label>
            <textarea name="description" rows="5" required></textarea>
          </div>
  
          
          <div class="form-item justify-content-center">
            <button type="submit" name="add"
						onclick="return confirm('Tạo hệ thống mới?')"
						value="">Tạo mới</button>
            <a class="btn btn-type2" 
			onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
			href="${pageContext.request.contextPath}/system">Hủy</a>
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