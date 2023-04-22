package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.JdbcTemplate;
import next.dao.UserDao;
import next.model.User;

public class ProfileController implements Controller {
    private final UserDao userDao = new UserDao(new JdbcTemplate());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = userDao.findByUserId(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);
        return "/user/profile.jsp";
    }
}
