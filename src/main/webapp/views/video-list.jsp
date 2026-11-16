<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video Của Tôi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h3 class="fw-bold mb-1">
                    <i class="bi bi-collection-play-fill text-primary me-2"></i>
                    ${canCrud ? 'Video Của Tôi (Quản lý Video)' : 'Tất Cả Video'}
                </h3>
                <span class="text-muted small">Tổng cộng: <strong>${totalItems}</strong> video (6 video/trang)</span>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary me-2">
                    <i class="bi bi-house me-1"></i>Top 10 mới nhất (/home)
                </a>
                <c:if test="${canCrud}">
                    <a href="${pageContext.request.contextPath}/video/add" class="btn btn-success fw-bold">
                        <i class="bi bi-plus-circle-fill me-1"></i>Thêm Video mới
                    </a>
                </c:if>
            </div>
        </div>

        <c:if test="${empty listvideo}">
            <div class="card border-0 shadow-sm text-center py-5">
                <div class="card-body">
                    <i class="bi bi-camera-video-off fs-1 text-muted"></i>
                    <p class="mt-2 mb-3 text-muted">${canCrud ? 'Bạn chưa đăng video nào.' : 'Hiện chưa có video nào.'}</p>
                    <c:if test="${canCrud}">
                        <a href="${pageContext.request.contextPath}/video/add" class="btn btn-primary">Đăng video đầu tiên</a>
                    </c:if>
                </div>
            </div>
        </c:if>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach items="${listvideo}" var="v">
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
                            <div class="d-flex justify-content-between align-items-center mb-1">
                                <span class="badge bg-light text-dark border">${v.videoId}</span>
                                <span class="badge ${v.active == 1 ? 'bg-success' : 'bg-secondary'}">
                                    ${v.active == 1 ? 'Hoạt động' : 'Ẩn'}
                                </span>
                            </div>
                            <h5 class="card-title fw-bold mt-2">
                                <a href="${pageContext.request.contextPath}/video/detail?id=${v.videoId}" class="text-decoration-none text-primary">
                                    ${v.title}
                                </a>
                            </h5>
                            <p class="card-text text-muted small text-truncate">${v.description}</p>
                        </div>
                        <div class="card-footer bg-white d-flex justify-content-between align-items-center">
                            <span class="small text-muted"><i class="bi bi-eye me-1"></i>${v.views} lượt xem</span>
                            <c:if test="${canCrud}">
                                <div>
                                    <a href="${pageContext.request.contextPath}/video/edit?id=${v.videoId}" class="btn btn-sm btn-outline-warning me-1">
                                        <i class="bi bi-pencil-square"></i> Sửa
                                    </a>
                                    <a href="${pageContext.request.contextPath}/video/delete?id=${v.videoId}" class="btn btn-sm btn-outline-danger"
                                       onclick="return confirm('Xóa video này?');">
                                        <i class="bi bi-trash"></i> Xóa
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Phân trang 6 sp/trang -->
        <c:if test="${totalPages > 1}">
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/video?page=${currentPage - 1}">Trước</a>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/video?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/video?page=${currentPage + 1}">Sau</a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
