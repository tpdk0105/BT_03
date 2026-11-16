<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực OTP Kích Hoạt Tài Khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="card border-0 shadow rounded-4">
                    <div class="card-header bg-success text-white text-center py-4 rounded-top-4">
                        <i class="bi bi-envelope-check-fill fs-1"></i>
                        <h4 class="fw-bold mt-2 mb-0">Kích Hoạt Tài Khoản (OTP)</h4>
                    </div>
                    <div class="card-body p-4">
                        <c:if test="${not empty alert}">
                            <div class="alert alert-danger"><i class="bi bi-exclamation-triangle-fill me-2"></i>${alert}</div>
                        </c:if>
                        <c:if test="${not empty alertSuccess}">
                            <div class="alert alert-success"><i class="bi bi-check-circle-fill me-2"></i>${alertSuccess}</div>
                        </c:if>
                        <c:if test="${not empty sessionScope.devOtpNotice}">
                            <div class="alert alert-info small"><i class="bi bi-info-circle-fill me-2"></i>${sessionScope.devOtpNotice}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/verify-otp" method="post">
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Email đã đăng ký</label>
                                <input type="email" name="email" class="form-control"
                                       value="${not empty param.email ? param.email : sessionScope.pendingEmail}" required />
                            </div>
                            <div class="mb-4">
                                <label class="form-label fw-semibold">Mã OTP (6 số)</label>
                                <input type="text" name="otp" class="form-control text-center fs-5 fw-bold"
                                       placeholder="Nhập mã OTP" maxlength="6" required />
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-success btn-lg fw-bold">
                                    <i class="bi bi-check2-circle me-2"></i>Xác nhận kích hoạt
                                </button>
                            </div>
                        </form>

                        <div class="d-flex justify-content-between mt-3 small">
                            <a href="${pageContext.request.contextPath}/resend-otp" class="text-decoration-none fw-semibold">
                                <i class="bi bi-arrow-repeat me-1"></i>Gửi lại mã OTP
                            </a>
                            <a href="${pageContext.request.contextPath}/login" class="text-decoration-none text-muted">
                                Quay lại đăng nhập
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
