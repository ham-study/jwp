package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.JdbcTemplate;
import next.dao.UserDao;
import next.model.User;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    private final UserDao userDao = new UserDao(new JdbcTemplate());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = userDao.findByUserId(req.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);

        userDao.update(user);
        return "redirect:/";
    }
}
