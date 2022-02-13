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
  <link rel="icon" type="image/png" href="assets/image/logo.png"/>

  <!-- Import lib -->
  <!-- <link rel="stylesheet" type="text/css" href="assets/fontawesome-free/css/all.min.css"> -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
  <script src="https://kit.fontawesome.com/aa8e5675e3.js" crossorigin="anonymous"></script>
  <!-- End import lib -->

  <link rel="stylesheet" type="text/css" href="assets/css/style.css">
  <link rel="stylesheet" type="text/css" href="assets/css/style_general.css">
  <link rel="stylesheet" type="text/css" href="assets/css/risk.css">
</head>
<body class="overlay-scrollbar">

  <jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp">
    <jsp:param value="true" name="trouble_active"/>
  </jsp:include>
  
  
  
  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Lịch sử sự cố</p>
    <c:choose>
    <c:when test="${troubles == null || troubles.size() == 0 }">
      <div class="row justify-content-center">
      <div class="col-6 col-m-12 col-sm-12">
        <p class="notice">Chưa ghi nhận sự cố</p>
        <img class="img_description" src="assets/image/no_history.svg">
        
        <a class="link_btn" href="${pageContext.request.contextPath}/trouble/add">Ghi nhận sự cố mới</a>
      </div>
    
    </div>
    </c:when>
    <c:otherwise>
    <div class="col-3 col-m-12 col-sm-12">
        <label for="filter" class="label-filter">Lọc theo trạng thái</label> 
        <select id="filter" onchange="filterTable()">
          <option value="all" selected>Tất cả</option>
          <option style="color: var(--danger-color);" value="Chưa xử lý">Chưa xử lý</option>
          <option style="color: var(--warning-color);" value="Đang xử lý">Đang xử lý</option>
          <option style="color: var(--success-color);" value="Đã xử lý">Đã xử lý</option>
        </select>
      </div>
      <div class="row justify-content-center">
      <div class="col-12 col-m-12 col-sm-12">
        <div class="card">
          <div class="card-header">
            <h3>Danh sách sự cố</h3>
            <a href="${pageContext.request.contextPath}/trouble/add">
              <i class="fas fa-plus-circle"></i>
            </a>
          </div>

          <div class="card-content">
            <p>Số lượng: <span id="num-trouble">${troubles.size()}</span></p>
            <table>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Mô tả ngắn</th>
                  <th>Chi tiết</th>
                  <th>Tình trạng</th>
                  <!-- <th>Khắc phục</th> -->
                  <th>Ghi nhận</th>
                  <th>Cập nhật</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${troubles}" var="trouble">
                <tr class="row_link" data-link="trouble/action?id=${trouble.id}" onclick="clickRow(event)">
                  <td>${trouble.id}</td>
                  <td>${fn:escapeXml(trouble.short_description)}</td>
                  <td>${fn:escapeXml(trouble.detail)}</td>
                  <td>
                  <c:choose>
                    <c:when test="${trouble.status == 0}">
                      <span class="dot" style="color: var(--danger-color);"><i class="bg-danger"></i>Chưa xử lý</span>
                    </c:when>
                    <c:when test="${trouble.status == 1}">
                      <span class="dot" style="color: var(--warning-color);"><i class="bg-warning"></i>Đang xử lý</span>
                    </c:when>
                    <c:when test="${trouble.status == 2}">
                      <span class="dot" style="color: var(--success-color);"><i class="bg-success"></i>Đã xử lý</span>
                    </c:when>
                  </c:choose>
                    
                  </td>
                  
                  <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${trouble.time_happen}" var="parsed_time_happen" />
                  <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${trouble.modified_time}" var="parsed_modified_time" />
                  <td><fmt:formatDate value="${parsed_time_happen}" pattern="HH:mm dd-MM-yyyy" /></td>
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
  <script src="assets/js/trouble_view.js"></script>
  <!-- end import script -->
</body>
</html>