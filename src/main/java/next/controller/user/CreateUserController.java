package next.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.controller.qna.AbstractController;
import next.dao.UserDao;
import next.model.User;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        UserDao userDao = new UserDao();
        userDao.insert(user);
        return jspView("redirect:/");
    }
}
