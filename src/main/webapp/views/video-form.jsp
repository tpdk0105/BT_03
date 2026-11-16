<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${formMode == 'edit' ? 'Cập Nhật Video' : 'Thêm Video Mới'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-md-8">
                <div class="card border-0 shadow rounded-3">
                    <div class="card-header ${formMode == 'edit' ? 'bg-warning text-dark' : 'bg-success text-white'} py-3">
                        <h4 class="mb-0 fw-bold">
                            <i class="bi ${formMode == 'edit' ? 'bi-pencil-square' : 'bi-plus-circle-fill'} me-2"></i>
                            ${formMode == 'edit' ? 'Cập Nhật Video' : 'Thêm Video Mới'}
                        </h4>
                    </div>
                    <div class="card-body p-4">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger"><i class="bi bi-exclamation-triangle-fill me-2"></i>${error}</div>
                        </c:if>
                        <c:set var="actionUrl" value="${formMode == 'edit' ? '/video/update' : '/video/insert'}" />
                        <form action="<c:url value='${actionUrl}'/>" method="post" enctype="multipart/form-data">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Mã video (VideoId):</label>
                                <input type="text" name="videoId" class="form-control bg-light"
                                       value="${formMode == 'edit' ? video.videoId : nextVideoId}" readonly>
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
                                <label class="form-label fw-bold">Link Video (YouTube hoặc .mp4) <span class="text-danger">*</span></label>
                                <input type="text" name="videoUrl" class="form-control" value="${video.videoUrl}" placeholder="https://www.youtube.com/watch?v=...">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Hoặc Upload file Video (.mp4 - Multipart) <span class="text-danger">*</span></label>
                                <input type="file" name="videoFile" class="form-control" accept="video/*">
                                <div class="form-text">Bắt buộc nhập Link Video hoặc chọn Upload file Video.</div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Link poster <span class="text-danger">*</span></label>
                                <input type="text" name="poster" class="form-control" value="${video.poster}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Hoặc Upload poster (Multipart) <span class="text-danger">*</span></label>
                                <input type="file" name="poster1" class="form-control" accept="image/*">
                                <div class="form-text">Bắt buộc nhập Link Poster hoặc chọn Upload Poster.</div>
                            </div>
                            <div class="mb-4 form-check">
                                <input type="checkbox" class="form-check-input" id="active" name="active" value="1"
                                       ${formMode != 'edit' || video.active == 1 ? 'checked' : ''}>
                                <label class="form-check-label" for="active">Hoạt động</label>
                            </div>
                            <div class="d-flex justify-content-between">
                                <a href="<c:url value='/video'/>" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left me-1"></i>Quay lại
                                </a>
                                <button type="submit" class="btn ${formMode == 'edit' ? 'btn-warning' : 'btn-success'} fw-bold px-4">
                                    ${formMode == 'edit' ? 'Cập nhật' : 'Lưu Video'}
                                </button>
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
