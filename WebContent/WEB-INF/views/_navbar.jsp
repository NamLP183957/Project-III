<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- navbar -->
  <div class="navbar">
    <!-- nav left -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link">
          <i class="fas fa-bars" onclick="collapseSidebar()"></i>
        </a>
      </li>
      <li class="nav-item">
        <img src="${pageContext.request.contextPath}/assets/image/logo_side-1_line-black.png" alt="logo" class="logo logo-light">
        <img src="${pageContext.request.contextPath}/assets/image/logo_side-1_line-white.png" alt="logo" class="logo logo-dark">
      </li>
    </ul>
    <!-- end nav left -->

    <!-- nav right -->
    <ul class="navbar-nav nav-right">
      <li class="nav-item mode">
        <a class="nav-link" href="#" onclick="switchTheme()">
          <i class="fas fa-moon dark-icon"></i>
          <i class="fas fa-sun light-icon"></i>
        </a>
      </li>
      <li class="nav-item avt-wrapper">
        <div class="avt dropdown">
          <i class="fas fa-user user-icon dropdown-toggle" data-toggle="user-menu"></i>
          <!-- <i class="fas fa-user dropdown-toggle" data-toggle="user-menu"></i> -->
          <!-- <img src="assets/image/admin_avatar.png" alt="admin avatar" class="dropdown-toggle" data-toggle="user-menu"> -->
          <ul id="user-menu" class="dropdown-menu">
            <li  class="dropdown-menu-item">
              <a class="dropdown-menu-link" href="${pageContext.request.contextPath}/user/info">
                <div>
                  <i class="fas fa-user-tie"></i>
                </div>
                <span>Thông tin tài khoản</span>
              </a>
            </li>
            <li class="dropdown-menu-item">
              <a class="dropdown-menu-link" href="${pageContext.request.contextPath}/user/setting">
                <div>
                  <i class="fas fa-cog"></i>
                </div>
                <span>Cài đặt</span>
              </a>
            </li>
            <li  class="dropdown-menu-item">
              <a class="dropdown-menu-link" href="${pageContext.request.contextPath}/logout">
                <div>
                  <i class="fas fa-sign-out-alt"></i>
                </div>
                <span>Đăng xuất</span>
              </a>
            </li>
          </ul>
        </div>
      </li>
    </ul>
    <!-- end nav right -->
  </div>
  <!-- end navbar -->