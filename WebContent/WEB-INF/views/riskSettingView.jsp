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
<link rel="stylesheet" type="text/css"
  href="${pageContext.request.contextPath}/assets/css/risk_scale.css">
</head>
<body class="overlay-scrollbar">
  <jsp:include page="_navbar.jsp"></jsp:include>
  <jsp:include page="_sidebar.jsp">
    <jsp:param value="true" name="risk_active" />
  </jsp:include>

  <div id="flask-message-container" class="hide">
    <i class="far fa-times-circle"></i>
    <p id="flask-message">Xóa thất bại! Nhãn này đang được sử dụng!</p>
  </div>
  <!-- main content -->
  <div class="wrapper">
    <p class="link_mark">Home > Quản lý rủi ro > Cài đặt thang điểm
      đánh giá</p>
    
    
    <div class="row justify-content-center">
      <div class="col-8 col-m-12 col-sm-12">
        <p style="color: var(--success-color);"><c:out value="${successMessage}"></c:out></p>
        <c:remove var="successMessage" scope="session" />
        <h4>Thang đo mức độ tác động</h4>
        <form onkeydown="return event.key != 'Enter';" method="POST"
          action="${pageContext.request.contextPath}/risk/setting">
          
          <p>Thang điểm từ 0 đến 100. Mỗi nhãn tương ứng với một giá trị điểm số</p>
          <input type="hidden" name="system_id" value="${system_id}">
          <!-- <button type="button" class="btn-restore"
            onclick="setDefaultImpact()">Khôi phục cài đặt mặc định</button> -->
          <div class="form-item">
            <div class="container">
              <div class="label_container" id="impact_container">
                <c:forEach items="${impacts}" var="e">
                  <div>
                  
                  <i class="fas fa-minus-circle" onclick="removeInputWithCheck(event, ${i_used.contains(e.id)})"></i> 
                  <input type="text" class="impact_label" name="impact_label"
                    value="${fn:escapeXml(e.level)}"  style="color: ${e.color};" required> 
                  <input type="number" name="impact_score" value="${e.score}" min="0"
                    max="100" onblur="imposeMinMax(this)"  required>
                  <input type="color" name="impact_color" value="${e.color}" oninput="changeColor(event)">
                  <input type="hidden" name="impact_id" value="${e.id}">
                  </div>
                </c:forEach>
              </div>
              <div class="add_container">
                <label>Thêm nhãn mới</label> <input type="text"
                  id="impact" placeholder="Nhập tên nhãn gán"> <span
                  onclick="addInput('impact', 'impact_container')">
                  <i class="fas fa-plus-circle"></i>
                </span>
              </div>
            </div>
          </div>
          <div class="form-item justify-content-center">
            <button type="submit" name="setting"
              onclick="return confirm('Thêm rủi ro mới?')"
              value="impact_level">Cập nhật</button>
            <a class="btn btn-type2"
              onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
              href="${pageContext.request.contextPath}/risk">Hủy</a>
          </div>
        </form>

        <h4>Thang đo khả năng xảy ra</h4>
        <form onkeydown="return event.key != 'Enter';" method="POST"
          action="${pageContext.request.contextPath}/risk/setting">
          
          <p>Thang điểm từ 0 đến 100. Mỗi nhãn tương ứng với một giá trị điểm số</p>
         <!--  <button type="button" class="btn-restore"
            onclick="setDefaultLikelihood()">Khôi phục cài đặt
            mặc định</button> -->
          <input type="hidden" name="system_id" value="${system_id}">
          <div class="form-item">
            <div class="container">
              <div class="label_container" id="likelihood_container">
              <c:forEach items="${likelihoods}" var="e">
                  <div>
                  
                  <i class="fas fa-minus-circle" onclick="removeInputWithCheck(event, ${l_used.contains(e.id)})"></i> 
                  <input type="text" class="likelihood_label" name="likelihood_label"
                    value="${fn:escapeXml(e.level)}"  style="color: ${e.color};" required> 
                  <input type="number" name="likelihood_score" value="${e.score}" min="0"
                    max="100" onblur="imposeMinMax(this)" required>
                  <input type="color" name="likelihood_color" value="${e.color}" oninput="changeColor(event)">
                  <input type="hidden" name="likelihood_id" value="${e.id}">
                  </div>
                </c:forEach>          

              </div>
              <div class="add_container">
                <label>Thêm nhãn mới</label> <input type="text"
                  id="likelihood" placeholder="Nhập tên nhãn gán">
                <span
                  onclick="addInput('likelihood', 'likelihood_container')">
                  <i class="fas fa-plus-circle"></i>
                </span>
              </div>
            </div>
          </div>
          <div class="form-item justify-content-center">
            <button type="submit" name="setting"
              onclick="return confirm('Thêm rủi ro mới?')"
              value="likelihood_level">Cập nhật</button>
            <a class="btn btn-type2"
              onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
              href="${pageContext.request.contextPath}/risk">Hủy</a>
          </div>
        </form>

        <h4>Thang đo rủi ro</h4>
        <form onkeydown="return event.key != 'Enter';" method="POST"
          action="${pageContext.request.contextPath}/risk/setting">
          
          <p>Thang điểm từ 0 đến 100. Mỗi nhãn tương ứng với một khoảng giá trị</p>
          <p> Khi đánh giá, điểm rủi ro được tính tự động theo công thức: 
          <p style="text-align: center; font-weight: bold;">Rủi ro = Mức độ tác động x (Khả năng xảy ra/100)</p>

          <input type="hidden" name="system_id" value="${system_id}">
          <div class="form-item">
            <div class="container">
              <div class="label_container" id="risk_container">
               <c:forEach items="${risk_levels}" var="e">
                  <div>
                 
                  <i class="fas fa-minus-circle" onclick="removeInputRisk(event)"></i> 
                  <input type="text" class="risk_label" name="risk_label"
                    value="${fn:escapeXml(e.level)}" style="color: ${e.color};" required> 
                  <input type="number" name="risk_score_min" value="${e.range_min}"
                    min="0" max="100" readonly required> 
                  <input type="number" name="risk_score_max" value="${e.range_max}"
                    min="0" max="100" onblur="imposeMinMaxRisk(event)"
                    required>
                  <input type="color" name="risk_color" value="${e.color}" oninput="changeColor(event)">
                  <input type="hidden" name="risk_level_id" value="${e.id}">
                  </div>
                </c:forEach>          
              </div>
              <div class="add_container">
                <label>Thêm nhãn mới</label> <input type="text"
                  id="risk" placeholder="Nhập tên nhãn gán"> <span
                  onclick="addInputRisk()"> <i
                  class="fas fa-plus-circle"></i>
                </span>
              </div>
            </div>
          </div>


          <div class="form-item justify-content-center">
            <button type="submit" name="setting"
              onclick="return confirm('Thêm rủi ro mới?')"
              value="risk_level">Cập nhật</button>
            <a class="btn btn-type2"
              onclick="return confirm('Thay đổi sẽ KHÔNG được lưu?')"
              href="${pageContext.request.contextPath}/risk">Hủy</a>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- end main content -->
  <!-- import script -->
  <script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/risk_scale.js"></script>
  <script type="text/javascript">
  	var flask_message = document.querySelector("#flask-message-container");
  	var timeout;
  	function removeInputWithCheck(event, check) {
  		if(check) {
  			clearTimeout(timeout);
  		    flask_message.classList.remove("hide");
  		    timeout = setTimeout(() => {
  		      flask_message.classList.add("hide");
  		    }, 1500);
  		} else {
  			removeInput(event);
  		}
  	}
  	
  	function removeInputRisk(event) {
  		removeInput(event);
  		var container = document.getElementById("risk_container");
  		var inputs = container.getElementsByTagName("input");
  		if(inputs.length > 0) {
  			var e = inputs[1];
  			e.value = 0;
  		}
  	}
  </script>
  <!-- end import script -->
</body>
</html>