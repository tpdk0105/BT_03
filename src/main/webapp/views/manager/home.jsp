<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bảng Quản Lý - Manager</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card border-0 shadow rounded-3">
                    <div class="card-header bg-info text-white py-3">
                        <h4 class="mb-0 fw-bold"><i class="bi bi-briefcase me-2"></i>Bảng Quản Lý (Manager Dashboard)</h4>
                    </div>
                    <div class="card-body p-4">
                        <div class="alert alert-info">
                            <i class="bi bi-person-check-fill me-2"></i>Bạn đang đăng nhập với tư cách <strong>Quản lý (Manager - Role 2)</strong>.
                        </div>

                        <h5 class="fw-bold mb-3 text-secondary">Thông tin quản lý:</h5>
                        <table class="table table-bordered table-striped">
                            <tbody>
                                <tr>
                                    <th style="width: 30%;">ID</th>
                                    <td>${sessionScope.account.id}</td>
                                </tr>
                                <tr>
                                    <th>Tên đăng nhập (Username)</th>
                                    <td><strong>${sessionScope.account.userName}</strong></td>
                                </tr>
                                <tr>
                                    <th>Họ và tên (Full Name)</th>
                                    <td>${sessionScope.account.fullName}</td>
                                </tr>
                                <tr>
                                    <th>Email</th>
                                    <td>${sessionScope.account.email}</td>
                                </tr>
                                <tr>
                                    <th>Số điện thoại</th>
                                    <td>${sessionScope.account.phone}</td>
                                </tr>
                                <tr>
                                    <th>Mã vai trò (Role ID)</th>
                                    <td><span class="badge bg-info">2 (Manager)</span></td>
                                </tr>
                                <tr>
                                    <th>Ngày tạo</th>
                                    <td>${sessionScope.account.createdDate}</td>
                                </tr>
                            </tbody>
                        </table>

                        <div class="text-end mt-4">
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">
                                <i class="bi bi-box-arrow-right me-1"></i>Đăng xuất
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
