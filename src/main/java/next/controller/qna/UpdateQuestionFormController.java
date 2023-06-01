package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.QuestionDao;
import next.model.Question;

public class UpdateQuestionFormController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));

		Question question = questionDao.findById(questionId);

		return jspView("/qna/update.jsp")
			.addObject("question", question);
	}
}
