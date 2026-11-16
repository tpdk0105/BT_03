<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa Video</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-md-8">
                <div class="card border-0 shadow rounded-3">
                    <div class="card-header bg-warning text-dark py-3">
                        <h4 class="mb-0 fw-bold"><i class="bi bi-pencil-square me-2"></i>Cập Nhật Video</h4>
                    </div>
                    <div class="card-body p-4">
                        <form action="<c:url value='/admin/video/update'/>" method="post" enctype="multipart/form-data">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Mã video:</label>
                                <input type="text" name="videoId" class="form-control" value="${video.videoId}" readonly>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Tiêu đề:</label>
                                <input type="text" name="title" class="form-control" value="${video.title}" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Mô tả:</label>
                                <textarea name="description" class="form-control" rows="3">${video.description}</textarea>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Thể loại (Category):</label>
                                <select name="categoryId" class="form-select" required>
                                    <option value="">-- Chọn thể loại --</option>
                                    <c:forEach items="${listcate}" var="c">
                                        <option value="${c.categoryid}" ${video.category.categoryid == c.categoryid ? 'selected' : ''}>${c.categoryname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Lượt xem:</label>
                                <input type="number" name="views" min="0" class="form-control" value="${video.views}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Link video (YouTube hoặc .mp4):</label>
                                <input type="text" name="videoUrl" class="form-control" value="${video.videoUrl}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Hoặc Upload file Video mới (.mp4 - Multipart):</label>
                                <input type="file" name="videoFile" class="form-control" accept="video/*">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Link poster:</label>
                                <input type="text" name="poster" class="form-control" value="${video.poster}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Upload poster mới (thay thế nếu chọn):</label>
                                <input type="file" name="poster1" class="form-control" accept="image/*">
                            </div>
                            <div class="mb-4 form-check">
                                <input type="checkbox" class="form-check-input" id="active" name="active" value="1" ${video.active == 1 ? 'checked' : ''}>
                                <label class="form-check-label" for="active">Hoạt động</label>
                            </div>
                            <div class="d-flex justify-content-between">
                                <a href="<c:url value='/admin/videos'/>" class="btn btn-outline-secondary"><i class="bi bi-arrow-left me-1"></i> Quay lại</a>
                                <input type="submit" value="Update" class="btn btn-warning fw-bold px-4">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
