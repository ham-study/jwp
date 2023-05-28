package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.model.Result;
import next.service.DeleteQuestionService;

public class ApiDeleteQuestionController extends AbstractController {

	private DeleteQuestionService deleteQuestionService = new DeleteQuestionService();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));
		ModelAndView mav = jspView("redirect:/");
		try {
			deleteQuestionService.delete(questionId);
			mav.addObject("result", Result.ok());
		} catch (Exception e) {
			mav.addObject("result", Result.fail(e.getMessage()));
		}

		return mav;
	}
}
