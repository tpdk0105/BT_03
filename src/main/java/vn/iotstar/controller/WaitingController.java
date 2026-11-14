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

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User u = (User) session.getAttribute("account");
            if (u == null && session.getAttribute("username") != null) {
                UserService service = new UserServiceImpl();
                u = service.get((String) session.getAttribute("username"));
                if (u != null) {
                    session.setAttribute("account", u);
                }
            }

            if (u != null) {
                req.setAttribute("username", u.getUserName());
                if (u.getRoleid() == 1) {
                    resp.sendRedirect(req.getContextPath() + "/admin/home");
                } else if (u.getRoleid() == 2) {
                    resp.sendRedirect(req.getContextPath() + "/manager/home");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/home");
                }
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
