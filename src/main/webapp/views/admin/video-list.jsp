<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Video</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="card border-0 shadow rounded-3">
            <div class="card-header bg-primary text-white py-3 d-flex justify-content-between align-items-center">
                <h4 class="mb-0 fw-bold"><i class="bi bi-camera-video-fill me-2"></i>Danh Sách Video</h4>
                <a href="<c:url value='/admin/video/add'/>" class="btn btn-light fw-bold text-primary">
                    <i class="bi bi-plus-circle-fill me-1"></i> Add Video
                </a>
            </div>
            <div class="card-body p-4">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle text-center">
                        <thead class="table-dark">
                            <tr>
                                <th>STT</th>
                                <th>Mã video</th>
                                <th>Poster</th>
                                <th>Tiêu đề</th>
                                <th>Thể loại</th>
                                <th>Lượt xem</th>
                                <th>Trạng thái</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listvideo}" var="v" varStatus="STT">
                                <tr>
                                    <td class="fw-bold">${STT.index + 1}</td>
                                    <td>${v.videoId}</td>
                                    <td>
                                        <c:if test="${not empty v.poster}">
                                            <c:choose>
                                                <c:when test="${fn:startsWith(v.poster, 'http://') || fn:startsWith(v.poster, 'https://')}">
                                                    <c:url value="${v.poster}" var="imgUrl"></c:url>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:url value="/image?fname=${v.poster}" var="imgUrl"></c:url>
                                                </c:otherwise>
                                            </c:choose>
                                            <img height="80" width="120" src="${imgUrl}" class="rounded img-thumbnail" alt="poster"/>
                                        </c:if>
                                    </td>
                                    <td class="text-start">${v.title}</td>
                                    <td>${v.category.categoryname}</td>
                                    <td>${v.views}</td>
                                    <td>
                                        <c:if test="${v.active == 1}"><span class="badge bg-success">Hoạt động</span></c:if>
                                        <c:if test="${v.active != 1}"><span class="badge bg-secondary">Khóa</span></c:if>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/admin/video/edit?id=${v.videoId}'/>" class="btn btn-sm btn-outline-warning me-1">
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </a>
                                        <a href="<c:url value='/admin/video/delete?id=${v.videoId}'/>" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa video này?');">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listvideo}">
                                <tr>
                                    <td colspan="8" class="text-muted py-4">Chưa có video nào. Hãy bấm "Add Video" để thêm mới!</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="card-footer bg-white">
                <a href="<c:url value='/admin/home'/>" class="btn btn-outline-secondary me-2">
                    <i class="bi bi-arrow-left me-1"></i> Admin Dashboard
                </a>
                <a href="<c:url value='/admin/categories'/>" class="btn btn-outline-primary">
                    <i class="bi bi-tags-fill me-1"></i> Quản lý Thể loại
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
