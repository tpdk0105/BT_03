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
@WebServlet(urlPatterns = { "/verify-otp", "/resend-otp" })
public class VerifyOtpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/resend-otp")) {
            HttpSession session = req.getSession(false);
            String email = req.getParameter("email");
            if ((email == null || email.trim().isEmpty()) && session != null) {
                email = (String) session.getAttribute("pendingEmail");
            }
            if (email != null && !email.trim().isEmpty()) {
                UserService service = new UserServiceImpl();
                boolean mailSent = service.resendOtp(email.trim());
                if (!mailSent && session != null) {
                    User u = service.getByEmail(email.trim());
                    if (u != null) {
                        session.setAttribute("devOtpNotice", "Chế độ kiểm thử (chưa cấu hình SMTP Gmail) - Mã OTP mới là: " + u.getCode());
                    }
                } else if (session != null) {
                    session.removeAttribute("devOtpNotice");
                }
                req.setAttribute("alertSuccess", "Đã gửi lại mã OTP mới tới email " + email);
            }
        }
        req.getRequestDispatcher(Constant.VERIFY_OTP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String otp = req.getParameter("otp");

        if (email == null || email.trim().isEmpty() || otp == null || otp.trim().isEmpty()) {
            req.setAttribute("alert", "Vui lòng nhập đầy đủ Email và mã OTP!");
            req.getRequestDispatcher(Constant.VERIFY_OTP).forward(req, resp);
            return;
        }

        UserService service = new UserServiceImpl();
        if (service.verifyOtp(email.trim(), otp.trim())) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.removeAttribute("pendingEmail");
                session.removeAttribute("devOtpNotice");
            }
            req.setAttribute("alertSuccess", "Kích hoạt tài khoản thành công! Vui lòng đăng nhập.");
            req.getRequestDispatcher(Constant.LOGIN).forward(req, resp);
        } else {
            req.setAttribute("alert", "Mã OTP không đúng hoặc email không hợp lệ!");
            req.getRequestDispatcher(Constant.VERIFY_OTP).forward(req, resp);
        }
    }
}
