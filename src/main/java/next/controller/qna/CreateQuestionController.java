package next.controller.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class CreateQuestionController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(CreateQuestionController.class);
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return jspView("redirect:/users/loginForm");
		}

		User user = UserSessionUtils.getUserFromSession(request.getSession());

		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		Question question = new Question(user.getName(), title, contents);

		questionDao.insert(question);

		logger.info("Question is created! {}", question);


		return jspView("redirect:/")
			.addObject("questions", questionDao.findAll());
	}
}
