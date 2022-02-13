<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" type="text/css" href="assets/css/risk.css">
</head>
<body class="overlay-scrollbar">
  <jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp">
    <jsp:param value="true" name="risk_active" />
  </jsp:include>

  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Đánh giá rủi ro</p>
    
     <c:choose>
      <c:when test="${list == null || list.size() == 0}">
        <div class="row justify-content-center">
			<div class="col-6 col-m-12 col-sm-12">
				<p class="notice">Chưa ghi nhận rủi ro</p>
				<img class="img_description" src="assets/image/no_risk.svg">
        
				<a class="link_btn" href="${pageContext.request.contextPath}/risk/add">Thêm rủi ro mới</a>
			</div>
		
		</div>
      </c:when>
      <c:otherwise>
      <div class="row">
      <div class="col-3 col-m-12 col-sm-12">
        <label for="risk_level" class="label-filter">Lọc theo mức độ rủi ro</label> 
        <select name="risk_level" id="filter" onchange="filterTable()">
          <option value="all" selected>Tất cả</option>
          <option value="evaluated">Đã đánh giá</option>
          <option value="not-be-evaluated">Chưa đánh giá</option>
          <c:forEach items="${risk_levels}" var="risk_level">
            <option value="${fn:escapeXml(risk_level.level)}" style="color: ${risk_level.color};">
            ${fn:escapeXml(risk_level.level)}
            </option>
          </c:forEach>
        </select>
      </div>
      <!-- <div class="col-4 col-m-12 col-sm-12">
        <label for="sort">Sắp xếp</label> 
        <select name="sort">
          <option value="modified_time desc">Thời gian cập nhật từ mới đến cũ</option>
          <option value="modified_time asc">Thời gian cập nhật từ cũ đến mới</option>
          <option value="risk">Theo mức độ rủi ro tăng dần</option>
          <option value="likelihood">Theo mức độ tác động tăng dần</option>
          <option value="impact">Theo khả năng xảy ra tăng dần</option>
        </select>
      </div> -->
      <div class="col-9 col-m-12 col-sm-12">
        <div>
          <a class="link_btn right" href="${pageContext.request.contextPath}/risk/setting">Cài đặt
            thang điểm đánh giá <i class="fas fa-cog"></i>
          </a>
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-12 col-m-12 col-sm-12">
        <div class="card">
          <div class="card-header">
            <h3>Danh sách rủi ro</h3>

            <a href="${pageContext.request.contextPath}/risk/add"> <i class="fas fa-plus-circle"></i>
            </a>
          </div>

          <div class="card-content">
            <p>Số lượng: <span id="num-risk">${list.size()}</span></p>
            <table>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Mô tả ngắn</th>
                  <th>Lỗ hổng</th>
                  <th>Nguồn đe dọa</th>
                  <th>Mức độ rủi ro</th>
                  <th>Mức độ tác động</th>
                  <th>Khả năng xảy ra</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${list}" var="element">
                <tr class="row_link" data-link="risk/action?id=${element.risk.id}"
                  onclick="clickRow(event)">
                  <td>${fn:escapeXml(element.risk.id)}</td>
                  <td>${fn:escapeXml(element.risk.short_description)}</td>
                  <td>${fn:escapeXml(element.risk.flaw)}</td>
                  <td>${fn:escapeXml(element.risk.threat)}</td>
                  <td style="color:${element.riskLevel.color};">${fn:escapeXml(element.riskLevel.level)}</td>
                  <td style="color:${element.impactLevel.color};">${fn:escapeXml(element.impactLevel.level)}</td>
                  <td style="color:${element.likelihoodLevel.color};">${fn:escapeXml(element.likelihoodLevel.level)}</td>
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
  <script src="assets/js/risk_view.js"></script>
  <!-- end import script -->
</body>
</html>