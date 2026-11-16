package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/admin/users", "/admin/user/role" })
public class AdminUserController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User acc = (User) session.getAttribute("account");
        if (acc.getRoleid() != 1) {
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }

        String url = req.getRequestURI();
        if (url.contains("/admin/user/role")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                int role = Integer.parseInt(req.getParameter("role"));
                userService.updateRole(id, role);
            } catch (Exception ignored) {
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        List<User> list = userService.findAll();
        req.setAttribute("listuser", list);
        req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
    }
}
