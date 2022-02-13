<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/risk.css">
</head>
<body class="overlay-scrollbar">
	<jsp:include page="_navbar.jsp"></jsp:include>
    <jsp:include page="_sidebar.jsp">
      <jsp:param value="true" name="risk_active" />
    </jsp:include>

	<!-- main content -->
	<div class="wrapper">
		<p class="link_mark">Home > Quản lý rủi ro > Thêm rủi ro mới</p>
		<div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">
        <h2 class="form-title">Thêm rủi ro mới</h2>
        <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
        <c:remove var="successMessage" scope="session" />
        <form onkeydown="return event.key != 'Enter';" method="POST" action="${pageContext.request.contextPath}/risk/add">
		
        <input type="hidden" name="system_id" value="${system_id }">
        
        <h4>Thông tin về rủi ro</h4>
          <div class="form-item">
            <label for="short_description">Mô tả ngắn <span>(*)</span></label>
            <textarea name="short_description" rows="3" placeholder="Mô tả ngắn về rủi ro" required></textarea>
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
					</select>
					<span onclick="addTag('tag_asset', 'tag_asset_container')">
						<i class="fas fa-plus-circle"></i>
					</span>
				</div>
			</div>	
        	</div>
					
          <div class="form-item">
            <label for="flaw">Lỗ hổng <span>(*)</span></label>
            <textarea name="flaw" rows="5" placeholder="Chi tiết lỗ hổng" required></textarea>
          </div>

					<div class="form-item">
						<label id="threat">Nguồn đe dọa <span>(*)</span></label>
            <textarea name="threat" rows="5" placeholder="Nguồn đe dọa, hành động, động cơ..." required></textarea>
          </div>
  
          <div class="form-item">
            <label id="type-label">Loại nguồn đe dọa <span>(*)</span></label>
            <div class="input-container">
              <label for="threat-type">
                <input type="radio" name="threat-type" value="0" checked>
								Con người <span>(vô tình/cố ý truy cập, tấn công...)</span>
              </label>
              <label for="status">
                <input type="radio" name="threat-type" value="1">
								Thiên nhiên <span>(lũ lụt, động đất...)</span>
              </label>
              <label for="status">
                <input type="radio" name="threat-type" value="2">
								Môi trường <span>(mất điện kéo dài, rò rỉ chất lỏng...)</span>
              </label>
            </div>
          </div>

          <div class="form-item">
            <label for="solution">Biện pháp kiểm sát</label>
            <textarea name="solution" rows="5" placeholder="Biện pháp kiếm soát hiện tại/dự định/đề xuất"></textarea>
          </div>

		<hr class="hr_2px">
		<h4>Đánh giá rủi ro</h4>
		<div>
			<h5>Thang đo mức độ tác động</h5>
			<ul>
              <c:forEach items="${impacts}" var="impact">
                <li class="level" data-color="${impact.color}">${fn:escapeXml(impact.level)}: ${impact.score}</li>
              </c:forEach>
			</ul>
		</div>
		<div class="form-item">
			<label>Mức độ tác động</label>
			<div class="option_container">
				<select onchange="changeScore(event)" name="impact">
					<option selected disabled>Chọn nhãn gán</option>
                    <c:forEach items="${impacts}" var="impact">
                      <option value="${impact.id}" data-min="${impact.score}" data-max="${impact.score}">${fn:escapeXml(impact.level)}</option>
                    </c:forEach>
				</select>
				<!-- onkeyup không ổn lắm -->
				<input type="number" min="0" max="0" value="0" 
				name="impact_score" id="impact_score" 
				onblur="imposeMinMax(this)"
				onchange="calRiskScore()">
			</div>
		</div>

		<hr class="hr_4px">
		<div>
			<h5>Thang đo khả năng xảy ra</h5>
			<ul>
				<c:forEach items="${likelihoods}" var="likelihood">
                  <li class="level" data-color="${likelihood.color}">${fn:escapeXml(likelihood.level)}: ${likelihood.score}</li>
                </c:forEach>
			</ul>
		</div>
		<div class="form-item">
			<label>Khả năng xảy ra</label>
			<div class="option_container">
				<select onchange="changeScore(event)" name="likelihood">
					<option selected disabled>Chọn nhãn gán</option>
					<c:forEach items="${likelihoods}" var="likelihood">
                      <option value="${likelihood.id}" data-min="${likelihood.score}" data-max="${likelihood.score}">${fn:escapeXml(likelihood.level)}</option>
                    </c:forEach>
				</select>
				<input type="number" min="0" max="0" value="0" 
				name="likelihood_score" id="likelihood_score" 
				onblur="imposeMinMax(this)"
				onchange="calRiskScore()">
			</div>
		</div>

		<hr class="hr_1px">
		<div>
			<h5>Thang đo rủi ro</h5>
			<ul>
				<c:forEach items="${risk_levels}" var="risk_level">
                  <li class="level" data-color="${risk_level.color}">${fn:escapeXml(risk_level.level)}: ${risk_level.range_min} - ${risk_level.range_max}</li>
                </c:forEach>
			</ul>
			<h5>Được tính tự động: Rủi ro = Mức độ tác động x (Khả năng xảy ra/100)</h5>
		</div>
		<div class="form-item">
			<label>Mức độ rủi ro</label>
			<div class="option_container">
				<select id="risk_scale" style="display: none;">
					<option selected disabled>Chọn nhãn gán</option>
                    <c:forEach items="${risk_levels}" var="risk_level">
                      <option data-min="${risk_level.range_min}" data-max="${risk_level.range_max}">${fn:escapeXml(risk_level.level)}</option>
                    </c:forEach>
				</select>
				<input type="text" value="" id="risk_level" readonly>
				<input type="number" value="0" name="risk_score" id="risk_score" readonly>
			</div>
		</div>

					
          
          <div class="form-item justify-content-center">
            <button type="submit" name="add"
						onclick="return confirm('Thêm rủi ro mới?')"
						value="">Thêm mới</button>
            <a class="btn btn-type2"
						onclick="return confirm('Thông tin sẽ KHÔNG được lưu?')"
						href="${pageContext.request.contextPath}/risk">Hủy</a>
          </div>
        </form>
			</div>
		</div>
	</div>
	<!-- end main content -->
	<!-- import script -->
	<script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/select.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/risk.js"></script>
	<!-- end import script -->
</body>
</html>