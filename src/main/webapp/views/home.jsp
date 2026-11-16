<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ - Hệ Thống ABC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="row justify-content-center mb-4">
            <div class="col-lg-10">
                <div class="card border-0 shadow rounded-3">
                    <div class="card-header bg-success text-white py-3 d-flex justify-content-between align-items-center">
                        <h5 class="mb-0 fw-bold"><i class="bi bi-person-badge me-2"></i>Trang Chủ Thành Viên</h5>
                        <c:if test="${sessionScope.account.roleid == 1 || sessionScope.account.roleid == 2}">
                            <a href="${pageContext.request.contextPath}/video" class="btn btn-light btn-sm fw-bold text-success">
                                <i class="bi bi-collection-play-fill me-1"></i>Quản lý Video của tôi
                            </a>
                        </c:if>
                    </div>
                    <div class="card-body p-4">
                        <div class="alert alert-info py-2 mb-3">
                            <i class="bi bi-info-circle me-2"></i>Xin chào <strong>${sessionScope.account.fullName != null ? sessionScope.account.fullName : sessionScope.account.userName}</strong> (${sessionScope.account.email}).
                        </div>
                        <div class="row small text-secondary">
                            <div class="col-md-4"><strong>Username:</strong> ${sessionScope.account.userName}</div>
                            <div class="col-md-4"><strong>SĐT:</strong> ${sessionScope.account.phone}</div>
                            <div class="col-md-4"><strong>Ngày tạo:</strong> ${sessionScope.account.createdDate}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 10 Video Mới Nhất -->
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
                    <h4 class="fw-bold mb-0"><i class="bi bi-fire text-danger me-2"></i>10 Video Mới Nhất</h4>
                    <div class="btn-group btn-group-sm">
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary active">
                            <i class="bi bi-stars me-1"></i>Top 10 mới nhất
                        </a>
                        <a href="${pageContext.request.contextPath}/video" class="btn btn-outline-primary">
                            <i class="bi bi-grid-3x3-gap me-1"></i>Xem tất cả Video
                        </a>
                    </div>
                </div>

                <c:if test="${empty latestVideos}">
                    <div class="alert alert-secondary text-center py-4">Chưa có video nào được đăng.</div>
                </c:if>

                <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                    <c:forEach items="${latestVideos}" var="v">
                        <div class="col">
                            <div class="card h-100 border-0 shadow-sm rounded-3">
                                <c:choose>
                                    <c:when test="${not empty v.poster && (fn:startsWith(v.poster, 'http://') || fn:startsWith(v.poster, 'https://'))}">
                                        <c:set var="posterSrc" value="${v.poster}" />
                                    </c:when>
                                    <c:when test="${not empty v.poster}">
                                        <c:url value="/image?fname=${v.poster}" var="posterSrc" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="posterSrc" value="https://via.placeholder.com/400x220?text=Video" />
                                    </c:otherwise>
                                </c:choose>
                                <img src="${posterSrc}" class="card-img-top" alt="${v.title}" style="height: 180px; object-fit: cover;"
                                     onerror="this.src='https://via.placeholder.com/400x220?text=Video'">
                                <div class="card-body">
                                    <h6 class="card-title fw-bold mb-2">
                                        <a href="${pageContext.request.contextPath}/video/detail?id=${v.videoId}" class="text-decoration-none text-dark stretched-link">
                                            ${v.title}
                                        </a>
                                    </h6>
                                    <p class="card-text text-muted small text-truncate">${v.description}</p>
                                </div>
                                <div class="card-footer bg-white border-0 small text-muted d-flex justify-content-between">
                                    <span><i class="bi bi-eye me-1"></i>${v.views} lượt xem</span>
                                    <span><i class="bi bi-person me-1"></i>${v.user != null ? v.user.userName : 'Admin'}</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
