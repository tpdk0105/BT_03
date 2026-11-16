<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Tài Khoản & Phân Quyền Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="card border-0 shadow rounded-3">
            <div class="card-header bg-dark text-white py-3 d-flex justify-content-between align-items-center">
                <h4 class="mb-0 fw-bold"><i class="bi bi-people-fill me-2"></i>Quản Lý Tài Khoản & Cấp Quyền Manager</h4>
                <a href="<c:url value='/admin/home'/>" class="btn btn-outline-light btn-sm">
                    <i class="bi bi-arrow-left me-1"></i>Admin Dashboard
                </a>
            </div>
            <div class="card-body p-4">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle text-center">
                        <thead class="table-secondary">
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Họ và tên</th>
                                <th>Email</th>
                                <th>Trạng thái</th>
                                <th>Vai trò hiện tại</th>
                                <th>Phân quyền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listuser}" var="u">
                                <tr>
                                    <td class="fw-bold">${u.id}</td>
                                    <td class="fw-semibold">${u.userName}</td>
                                    <td class="text-start">${u.fullName}</td>
                                    <td class="text-start">${u.email}</td>
                                    <td>
                                        <c:if test="${u.status == 1}">
                                            <span class="badge bg-success">Đã kích hoạt</span>
                                        </c:if>
                                        <c:if test="${u.status != 1}">
                                            <span class="badge bg-warning text-dark">Chờ OTP</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${u.roleid == 1}"><span class="badge bg-danger">Admin (1)</span></c:when>
                                            <c:when test="${u.roleid == 2}"><span class="badge bg-info text-dark">Manager (2)</span></c:when>
                                            <c:otherwise><span class="badge bg-secondary">User (3)</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${u.roleid == 1}">
                                                <span class="text-muted small">Mặc định Admin</span>
                                            </c:when>
                                            <c:when test="${u.roleid == 2}">
                                                <a href="<c:url value='/admin/user/role?id=${u.id}&role=3'/>"
                                                   class="btn btn-sm btn-outline-secondary"
                                                   onclick="return confirm('Hạ tài khoản ${u.userName} xuống User?');">
                                                    <i class="bi bi-arrow-down-circle me-1"></i>Hạ xuống User
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value='/admin/user/role?id=${u.id}&role=2'/>"
                                                   class="btn btn-sm btn-info fw-bold"
                                                   onclick="return confirm('Cấp quyền Manager cho ${u.userName}?');">
                                                    <i class="bi bi-arrow-up-circle me-1"></i>Cấp quyền Manager
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
