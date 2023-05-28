package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.service.DeleteQuestionService;

public class DeleteQuestionController extends AbstractController {
	private DeleteQuestionService deleteQuestionService = new DeleteQuestionService();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));

		deleteQuestionService.delete(questionId);

		return jspView("redirect:/");
	}

}
