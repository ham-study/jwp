package next.controller;

import core.db.DataBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import next.model.User;

public class LoginController implements Controller {
    private static final LoginController INSTANCE = new LoginController();

    private LoginController() {
    }

    public static LoginController getInstance() {
        return INSTANCE;
    }

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().equals("/users/loginForm")) {
            return "/user/login.jsp";
        }

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            request.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
            request.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
    }
}
