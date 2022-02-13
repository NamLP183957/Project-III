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
</head>
<body class="overlay-scrollbar">

  <jsp:include page="_navbar.jsp"></jsp:include>
  
  <jsp:include page="_sidebar.jsp">
    <jsp:param name="system_active" value="true" />
  </jsp:include>

  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Tổng quan hệ thống</p>
    <c:choose>
      <c:when test="${system == null}">
        <div class="row justify-content-center">
      <div class="col-6 col-m-12 col-sm-12">
        <p class="notice">Chưa khởi tạo thông tin về hệ thống</p>
        <img class="img_description" src="assets/image/no_system.svg">
        
        <a class="link_btn" href="${pageContext.request.contextPath}/system/add">Tạo hệ thống</a>
      </div>
    
    </div>
      </c:when>
      <c:otherwise>
        <div class="row justify-content-center">
      <div class="col-12 col-m-12 col-sm-12">
        
        <div>
          <h2 class="notice">Thông tin hệ thống</h2>
          <a class="link_btn right" href="${pageContext.request.contextPath}/system/action">Thao tác <i class="fas fa-cog"></i></a>
        </div>
        <div class="card">
          <div class="card-header">
            <h3>Tên hệ thống</h3>
          </div>
          <div class="card-content">
            <p><strong>${fn:escapeXml(system.name) }</strong></p>
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${system.created_time}" var="parsed_created_time" />
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${system.modified_time}" var="parsed_modified_time" />
            <p>Thời điểm tạo: <fmt:formatDate value="${parsed_created_time}" pattern="HH:mm dd-MM-yyyy" /></p>
            <p>Thời điểm cập nhật cuối: <fmt:formatDate value="${parsed_modified_time}" pattern="HH:mm dd-MM-yyyy" /></p>
            <%-- <p>Thời điểm tạo: <span>${fn:escapeXml(system.created_time) }</span></p>
            <p>Thời điểm cập nhật cuối: <span>${fn:escapeXml(system.modified_time) }</span></p> --%>
          </div>
        </div>
        <div class="card">
          <div class="card-header">
            <h3>Mô tả hệ thống</h3>
          </div>
          <div class="card-content">
            ${fn:escapeXml(system.description) }
          </div>
        </div>
        
        <c:if test="${images != null }">
          <div class="card">
          
            <div class="card-header">
              <h3>Hình ảnh</h3>
            </div>
            
            <div class="card-content">
              <div class="img-container">
              <c:forEach items="${images}" var="link">
                <div>
                  <img class="img-item" src="${link }">
                </div>
              </c:forEach>
              </div>
            </div>
            
          </div>
        </c:if>
        
        
        
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