package next.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutController implements Controller {
    private static final LogoutController INSTANCE = new LogoutController();

    private LogoutController() {
    }

    public static LogoutController getInstance() {
        return INSTANCE;
    }

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
