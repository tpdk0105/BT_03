package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.User;

public interface UserService {
    User login(String username, String password);
    User get(String username);
    User getByEmail(String email);
    User findById(int id);
    List<User> findAll();
    boolean updateRole(int userId, int newRoleId);
    void insert(User user);
    void update(User user);
    boolean register(String username, String password, String email, String fullname, String phone);
    boolean sendActivationOtp(String email);
    boolean verifyOtp(String email, String otp);
    boolean resendOtp(String email);
    boolean sendForgotPasswordOtp(String email);
    boolean resetPassword(String email, String otp, String newPassword);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
}
