package vn.iotstar.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        // Check cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant.COOKIE_REMEMBER.equals(cookie.getName())) {
                    UserService service = new UserServiceImpl();
                    User user = service.get(cookie.getValue());
                    if (user != null) {
                        session = req.getSession(true);
                        session.setAttribute("account", user);
                        session.setAttribute("username", user.getUserName());
                        resp.sendRedirect(req.getContextPath() + "/waiting");
                        return;
                    }
                }
            }
        }
        req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String username = trim(req.getParameter("username"));
        String password = trim(req.getParameter("password"));
        String email = trim(req.getParameter("email"));
        String fullname = trim(req.getParameter("fullname"));
        String phone = trim(req.getParameter("phone"));

        UserService service = new UserServiceImpl();
        String alertMsg = "";

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            alertMsg = "Vui lòng điền đầy đủ các thông tin bắt buộc!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            alertMsg = "Email không hợp lệ! Vui lòng nhập đúng cú pháp Gmail (ví dụ: tenban@gmail.com).";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        if (service.checkExistEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        if (service.checkExistUsername(username)) {
            alertMsg = "Tài khoản đã tồn tại!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
            return;
        }

        boolean isSuccess = service.register(username, password, email, fullname, phone);
        if (isSuccess) {
            boolean mailSent = service.sendActivationOtp(email);
            HttpSession session = req.getSession(true);
            session.setAttribute("pendingEmail", email);
            if (!mailSent) {
                User created = service.getByEmail(email);
                session.setAttribute("devOtpNotice", "Chế độ kiểm thử (chưa cấu hình SMTP Gmail trong Constant.java) - Mã OTP của bạn là: " + created.getCode());
            } else {
                session.removeAttribute("devOtpNotice");
            }
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } else {
            alertMsg = "System error!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
        }
    }
}
