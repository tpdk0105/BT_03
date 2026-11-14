<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Thể Loại (Categories)</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="card border-0 shadow rounded-3">
            <div class="card-header bg-primary text-white py-3 d-flex justify-content-between align-items-center">
                <h4 class="mb-0 fw-bold"><i class="bi bi-tags-fill me-2"></i>Danh Sách Thể Loại (Category List)</h4>
                <a href="<c:url value='/admin/category/add'/>" class="btn btn-light fw-bold text-primary">
                    <i class="bi bi-plus-circle-fill me-1"></i> Add Category
                </a>
            </div>
            <div class="card-body p-4">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle text-center">
                        <thead class="table-dark">
                            <tr>
                                <th style="width: 5%;">STT</th>
                                <th style="width: 25%;">Images</th>
                                <th style="width: 35%;">Category name</th>
                                <th style="width: 15%;">Status</th>
                                <th style="width: 20%;">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listcate}" var="cate" varStatus="STT">
                                <tr>
                                    <td class="fw-bold">${STT.index + 1}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${cate.images != null && (fn:startsWith(cate.images, 'http://') || fn:startsWith(cate.images, 'https://'))}">
                                                <c:url value="${cate.images}" var="imgUrl"></c:url>
                                            </c:when>
                                            <c:otherwise>
                                                <c:url value="/image?fname=${cate.images}" var="imgUrl"></c:url>
                                            </c:otherwise>
                                        </c:choose>
                                        <img height="100" width="140" src="${imgUrl}" class="rounded shadow-sm img-thumbnail" alt="${cate.categoryname}"
                                             onerror="this.src='https://via.placeholder.com/140x100?text=No+Image'"/>
                                    </td>
                                    <td class="fw-semibold text-start ps-3">${cate.categoryname}</td>
                                    <td>
                                        <c:if test="${cate.status == 1}">
                                            <span class="badge bg-success px-3 py-2"><i class="bi bi-check-circle me-1"></i>Hoạt động</span>
                                        </c:if>
                                        <c:if test="${cate.status != 1}">
                                            <span class="badge bg-secondary px-3 py-2"><i class="bi bi-lock-fill me-1"></i>Khóa</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>" class="btn btn-sm btn-outline-warning me-1">
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </a>
                                        <a href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa thể loại này?');">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listcate}">
                                <tr>
                                    <td colspan="5" class="text-muted py-4">Chưa có danh mục nào. Hãy bấm "Add Category" để thêm mới!</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="card-footer bg-white text-muted py-3">
                <a href="<c:url value='/admin/home'/>" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i> Quay lại Admin Dashboard
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
