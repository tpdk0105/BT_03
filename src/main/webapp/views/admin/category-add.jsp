<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Thể Loại Mới - Add Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-6 col-md-8">
                <div class="card border-0 shadow rounded-3">
                    <div class="card-header bg-success text-white py-3">
                        <h4 class="mb-0 fw-bold"><i class="bi bi-plus-circle-fill me-2"></i>Thêm Thể Loại (Add Category)</h4>
                    </div>
                    <div class="card-body p-4">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger"><i class="bi bi-exclamation-triangle-fill me-2"></i>${error}</div>
                        </c:if>
                        <form action="<c:url value='/admin/category/insert'/>" method="post" enctype="multipart/form-data">
                            
                            <div class="mb-3">
                                <label for="categoryname" class="form-label fw-bold">Category name:</label>
                                <input type="text" id="categoryname" name="categoryname" class="form-control" placeholder="Nhập tên thể loại" value="${categoryname}" required>
                            </div>

                            <div class="mb-3">
                                <label for="images" class="form-label fw-bold">Link images:</label>
                                <input type="text" id="images" name="images" class="form-control" placeholder="URL hình ảnh (nếu có)" value="${images}">
                            </div>

                            <div class="mb-3">
                                <label for="images1" class="form-label fw-bold">Upload images:</label>
                                <input type="file" id="images1" name="images1" class="form-control" accept="image/*">
                            </div>

                            <div class="mb-4">
                                <label class="form-label fw-bold d-block">Status</label>
                                <div class="form-check form-check-inline">
                                    <input type="radio" id="ston" name="status" value="1" class="form-check-input" checked>
                                    <label for="ston" class="form-check-label text-success fw-semibold">Hoạt động</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input type="radio" id="stoff" name="status" value="0" class="form-check-input">
                                    <label for="stoff" class="form-check-label text-secondary fw-semibold">Khóa</label>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between align-items-center">
                                <a href="<c:url value='/admin/categories'/>" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left me-1"></i> Quay lại
                                </a>
                                <input type="submit" value="Insert" class="btn btn-success fw-bold px-4">
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
