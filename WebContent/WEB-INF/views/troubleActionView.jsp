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
    <p class="link_mark">Home > Lịch sử sự cố > Sự cố #${trouble.id}</p>
    <div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">

        <h2 class="form-title">Chi tiết thông tin</h2>
        <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
        <c:remove var="successMessage" scope="session" />
        <form method="POST"
          action="${pageContext.request.contextPath}/trouble/action">
          <input type="hidden" name="id" value="${trouble.id }">
          
          <div class="form-item">
            <label for="name">Cập nhật cuối</label>
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${trouble.modified_time}" var="parsed_modified_time" />
            <input type="text" value='<fmt:formatDate value="${parsed_modified_time}" pattern="HH:mm dd-MM-yyyy" />' disabled readonly>
          </div>
          
          <div class="form-item">
            <label for="name">Mô tả ngắn <span>(*)</span></label>
            <textarea name="short_description" rows="3"
              placeholder="Mô tả ngắn về sự cố" required>${fn:escapeXml(trouble.short_description) }</textarea>
          </div>

          <div class="form-item">
            <label for="detail">Chi tiết <span>(*)</span></label>
            <textarea name="detail" rows="5"
              placeholder="Chi tiết sự cố" required>${fn:escapeXml(trouble.detail) }</textarea>
          </div>

          <div class="form-item">
            <label id="status-label">Trạng thái <span>(*)</span></label>
            <div class="input-container">
              <label for="status"> 
                <input type="radio" name="status" value="0" ${trouble.status == 0 ? "checked" : ""}> 
                <span class="dot" style="color: var(--danger-color);"> Chưa xử lý <i class="bg-danger"></i></span>
              </label> 
              
              <label for="status"> 
                <input type="radio" name="status" value="1" ${trouble.status == 1 ? "checked" : ""}> 
                <span class="dot" style="color: var(--warning-color);"> Đang xử lý <i class="bg-warning"></i></span>
              </label>
              
             <label for="status"> 
                <input type="radio" name="status" value="2" ${trouble.status == 2 ? "checked" : ""}> 
                <span class="dot" style="color: var(--success-color);"> Đã xử lý <i class="bg-success"></i></span>
              </label>
            </div>
          </div>

          <div class="form-item">
            <label for="solution">Khắc phục</label>
            <textarea name="solution" rows="5"
              placeholder="Giải pháp khắc phục sự cố">${fn:escapeXml(trouble.solution) }</textarea>
          </div>

          <div class="form-item">
            <!-- Định dạng value: yyyy/mm/dd -->
            <label for="date">Ngày ghi nhận <span>(*)</span></label> 
            <input name="date"
              type="date" id="date" value='${trouble.time_happen.split(" ")[0]}' required>
          </div>
          <div class="form-item">
            <!-- Định dạng value: hh/MM -->
            <label for="date">Thời điểm ghi nhận <span>(*)</span></label> 
            <input
              name="time" type="time" id="time"
              value='${trouble.time_happen.split(" ")[1]}' required>
          </div>

          <div class="form-item">
            <label>Rủi ro liên quan</label>
            <div class="option_container">
              <div class="tag_container" id="tag_risk_container">
                 <c:forEach items="${risks}" var="risk">
                     <div>
                      <i class="fas fa-minus-circle" onclick="removeTag('tag_risk', event)"></i>
                      <input type="hidden" name="tag_risk" value="${risk.id}">
                      <input type="text" readonly value="${fn:escapeXml(risk.short_description)}" disabled>
                    </div>
                </c:forEach>
              </div>
              <div>
                <select id="tag_risk">
                  <option selected disabled="true">Chọn rủi ro
                    liên quan</option>
                  <c:forEach items="${allRisks}" var="risk">
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
              <div class="tag_container" id="tag_asset_container">
                <c:forEach items="${assets}" var="asset">
                     <div>
                      <i class="fas fa-minus-circle" onclick="removeTag('tag_asset', event)"></i>
                      <input type="hidden" name="tag_asset" value="${asset.id}">
                      <input type="text" readonly value="${fn:escapeXml(asset.name)}" disabled>
                    </div>
                </c:forEach>
              </div>
              <div>
                <select id="tag_asset">
                  <option selected disabled="true">Chọn tài sản liên quan</option>
                  <c:forEach items="${allAssets}" var="asset">
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
            <button type="submit" name="action"
              onclick="return confirm('Xác nhận LƯU thay đổi?')"
              value="update">Cập nhật</button>
            <a class="btn btn-type2"
              onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
              href="${pageContext.request.contextPath}/trouble">Hủy</a>
          </div>
        </form>

        <h2 class="form-title">Xóa sự cố</h2>
        <form method="POST"
          action="${pageContext.request.contextPath}/trouble/action">
          <input type="hidden" name="id" value="${trouble.id }">
          <p>Sự cố bị xóa sẽ không thể hoàn tác. Hãy cân nhắc chắc
            chắn.</p>
          <div class="form-item">
            <button class="btn-danger" type="submit" name="action"
              onclick="return confirm('Bạn chắc chắn muốn XÓA sự cố #${trouble.id}?')"
              value="delete">Xóa sự cố</button>
          </div>

        </form>
      </div>
    </div>
  </div>
  <!-- end main content -->
  <!-- import script -->
  <script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/select.js"></script>
  <script type="text/javascript">
    window.onload = () => {
    	checkTag('tag_risk', 'tag_risk_container');
      	checkTag('tag_asset', 'tag_asset_container');
    }
  </script>
  <!-- end import script -->
</body>

</html>