package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.JdbcTemplate;
import next.dao.UserDao;

public class ListUserController implements Controller {
    private final UserDao userDao = new UserDao(new JdbcTemplate());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        req.setAttribute("users", userDao.findAll());
        return "/user/list.jsp";
    }
}
