<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="card border-0 shadow rounded-4">
                    <div class="card-header bg-warning text-dark text-center py-4 rounded-top-4">
                        <i class="bi bi-key-fill fs-1"></i>
                        <h4 class="fw-bold mt-2 mb-0">Quên Mật Khẩu</h4>
                    </div>
                    <div class="card-body p-4">
                        <p class="text-muted small">Nhập email tài khoản của bạn. Hệ thống sẽ gửi mã OTP xác nhận để đặt lại mật khẩu mới.</p>
                        <c:if test="${not empty alert}">
                            <div class="alert alert-danger"><i class="bi bi-exclamation-triangle-fill me-2"></i>${alert}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                            <div class="mb-4">
                                <label class="form-label fw-semibold">Email</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-envelope-fill"></i></span>
                                    <input type="email" name="email" class="form-control" placeholder="example@gmail.com" required />
                                </div>
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-warning btn-lg fw-bold">
                                    <i class="bi bi-send-fill me-2"></i>Gửi mã OTP
                                </button>
                            </div>
                        </form>

                        <div class="text-center mt-3">
                            <a href="${pageContext.request.contextPath}/login" class="text-decoration-none small">Quay lại đăng nhập</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
