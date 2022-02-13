<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
    <jsp:param value="true" name="asset_active" />
  </jsp:include>


	<!-- main content -->
	<div class="wrapper">
		<p class="link_mark">Home > Tổng quan hệ thống > Tài sản #${fn:escapeXml(asset.id) }</p>
		<div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">

				<h2 class="form-title">Cập nhật thông tin</h2>
        <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
        <c:remove var="successMessage" scope="session" />
        <form method="POST" action="${pageContext.request.contextPath}/asset/action">
          
          
          <input type="hidden" name="id" value="${fn:escapeXml(asset.id) }" required>
          
          <div class="form-item">
            <label for="name">Cập nhật cuối</label>
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${asset.modified_time}" var="parsed_modified_time" />
            <input type="text" value='<fmt:formatDate value="${parsed_modified_time}" pattern="HH:mm dd-MM-yyyy" />' disabled readonly>
          </div>
          
          <div class="form-item">
            <label for="name">Tên tài sản <span>(*)</span></label>
            <input type="text" name="name" placeholder="Nhập tên tài sản"  value="${fn:escapeXml(asset.name) }" required>
          </div>

          <!-- <div class="form-item">
            <label for="image">Hình ảnh</label>
            <input type="file" accept="image/*" name="image" id="file" onchange="loadFile(event)" style="display: none;">
						<label for="file" id="btn-upload-file" class="btn-file">
							<i class="fas fa-upload"></i>
							<span>Tải ảnh lên</span>
						</label>
						<label id="btn-clear-file" class="btn-file" onclick="resetFile()">
							<i class="fas fa-trash"></i>
							<span>Reset</span>
						</label>
						
					  <img id="output" width="200" />
					
          </div>

          <div class="form-item">
						<label>Preview ảnh</label>
            <div id="img-container">
              <img id="img-item" src="">
            </div>
            <script>
              var loadFile = function (event) {
                var image = document.getElementById('img-item');
                image.src = URL.createObjectURL(event.target.files[0]);
              };

							var resetFile = function() {
								var input = document.getElementById('file');
								input.value = "";
								var image = document.getElementById('img-item');
                image.src = "";
							};
            </script>
          </div> -->

          <div class="form-item">
            <label for="description">Mô tả <span>(*)</span></label>
            <textarea name="description" rows="5" placeholder="Nhập mô tả tài sản" required>${fn:escapeXml(asset.description) }</textarea>
          </div>
          
          <div class="form-item justify-content-center">
            <button type="submit" name="action" 
						onclick="return confirm('Xác nhận LƯU thay đổi?')"
						value="update">Cập nhật</button>
            <a class="btn btn-type2"
						onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
						href="${pageContext.request.contextPath}/asset">Hủy</a>
          </div>
        </form>

				<h2 class="form-title">Xóa tài sản</h2>
				<form method="POST" action="${pageContext.request.contextPath}/asset/action">
					<p>Tài sản bị xóa sẽ không thể hoàn tác. Hãy cân nhắc chắc chắn.</p>
          <input type="hidden" name="id" value="${fn:escapeXml(asset.id) }" required>
					<div class="form-item">
						<button class="btn-danger" type="submit" name="action" 
						onclick="return confirm('Bạn chắc chắn muốn XÓA tài sản #${asset.id}?')"
						value="delete">Xóa tài sản</button>
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