<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo Tài Khoản Mới</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light d-flex flex-column min-vh-100">

    <jsp:include page="/views/common/topbar.jsp" />

    <div class="container my-auto py-4">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="card border-0 shadow rounded-3">
                    <div class="card-header bg-primary text-white text-center py-3">
                        <i class="bi bi-person-plus-fill fs-1"></i>
                        <h4 class="mt-2 mb-0 fw-bold">Tạo Tài Khoản Mới</h4>
                    </div>
                    <div class="card-body p-4">
                        <!-- Thông báo lỗi -->
                        <c:if test="${alert != null}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>${alert}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/register" method="post">
                            <!-- Tài khoản -->
                            <div class="mb-3">
                                <label class="form-label fw-semibold" for="username">Tài khoản <span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person"></i></span>
                                    <input type="text"
                                           class="form-control"
                                           id="username"
                                           name="username"
                                           placeholder="Tài khoản"
                                           value="${param.username}"
                                           required />
                                </div>
                            </div>

                            <!-- Họ và tên -->
                            <div class="mb-3">
                                <label class="form-label fw-semibold" for="fullname">Họ tên</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-card-text"></i></span>
                                    <input type="text"
                                           class="form-control"
                                           id="fullname"
                                           name="fullname"
                                           placeholder="Họ tên"
                                           value="${param.fullname}" />
                                </div>
                            </div>

                            <!-- Email -->
                            <div class="mb-3">
                                <label class="form-label fw-semibold" for="email">Nhập Email <span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                    <input type="email"
                                           class="form-control"
                                           id="email"
                                           name="email"
                                           placeholder="Nhập Email"
                                           value="${param.email}"
                                           required />
                                </div>
                            </div>

                            <!-- Điện thoại -->
                            <div class="mb-3">
                                <label class="form-label fw-semibold" for="phone">Số điện thoại</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-telephone"></i></span>
                                    <input type="tel"
                                           class="form-control"
                                           id="phone"
                                           name="phone"
                                           placeholder="Số điện thoại"
                                           value="${param.phone}" />
                                </div>
                            </div>

                            <!-- Mật khẩu -->
                            <div class="mb-4">
                                <label class="form-label fw-semibold" for="password">Mật khẩu <span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                    <input type="password"
                                           class="form-control"
                                           id="password"
                                           name="password"
                                           placeholder="Mật khẩu"
                                           required />
                                </div>
                            </div>

                            <!-- Nút tạo tài khoản -->
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-lg fw-bold">
                                    <i class="bi bi-check2-circle me-2"></i>Tạo tài khoản
                                </button>
                            </div>
                        </form>

                        <hr class="my-4" />

                        <div class="text-center">
                            <span class="text-muted">Nếu bạn đã có tài khoản? </span>
                            <a href="${pageContext.request.contextPath}/login" class="text-decoration-none fw-bold">Đăng nhập</a>
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
