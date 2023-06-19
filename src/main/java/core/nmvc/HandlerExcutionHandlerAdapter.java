package core.nmvc;

import core.mvc.HandlerAdapter;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HandlerExcutionHandlerAdapter implements HandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return handler instanceof HandlerExecution;
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			return ((HandlerExecution) handler).handle(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
