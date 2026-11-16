package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.util.EmailUtil;
import vn.iotstar.util.PasswordUtil;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user != null && PasswordUtil.checkPassword(password, user.getPassWord())) {
            // Nếu tài khoản cũ chưa hash mật khẩu thì tự động mã hóa lại
            if (!PasswordUtil.isHashed(user.getPassWord())) {
                user.setPassWord(PasswordUtil.hashPassword(password));
                userDao.update(user);
            }
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean updateRole(int userId, int newRoleId) {
        if (newRoleId != 2 && newRoleId != 3) {
            return false;
        }
        User u = userDao.findById(userId);
        if (u == null || u.getRoleid() == 1) {
            return false;
        }
        u.setRoleid(newRoleId);
        userDao.update(u);
        return true;
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if (userDao.checkExistUsername(username) || userDao.checkExistEmail(email)) {
            return false;
        }
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        User user = new User(email, username, fullname, PasswordUtil.hashPassword(password), null, 3, phone, date);
        user.setStatus(0); // Chưa kích hoạt, chờ xác nhận OTP
        user.setCode(EmailUtil.generateOtp());
        userDao.insert(user);
        return true;
    }

    @Override
    public boolean sendActivationOtp(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        if (user.getCode() == null || user.getCode().trim().isEmpty()) {
            user.setCode(EmailUtil.generateOtp());
            userDao.update(user);
        }
        String subject = "[Hệ Thống ABC] Mã OTP kích hoạt tài khoản";
        String body = "Xin chào " + (user.getFullName() != null ? user.getFullName() : user.getUserName())
                + ",\n\nMã OTP kích hoạt tài khoản của bạn là: " + user.getCode()
                + "\nVui lòng nhập mã này để hoàn tất đăng ký.";
        return EmailUtil.sendOtpEmail(email, subject, body);
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        User user = userDao.getByEmail(email.trim());
        if (user != null && user.getCode() != null && user.getCode().equals(otp.trim())) {
            user.setStatus(1);
            user.setCode(null);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean resendOtp(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        user.setCode(EmailUtil.generateOtp());
        userDao.update(user);
        return sendActivationOtp(email);
    }

    @Override
    public boolean sendForgotPasswordOtp(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        user.setCode(EmailUtil.generateOtp());
        userDao.update(user);
        String subject = "[Hệ Thống ABC] Mã OTP đặt lại mật khẩu";
        String body = "Xin chào " + (user.getFullName() != null ? user.getFullName() : user.getUserName())
                + ",\n\nMã OTP đặt lại mật khẩu của bạn là: " + user.getCode()
                + "\nNếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.";
        EmailUtil.sendOtpEmail(email, subject, body);
        return true;
    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        if (email == null || otp == null || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        User user = userDao.getByEmail(email.trim());
        if (user != null && user.getCode() != null && user.getCode().equals(otp.trim())) {
            user.setPassWord(PasswordUtil.hashPassword(newPassword.trim()));
            user.setCode(null);
            user.setStatus(1);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }
}
