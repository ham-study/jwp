package next.controller.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.QuestionDao;
import next.model.Question;

public class CreateQuestionController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(CreateQuestionController.class);
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		Question question = new Question(writer, title, contents);

		questionDao.insert(question);

		logger.info("Question is created! {}", question);


		return jspView("/").addObject("questions", questionDao.findAll());
	}
}
