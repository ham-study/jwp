package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class UpdateQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!UserSessionUtils.isLogined(request.getSession())) {
			return jspView("/user/login.jsp");
		}

		long questionId = Long.parseLong(request.getParameter("questionId"));
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		Question question = questionDao.findById(questionId);

		if (question.isNotWrittenBy(UserSessionUtils.getUserFromSession(request.getSession()))) {
			throw new IllegalStateException("cannot update question!");
		}

		question.update(title, contents);
		questionDao.update(question);
		return jspView(String.format("redirect:/qna/show?questionId=%d", questionId));
	}
}
