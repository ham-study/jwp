package next.controller;

import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.controller.qna.AbstractController;
import next.dao.QuestionDao;
import next.dao.UserDao;

public class HomeController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        QuestionDao questionDao = new QuestionDao();
        return jspView("home.jsp")
            .addAttribute("users", userDao.findAll())
            .addAttribute("questions", questionDao.findAll());
    }
}
