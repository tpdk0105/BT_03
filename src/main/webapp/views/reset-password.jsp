<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Lại Mật Khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="card border-0 shadow rounded-4">
                    <div class="card-header bg-primary text-white text-center py-4 rounded-top-4">
                        <i class="bi bi-shield-lock-fill fs-1"></i>
                        <h4 class="fw-bold mt-2 mb-0">Đặt Lại Mật Khẩu (OTP)</h4>
                    </div>
                    <div class="card-body p-4">
                        <c:if test="${not empty alert}">
                            <div class="alert alert-danger"><i class="bi bi-exclamation-triangle-fill me-2"></i>${alert}</div>
                        </c:if>
                        <c:if test="${not empty sessionScope.devResetOtpNotice}">
                            <div class="alert alert-info small"><i class="bi bi-info-circle-fill me-2"></i>${sessionScope.devResetOtpNotice}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/reset-password" method="post">
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Email</label>
                                <input type="email" name="email" class="form-control"
                                       value="${not empty param.email ? param.email : sessionScope.resetEmail}" required />
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Mã OTP</label>
                                <input type="text" name="otp" class="form-control fw-bold" maxlength="6" placeholder="Nhập mã OTP 6 số" required />
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Mật khẩu mới</label>
                                <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu mới" required />
                            </div>
                            <div class="mb-4">
                                <label class="form-label fw-semibold">Xác nhận mật khẩu mới</label>
                                <input type="password" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu mới" required />
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-lg fw-bold">
                                    <i class="bi bi-check-circle-fill me-2"></i>Cập nhật mật khẩu
                                </button>
                            </div>
                        </form>

                        <div class="text-center mt-3">
                            <a href="${pageContext.request.contextPath}/forgot-password" class="text-decoration-none small">Gửi lại OTP</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
