<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <jsp:param value="true" name="report_active"/>
  </jsp:include>
  
<!-- main content -->
	<div class="wrapper">
		<p class="link_mark">Home > Thống kê và báo cáo</p>
		<div class="col-12 col-m-12 col-sm-12">
				<a class="link_btn right" href="${pageContext.request.contextPath}/download">Xuất báo cáo đánh giá rủi ro <i class="fas fa-file"></i></a>
		</div>

        <div class="row">
      <div class="col-12 col-m-12 col-sm-12">
        <h2 class="notice">Thống kê rủi ro</h2>
      </div>
    </div>
    
     <div class="row">
      <div class="col-4 col-m-6 col-sm-6">
        <div class="counter bg-primary">
          <p>
            <i class="fas fa-tasks"></i>
          </p>
          <h3>${num_risk_system}</h3>
          <p>Tổng số</p>
        </div>
      </div>

      <div class="col-4 col-m-6 col-sm-6">
        <div class="counter bg-success">
          <p>
            <i class="fas fa-check-circle"></i>
          </p>
          <h3>${num_risk_assessment}</h3>
          <p>Đã đánh giá</p>
        </div>
      </div>

      <div class="col-4 col-m-6 col-sm-6">
        <div class="counter bg-warning">
          <p>
            <i class="fas fa-spinner"></i>
          </p>
          <h3>${num_risk_system - num_risk_assessment}</h3>
          <p>Chưa đánh giá</p>
        </div>
      </div>
    </div>

    <div class="row justify-content-center">
      <div class="col-6 col-m-12 col-sm-12">
      <div class="card">
        <div class="card-content">
            <table>
            <tbody>
              <thead>
                <tr>
                  <th>Mức độ rủi ro</th>
                  <th>Số lượng</th>
                </tr>
              </thead>
              <c:forEach items="${riskLevels}" var="riskLevel">
                <tr style="color: ${riskLevel.color};">
                    <td>${fn:escapeXml(riskLevel.level)}</td>
                    <td>${map.get(riskLevel.id) != null ? map.get(riskLevel.id) : '0' }</td>
                </tr>
              </c:forEach>
            </tbody>
            </table>
      </div>
    </div>
    </div>
    </div>
  
  
		<div class="row">
			<div class="col-12 col-m-12 col-sm-12">
        <h2 class="notice">Thống kê sự cố - ${year }</h2>
			</div>
		</div>

		<div class="row">
			<div class="col-3 col-m-6 col-sm-6">
				<div class="counter bg-primary">
                    <p>
                      <i class="fas fa-tasks"></i>
                    </p>
					<h3>${troubleStatus.get(0) + troubleStatus.get(1) + troubleStatus.get(2)}</h3>
					<p>Tổng số</p>
				</div>
			</div>

			<div class="col-3 col-m-6 col-sm-6">
				<div class="counter bg-danger">
                    <p>
                      <i class="fas fa-bug"></i>
                    </p>
					<h3>${troubleStatus.get(0)}</h3>
					<p>Chưa xử lý</p>
				</div>
			</div>

			<div class="col-3 col-m-6 col-sm-6">
				<div class="counter bg-warning">
                    <p>
                      <i class="fas fa-spinner"></i>
                    </p>
					<h3>${troubleStatus.get(1)}</h3>
					<p>Đang xử lý</p>
				</div>
			</div>

			<div class="col-3 col-m-6 col-sm-6">
				<div class="counter bg-success">
                    <p>
                      <i class="fas fa-check-circle"></i>
                    </p>
                    <h3>${troubleStatus.get(2)}</h3>
					<p>Đã xử lý</p>
				</div>
			</div>
			
		</div>

		<div class="row">
			<div class="col-12 col-m-12 col-sm-12">
				<div style="overflow-x: auto;">
					<div class="card">
						<!-- <div class="card-header">
							<h3>
								Chartjs
							</h3>
						</div> -->
						<div class="card-content">
							<canvas id="trouble_chart"></canvas>
						</div>
					</div>
				</div>
			
			</div>
		</div>


		

	</div>
	<!-- end main content -->
	<!-- import script -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js"></script>
	<script src="assets/js/index.js"></script>
    <script type="text/javascript">
   /*  console.log(${troubleMonth.get(0)});
    var data = [<c:forEach items="${troubleMonth.get(0)}" var="value">${value}, </c:forEach>];
    console.log(data); */
    var ctx = document.getElementById('trouble_chart')
    ctx.height = 500
    ctx.width = 500
    var data = {
    	labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    	datasets: [{
    		fill: false,
    		label: 'Chưa xử lý',
    		borderColor: 'rgb(235, 77, 75, 0.5)',
    		data: ${troubleMonth.get(0)},
    		borderWidth: 2,
    		lineTension: 0,
    	}, {
    		fill: false,
    		label: 'Đang xử lý',
    		borderColor: 'rgb(240, 147, 43, 0.5)',
    		data: ${troubleMonth.get(1)},
    		borderWidth: 2,
    		lineTension: 0,
    	}, {
    		fill: false,
    		label: 'Đã xử lý',
    		borderColor: 'rgb(106, 176, 76, 0.5)',
    		data: ${troubleMonth.get(2)},
    		borderWidth: 2,
    		lineTension: 0,
    	}]
    }

    var lineChart = new Chart(ctx, {
    	type: 'line',
    	data: data,
    	options: {
    		maintainAspectRatio: false,
    		bezierCurve: false,
    		scales: {
        		yAxes: [{
        	        ticks: {
        	        	precision: 0
        	        }
        	      }],
 
        	},
    	}
    })

    </script>
	<!-- end import script -->
</body>
</html>