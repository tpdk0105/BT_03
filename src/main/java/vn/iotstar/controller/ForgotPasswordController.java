package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/forgot-password", "/reset-password" })
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/reset-password")) {
            req.getRequestDispatcher(Constant.RESET_PASSWORD).forward(req, resp);
        } else {
            req.getRequestDispatcher(Constant.FORGOT_PASSWORD).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();
        UserService service = new UserServiceImpl();

        if (url.contains("/forgot-password")) {
            String email = req.getParameter("email");
            if (email == null || email.trim().isEmpty()) {
                req.setAttribute("alert", "Vui lòng nhập email đã đăng ký!");
                req.getRequestDispatcher(Constant.FORGOT_PASSWORD).forward(req, resp);
                return;
            }
            email = email.trim();
            if (!service.sendForgotPasswordOtp(email)) {
                req.setAttribute("alert", "Email không tồn tại trong hệ thống!");
                req.getRequestDispatcher(Constant.FORGOT_PASSWORD).forward(req, resp);
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("resetEmail", email);
            if (Constant.EMAIL_FROM == null || Constant.EMAIL_FROM.trim().isEmpty()) {
                User u = service.getByEmail(email);
                if (u != null) {
                    session.setAttribute("devResetOtpNotice", "Chế độ kiểm thử (chưa cấu hình SMTP Gmail) - Mã OTP đặt lại mật khẩu: " + u.getCode());
                }
            } else {
                session.removeAttribute("devResetOtpNotice");
            }
            resp.sendRedirect(req.getContextPath() + "/reset-password");
        } else if (url.contains("/reset-password")) {
            String email = req.getParameter("email");
            String otp = req.getParameter("otp");
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");

            if (email == null || email.trim().isEmpty() || otp == null || otp.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                req.setAttribute("alert", "Vui lòng nhập đầy đủ thông tin!");
                req.getRequestDispatcher(Constant.RESET_PASSWORD).forward(req, resp);
                return;
            }
            if (!password.trim().equals(confirmPassword != null ? confirmPassword.trim() : "")) {
                req.setAttribute("alert", "Mật khẩu xác nhận không khớp!");
                req.getRequestDispatcher(Constant.RESET_PASSWORD).forward(req, resp);
                return;
            }
            if (service.resetPassword(email.trim(), otp.trim(), password.trim())) {
                HttpSession session = req.getSession(false);
                if (session != null) {
                    session.removeAttribute("resetEmail");
                    session.removeAttribute("devResetOtpNotice");
                }
                req.setAttribute("alertSuccess", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập bằng mật khẩu mới.");
                req.getRequestDispatcher(Constant.LOGIN).forward(req, resp);
            } else {
                req.setAttribute("alert", "Mã OTP không chính xác hoặc email không hợp lệ!");
                req.getRequestDispatcher(Constant.RESET_PASSWORD).forward(req, resp);
            }
        }
    }
}
