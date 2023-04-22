package next.controller;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.JdbcTemplate;
import next.dao.UserDao;
import next.model.User;

public class UpdateFormUserController implements Controller {
    private final UserDao userDao = new UserDao(new JdbcTemplate());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = userDao.findByUserId(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        req.setAttribute("user", user);
        return "/user/updateForm.jsp";
    }
}
