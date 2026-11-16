package vn.iotstar.util;

import java.security.SecureRandom;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOtp() {
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }

    /**
     * Gửi mã OTP qua email SMTP.
     * @return true nếu gửi email thật thành công; false nếu chưa cấu hình SMTP hoặc gửi thất bại.
     */
    public static boolean sendOtpEmail(String toEmail, String subject, String bodyText) {
        if (Constant.EMAIL_FROM == null || Constant.EMAIL_FROM.trim().isEmpty()
                || Constant.EMAIL_PASSWORD == null || Constant.EMAIL_PASSWORD.trim().isEmpty()) {
            System.out.println("[OTP-DEV] Chua cau hinh EMAIL_FROM/EMAIL_PASSWORD. Noi dung gui toi " + toEmail + ": " + bodyText);
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", Constant.SMTP_HOST);
        props.put("mail.smtp.port", Constant.SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", Constant.SMTP_HOST);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constant.EMAIL_FROM, Constant.EMAIL_PASSWORD);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(Constant.EMAIL_FROM));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(bodyText, "UTF-8");
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[OTP-FALLBACK] Loi gui mail toi " + toEmail + ". Noi dung: " + bodyText);
            return false;
        }
    }
}
