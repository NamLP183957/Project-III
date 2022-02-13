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
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/assets/css/risk.css">
</head>

<body class="overlay-scrollbar">
  <jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp">
    <jsp:param value="true" name="risk_active" />
  </jsp:include>

  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Đánh giá rủi ro > Rủi ro #${risk.id}</p>
    <div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">

        <h2 class="form-title">Chi tiết thông tin</h2>
        <p style="color: var(--success-color);"><c:out value="${successMessage1}"></c:out></p>
        <c:remove var="successMessage1" scope="session" />
        <p style="color: var(--success-color);"><c:out value="${successMessage2}"></c:out></p>
        <c:remove var="successMessage2" scope="session" />
        
        <form method="POST" action="${pageContext.request.contextPath}/risk/action">

          <div class="form-item">
            <label for="name">Cập nhật cuối</label>
            <fmt:parseDate pattern="yyyy-MM-dd HH:mm:ss" value="${risk.modified_time}" var="parsed_modified_time" />
            <input type="text" value='<fmt:formatDate value="${parsed_modified_time}" pattern="HH:mm dd-MM-yyyy" />' disabled readonly>
          </div>

          <div class="form-item">
            <label for="short_description">Mô tả ngắn <span>(*)</span></label>
            <textarea name="short_description" rows="3"
              placeholder="Mô tả ngắn về rủi ro" required>${risk.short_description}</textarea>
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

          <div class="form-item">
            <label for="flaw">Lỗ hổng <span>(*)</span></label>
            <textarea name="flaw" rows="5"
              placeholder="Chi tiết lỗ hổng" required>${fn:escapeXml(risk.flaw)}</textarea>
          </div>

          <div class="form-item">
            <label id="threat">Nguồn đe dọa <span>(*)</span></label>
            <textarea name="threat" rows="5"
              placeholder="Nguồn đe dọa, hành động, động cơ..." required>${fn:escapeXml(risk.threat)}</textarea>
          </div>

          <div class="form-item">
            <label id="type-label">Loại nguồn đe dọa <span>(*)</span></label>
            <div class="input-container">
              <label for="threat-type"> <input type="radio"
                name="threat-type" value="0"
                ${risk.threat_type == 0 ? "checked" : "" }>
                Con người <span>(vô tình/cố ý truy cập, tấn công...)</span>
              </label> 
              <label for="status"> <input type="radio"
                name="threat-type" value="1"
                ${risk.threat_type == 1 ? "checked" : "" }>
                Thiên nhiên <span>(lũ lụt, động đất...)</span>
              </label> 
              <label for="status"> <input type="radio"
                name="threat-type" value="2"
                ${risk.threat_type == 2 ? "checked" : "" }>
                Môi trường <span>(mất điện kéo dài, rò rỉ chất lỏng...)</span>
              </label>
            </div>
          </div>

          <div class="form-item">
            <label for="solution">Biện pháp kiểm sát</label>
            <textarea name="solution" rows="5"
              placeholder="Biện pháp kiếm soát hiện tại/dự định/đề xuất">${fn:escapeXml(risk.solution)}</textarea>
          </div>
          
          <div class="form-item justify-content-center">
            <button type="submit" name="action"
              onclick="return confirm('Xác nhận LƯU thay đổi?')"
              value="update">Cập nhật</button>
            <a class="btn btn-type2"
              onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
              href="${pageContext.request.contextPath}/risk">Hủy</a>
          </div>
        </form>

        <h2 class="form-title">Đánh giá rủi ro</h2>
        <form method="POST" action="${pageContext.request.contextPath}/risk/action">
          <input type="hidden" name="id" value="${risk.id}" readonly>
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
                   <option value="${impact.id}" 
                   data-min="${impact.score}" 
                   data-max="${impact.score}" 
                   ${impact.id == i.id ? "selected": "" }>${fn:escapeXml(impact.level)}</option>
                </c:forEach>
              </select>

              <input type="number" min="${i.score}" max="${i.score}" value="${i.score}"
                name="impact_score" id="impact_score"
                onblur="imposeMinMax(this)" onchange="calRiskScore()">
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
                  <option value="${likelihood.id}" 
                  data-min="${likelihood.score}" 
                  data-max="${likelihood.score}" 
                  ${likelihood.id == l.id ? "selected": ""}>${fn:escapeXml(likelihood.level)}</option>
                </c:forEach>
              </select> <input type="number" min="${l.score}" max="${l.score}" value="${l.score}"
                name="likelihood_score" id="likelihood_score"
                onblur="imposeMinMax(this)" onchange="calRiskScore()">
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
            <h5>Được tính tự động: Rủi ro = Mức độ tác động x (Khả
              năng xảy ra/100)</h5>
          </div>
          <div class="form-item" >
            <label>Mức độ rủi ro</label>
            <div class="option_container">
              <select id="risk_scale" style="display: none;">
                <option selected disabled>Chọn nhãn gán</option>
                <c:forEach items="${risk_levels}" var="risk_level">
                      <option data-min="${risk_level.range_min}" data-max="${risk_level.range_max}">${fn:escapeXml(risk_level.level)}</option>
                    </c:forEach>
              </select> <input type="text" value="" id="risk_level" readonly>
              <input type="number" value="0" name="risk_score"
                id="risk_score" readonly>
            </div>
          </div>
         
          <div class="form-item justify-content-center">
            <button type="submit" name="action"
              onclick="return confirm('Xác nhận LƯU thay đổi?')"
              value="assess">Cập nhật đánh giá</button>
            <a class="btn btn-type2"
              onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
              href="${pageContext.request.contextPath}/risk">Hủy</a>
          </div>
        </form>

        <h2 class="form-title">Xóa rủi ro</h2>
        <form method="POST" action="${pageContext.request.contextPath}/risk/action">
          <p>Rủi ro bị xóa sẽ không thể hoàn tác. Hãy cân nhắc chắc chắn.</p>
          <input type="hidden" name="id" value="${risk.id}" readonly>
          <div class="form-item">
            <button class="btn-danger" type="submit" name="action"
              onclick="return confirm('Bạn chắc chắn muốn XÓA rủi ro #${risk.id}?')"
              value="delete">Xóa rủi ro</button>
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
  <script type="text/javascript">
  	window.onload = () => {
  		checkTag('tag_asset', 'tag_asset_container');
  		calRiskScore();
  	}
  </script>
  <!-- end import script -->
</body>

</html>