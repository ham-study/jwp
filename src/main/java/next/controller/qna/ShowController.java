package next.controller.qna;

import java.util.List;

import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class ShowController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		long questionId = Long.parseLong(req.getParameter("questionId"));

		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();

		Question question = questionDao.findById(questionId);
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);

		return jspView("/qna/show.jsp")
			.addAttribute("question", question)
			.addAttribute("answers", answers);
	}
}
