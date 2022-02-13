<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- sidebar -->
<div class="sidebar">
  <ul class="sidebar-nav">
    <li class="sidebar-nav-item"><c:if
        test="${param.system_active == true}">
        <a href="${pageContext.request.contextPath}/system" class="sidebar-nav-link active">
      </c:if> <c:if test="${param.system_active == null}">
        <a href="${pageContext.request.contextPath}/system" class="sidebar-nav-link">
      </c:if>
      <div>
        <i class="fas fa-network-wired"></i>
      </div> <span> Tổng quan hệ thống </span> </a></li>
      
      
    <li class="sidebar-nav-item"><c:if
        test="${param.asset_active == true}">
        <a href="${pageContext.request.contextPath}/asset" class="sidebar-nav-link active">
      </c:if> <c:if test="${param.asset_active == null}">
        <a href="${pageContext.request.contextPath}/asset" class="sidebar-nav-link">
      </c:if>

      <div>
        <i class="fas fa-server"></i>
      </div> <span>Quản lý tài sản</span> </a></li>
      
      
    <li class="sidebar-nav-item"><c:if
        test="${param.risk_active == true}">
        <a href="${pageContext.request.contextPath}/risk" class="sidebar-nav-link active">
      </c:if> <c:if test="${param.risk_active == null}">
        <a href="${pageContext.request.contextPath}/risk" class="sidebar-nav-link">
      </c:if>

      <div>
        <i class="fas fa-tasks"></i>
      </div> <span>Đánh giá rủi ro</span> </a></li>
    <li class="sidebar-nav-item"><c:if
        test="${param.trouble_active == true}">
        <a href="${pageContext.request.contextPath}/trouble" class="sidebar-nav-link active">
      </c:if> <c:if test="${param.trouble_active == null}">
        <a href="${pageContext.request.contextPath}/trouble" class="sidebar-nav-link">
      </c:if>

      <div>
        <i class="fas fa-history"></i>
      </div> <span>Lịch sử sự cố</span> </a></li>
      
      
    <li class="sidebar-nav-item"><c:if
        test="${param.report_active == true}">
        <a href="${pageContext.request.contextPath}/report" class="sidebar-nav-link active">
      </c:if> <c:if test="${param.report_active == null}">
        <a href="${pageContext.request.contextPath}/report" class="sidebar-nav-link">
      </c:if>

      <div>
        <i class="fas fa-chart-bar"></i>
      </div> <span>Thống kê - báo cáo</span> </a></li>

  </ul>
</div>
<!-- end sidebar -->