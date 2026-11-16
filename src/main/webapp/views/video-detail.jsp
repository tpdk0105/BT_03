<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${video.title} - Chi Tiết Video</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-9">
                <div class="card border-0 shadow rounded-4 overflow-hidden">
                    <div class="bg-dark text-center">
                        <c:choose>
                            <c:when test="${not empty youtubeEmbedUrl}">
                                <div class="ratio ratio-16x9">
                                    <iframe src="${youtubeEmbedUrl}" title="${video.title}" allowfullscreen></iframe>
                                </div>
                            </c:when>
                            <c:when test="${not empty video.videoUrl}">
                                <c:choose>
                                    <c:when test="${fn:startsWith(video.videoUrl, 'http://') || fn:startsWith(video.videoUrl, 'https://')}">
                                        <c:set var="playSrc" value="${video.videoUrl}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/media?fname=${video.videoUrl}" var="playSrc" />
                                    </c:otherwise>
                                </c:choose>
                                <video controls autoplay class="w-100" style="max-height: 480px;">
                                    <source src="${playSrc}" type="video/mp4">
                                    Trình duyệt không hỗ trợ phát video này.
                                </video>
                            </c:when>
                            <c:otherwise>
                                <div class="py-5 text-white-50">
                                    <i class="bi bi-film fs-1 d-block mb-2"></i>
                                    Video này chưa có nguồn phát (link YouTube hoặc file .mp4 upload).
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="card-body p-4">
                        <div class="d-flex justify-content-between align-items-start flex-wrap mb-2">
                            <h3 class="fw-bold mb-1">${video.title}</h3>
                            <span class="badge bg-primary fs-6"><i class="bi bi-eye me-1"></i>${video.views} lượt xem</span>
                        </div>

                        <div class="text-muted small mb-3">
                            <span class="me-3"><i class="bi bi-upc-scan me-1"></i>Mã: <strong>${video.videoId}</strong></span>
                            <span class="me-3"><i class="bi bi-tag me-1"></i>Thể loại: <strong>${video.category != null ? video.category.categoryname : 'Chưa phân loại'}</strong></span>
                            <span class="me-3"><i class="bi bi-person me-1"></i>Người đăng: <strong>${video.user != null ? video.user.userName : 'Admin'}</strong></span>
                            <span><i class="bi bi-calendar me-1"></i>Ngày đăng: <strong>${video.createdDate}</strong></span>
                        </div>

                        <hr>
                        <h6 class="fw-bold">Mô tả:</h6>
                        <p class="text-secondary mb-4">${not empty video.description ? video.description : 'Không có mô tả.'}</p>

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                                <i class="bi bi-house me-1"></i>Về Trang chủ
                            </a>
                            <a href="${pageContext.request.contextPath}/video" class="btn btn-outline-primary">
                                <i class="bi bi-collection-play me-1"></i>Danh sách Video của tôi
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
