<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
      <jsp:param name="system_active" value="true" />
    </jsp:include>
    
	<!-- main content -->
	<div class="wrapper">
		<p class="link_mark">Home > Tổng quan hệ thống >Thao tác</p>
		<div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">

				<h2 class="form-title">Cập nhật thông tin</h2>
        <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
<%--         <c:remove var="successMessage" scope="session" /> --%>
        <form method="POST" action="${pageContext.request.contextPath}/system/action" enctype="multipart/form-data">
        
          <input type="hidden" name="id" value="${fn:escapeXml(system.id) }" required>
          <div class="form-item">
            <label for="name">Cập nhật cuối</label>
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${system.modified_time}" var="parsed_modified_time" />
            <input type="text" value='<fmt:formatDate value="${parsed_modified_time}" pattern="HH:mm dd-MM-yyyy" />' disabled readonly>
          </div>
          <div class="form-item">
            <label for="name">Tên hệ thống <span>(*)</span></label>
            <input type="text" name="name" placeholder="Nhập tên hệ thống" value="${fn:escapeXml(system.name) }" required>
          </div>

          <div class="form-item">
            <label for="image">Hình ảnh</label>
            <input type="file" accept="image/*" name="image" id="file" onchange="loadFile(event)" multiple	style="display: none;">
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
            <div id="img-container">
              <c:forEach items="${images}" var="link">
                  <img class="img-item" src="${pageContext.request.contextPath}/${link }">
              </c:forEach>
            </div>
            <script>
              var loadFile = function (event) {
                var container = document.getElementById("img-container");
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
            <textarea name="description" rows="5" placeholder="Nhập mô tả hệ thống" required>${fn:escapeXml(system.description) }</textarea>
          </div>
 
          
          <div class="form-item justify-content-center">
            <button type="submit" name="action" 
						onclick="return confirm('Xác nhận LƯU thay đổi?')"
						value="update">Cập nhật</button>
            <a class="btn btn-type2"
						onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
						href="${pageContext.request.contextPath}/system">Hủy</a>
          </div>
        </form>

    		<h2 class="form-title">Chuyển quyền sở hữu hệ thống</h2>
    		<form method="POST" action="${pageContext.request.contextPath}/system/action">
    			<p>Mọi thông tin về hệ thống sẽ chuyển cho chủ sở hữu mới. Đồng thời bạn sẽ không thể truy cập đến các thông tin của hệ thống.</p>
    		<input type="hidden" name="id" value="${fn:escapeXml(system.id) }" required>	
          <div class="form-item">
    					<label for="new-owner">Chủ sở hữu mới</label>
    					<input type="text" name="new-owner" placeholder="Nhập tài khoản của chủ sở hữu mới">
    			</div>
    			<div class="form-item">
    				<label for="new-owner-confirm">Nhập lại</label>
    				<input type="text" name="new-owner-confirm" placeholder="Nhập lại tài khoản của chủ sở hữu mới">
    			</div>
    			<div class="form-item">
    				<label for="password-confirm">Mật khẩu</label>
    				<input type="password" name="password-confirm" placeholder="Nhập mật khẩu để xác nhận thực hiện">
    			</div>
                <p style="color: var(--danger-color);"><c:out value="${ errorMessage2}"></c:out></p>
                <c:remove var="errorMessage2" scope="session" />
    			<div class="form-item">
    				<button class="btn-danger" type="submit" name="action" 
    				value="change-owner" 
    				onclick="return confirm('Bạn chắc chắn muốn CHUYỂN QUYỀN SỞ HỮU hệ thống?')">Chuyển quyền sở hữu</button>
    			</div>
    			
    		</form>
    
    
    		<h2 class="form-title">Xóa hệ thống</h2>
    		<form method="POST" action="${pageContext.request.contextPath}/system/action">
        <input type="hidden" name="id" value="${fn:escapeXml(system.id) }" required>
    			<p>Hệ thống bị xóa sẽ không thể hoàn tác. Mọi thông tin gắn với hệ thống như tài sản, đánh giá rủi ro, lịch sử sự cố sẽ bị xóa. <br>Hãy cân nhắc chắc chắn.</p>
    			<p>Nhập <span style="color: var(--danger-color);">"I want to delete this system"</span> và mật khẩu để xác nhận thực hiện.</p>
    			<div class="form-item">
    				<label for="text_confirm">Xác nhận</label>
    				<input type="text" name="text_confirm" 
    				placeholder='Nhập "I want to delete this system"' 
    				title='Nhập "I want to delete this system"'
    				required>
    			</div>
    			<div class="form-item">
    				<label for="password_confirm">Mật khẩu</label>
    				<input type="password" name="password_confirm" placeholder="Nhập mật khẩu để xác nhận thực hiện" required>
    			</div>
                <p style="color: var(--danger-color);"><c:out value="${ errorMessage3}"></c:out></p>
                <c:remove var="errorMessage3" scope="session" />
    			<div class="form-item">
    				<button class="btn-danger" type="submit" name="action" 
    				onclick="return confirm('Bạn chắc chắn muốn XÓA HỆ THỐNG và các thông tin liên quan?')"
    				value="delete">Xóa hệ thống</button>
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