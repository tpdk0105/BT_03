<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/waiting">
            <i class="bi bi-box-seam me-2"></i>Hệ Thống ABC
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navContent">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navContent">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/waiting">Trang Chủ</a>
                </li>
            </ul>

            <ul class="navbar-nav ms-auto align-items-center">
                <c:choose>
                    <c:when test="${sessionScope.account == null}">
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm me-2" href="${pageContext.request.contextPath}/login">
                                <i class="bi bi-box-arrow-in-right me-1"></i>Đăng nhập
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-warning btn-sm" href="${pageContext.request.contextPath}/register">
                                <i class="bi bi-person-plus me-1"></i>Đăng ký
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item me-3 text-white">
                            <i class="bi bi-person-circle me-1"></i>
                            Xin chào, <strong>${sessionScope.account.fullName != null ? sessionScope.account.fullName : sessionScope.account.userName}</strong>
                            <c:choose>
                                <c:when test="${sessionScope.account.roleid == 1}">
                                    <span class="badge bg-danger ms-1">Admin</span>
                                </c:when>
                                <c:when test="${sessionScope.account.roleid == 2}">
                                    <span class="badge bg-info ms-1">Manager</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-success ms-1">Member</span>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/logout">
                                <i class="bi bi-box-arrow-right me-1"></i>Đăng Xuất
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
