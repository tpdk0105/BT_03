package vn.iotstar.util;

public class Constant {
    public static final String SESSION_USERNAME = "username";
    public static final String COOKIE_REMEMBER = "username";
    public static final String REGISTER = "/views/register.jsp";
    public static final String LOGIN = "/views/login.jsp";
    public static final String VERIFY_OTP = "/views/verify-otp.jsp";
    public static final String FORGOT_PASSWORD = "/views/forgot-password.jsp";
    public static final String RESET_PASSWORD = "/views/reset-password.jsp";
    public static final String DIR = "C:\\upload";

    // Cấu hình SMTP Gmail để gửi OTP qua email (điền Gmail + App Password để gửi mail thật)
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PORT = "587";
    public static final String EMAIL_FROM = "coc367325@gmail.com";
    public static final String EMAIL_PASSWORD = "lrhzaibtcbsyafvo";
}
