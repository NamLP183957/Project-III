<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
  <jsp:include page="_sidebar.jsp">
    <jsp:param value="true" name="trouble_active" />
  </jsp:include>

  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Lịch sử sự cố > Ghi nhận sự cố mới</p>
    <div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">
        <h2 class="form-title">Ghi nhận sự cố mới</h2>
        <form method="POST"
          action="${pageContext.request.contextPath}/trouble/add">
          
          <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
          <c:remove var="successMessage" scope="session" />
          
          <input type="hidden" name="system_id" value="${system_id }">
          <div class="form-item">
            <label for="name">Mô tả ngắn <span>(*)</span></label>
            <textarea name="short_description" rows="3"
              placeholder="Mô tả ngắn về sự cố" required></textarea>
          </div>

          <div class="form-item">
            <label for="detail">Chi tiết <span>(*)</span></label>
            <textarea name="detail" rows="5"
              placeholder="Chi tiết sự cố" required></textarea>
          </div>

          <div class="form-item">
            <label id="status-label">Trạng thái <span>(*)</span></label>
            <div class="input-container">
              <label for="status"> <input type="radio"
                name="status" value="0" checked> <span
                class="dot"> Chưa xử lý <i class="bg-danger"></i>
              </span>
              </label> <label for="status"> <input type="radio"
                name="status" value="1"> <span class="dot">
                  Đang xử lý <i class="bg-warning"></i>
              </span>
              </label> <label for="status"> <input type="radio"
                name="status" value="2"> <span class="dot">
                  Đã xử lý <i class="bg-success"></i>
              </span>
              </label>
            </div>
          </div>

          <div class="form-item">
            <label for="solution">Khắc phục</label>
            <textarea name="solution" rows="5"
              placeholder="Giải pháp khắc phục sự cố"></textarea>
          </div>

          <div class="form-item">
            <!-- Định dạng value: yyyy/mm/dd -->
            <label for="date">Ngày ghi nhận</label> <input name="date"
              type="date" id="date" required>
          </div>
          <div class="form-item">
            <!-- Định dạng value: hh/MM -->
            <label for="date">Thời điểm ghi nhận</label> <input
              name="time" type="time" id="time" required>
          </div>
          
           <div class="form-item">
            <label>Rủi ro liên quan</label>
            <div class="option_container">
              <div class="tag_container" id="tag_risk_container"></div>
              <div>
                <select id="tag_risk">
                  <option selected disabled="true">Chọn rủi ro liên quan</option>
                  <c:forEach items="${risks}" var="risk">
                    <option value="${risk.id}">${fn:escapeXml(risk.short_description)}</option>
                  </c:forEach>
                </select> <span
                  onclick="addTag('tag_risk', 'tag_risk_container')">
                  <i class="fas fa-plus-circle"></i>
                </span>
              </div>
            </div>
          </div>


          <div class="form-item">
            <label>Tài sản liên quan</label>
            <div class="option_container">
              <div class="tag_container" id="tag_asset_container"></div>
              <div>
                <select id="tag_asset">
                  <option selected disabled="true">Chọn tài sản liên quan</option>
                  <c:forEach items="${assets}" var="asset">
                    <option value="${asset.id}">${fn:escapeXml(asset.name)}</option>
                  </c:forEach>
                </select> <span
                  onclick="addTag('tag_asset', 'tag_asset_container')">
                  <i class="fas fa-plus-circle"></i>
                </span>
              </div>
            </div>
          </div>

          <div class="form-item justify-content-center">
            <button type="submit" name="add"
              onclick="return confirm('Ghi nhận sự cố mới?')" value="">Thêm mới</button>
            <a class="btn btn-type2"
              onclick="return confirm('Thông tin sẽ KHÔNG được lưu?')"
              href="${pageContext.request.contextPath}/trouble">Hủy</a>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- end main content -->
  <!-- import script -->
  <script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/select.js"></script>
  <!-- end import script -->
</body>
</html>