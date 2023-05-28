package next.controller.qna;

import java.util.List;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;

public class ListQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = jsonView();
		try {
			List<Question> questions = questionDao.findAll();
			mav.addObject("questions", questions);
		} catch (DataAccessException e) {
			mav.addObject("result", Result.fail(e.getMessage()));
		}
		return mav;
	}
}
