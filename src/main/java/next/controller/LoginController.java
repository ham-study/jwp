package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import next.dao.JdbcTemplate;
import next.dao.UserDao;
import next.model.User;

public class LoginController implements Controller {
    private final UserDao userDao = new UserDao(new JdbcTemplate());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = userDao.findByUserId(userId);

        if (user == null) {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
    }
}
