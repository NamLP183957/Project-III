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
<link rel="icon" type="image/png" href="assets/image/logo.png" />

<!-- Import lib -->
<!-- <link rel="stylesheet" type="text/css" href="assets/fontawesome-free/css/all.min.css"> -->
<link
  href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
  rel="stylesheet">
<script src="https://kit.fontawesome.com/aa8e5675e3.js"
  crossorigin="anonymous"></script>
<!-- End import lib -->

<link rel="stylesheet" type="text/css" href="assets/css/style.css">
<link rel="stylesheet" type="text/css"
  href="assets/css/style_general.css">
</head>
<body class="overlay-scrollbar">
  <jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp">
    <jsp:param value="true" name="asset_active" />
  </jsp:include>

  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Quản lý tài sản</p>
    
    <c:choose>
      <c:when test="${assets == null || assets.size() == 0}">
        <div class="row justify-content-center">
          <div class="col-6 col-m-12 col-sm-12">
            <p class="notice">Thông tin về tài sản trống</p>
            <img class="img_description" src="assets/image/no_asset.svg">

            <a class="link_btn" href="${pageContext.request.contextPath}/asset/add">Thêm tài sản mới</a>
          </div>

        </div>
      </c:when>
      <c:otherwise>
        <div class="row justify-content-center">
      <div class="col-12 col-m-12 col-sm-12">
        <div class="card">
          <div class="card-header">
            <h3>Danh sách tài sản</h3>
            <a href="${pageContext.request.contextPath}/asset/add"> <i class="fas fa-plus-circle"></i>
            </a>
          </div>

          <div class="card-content">
            <p>Số lượng: ${assets.size()}</p>
            <table>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Tên tài sản</th>
                  <!-- <th>Hình ảnh</th> -->
                  <th>Mô tả</th>
                  <th>Thời điểm tạo</th>
                  <th>Cập nhật cuối</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${assets }" var="asset">
                  <tr class="row_link" data-link="asset/action?id=${asset.id}"
                  onclick="clickRow(event)">
                    <td>${fn:escapeXml(asset.id) }</td>
                    <td>${fn:escapeXml(asset.name) }</td>
                    <td>${fn:escapeXml(asset.description) }</td>
                    <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${asset.created_time}" var="parsed_created_time" />
                    <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${asset.modified_time}" var="parsed_modified_time" />
                    <td><fmt:formatDate value="${parsed_created_time}" pattern="HH:mm dd-MM-yyyy" /></td>
                    <td><fmt:formatDate value="${parsed_modified_time}" pattern="HH:mm dd-MM-yyyy" /></td>
                  </tr>
                </c:forEach>
                
              </tbody>
            </table>
          </div>
        </div>



      </div>

    </div>
      </c:otherwise>
    </c:choose>
    
  </div>
  <!-- end main content -->
  <!-- import script -->
  <script src="assets/js/index.js"></script>
  <!-- end import script -->
</body>
</html>