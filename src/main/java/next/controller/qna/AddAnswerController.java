package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import next.view.ModelAndView;

public class AddAnswerController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
			Long.parseLong(req.getParameter("questionId")));
		log.debug("answer : {}", answer);

		AnswerDao answerDao = new AnswerDao();
		Answer savedAnswer = answerDao.insert(answer);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setModelAttribute("answer", savedAnswer);

		return modelAndView;
	}
}
