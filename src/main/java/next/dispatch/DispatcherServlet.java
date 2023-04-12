package next.dispatch;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.controller.Controller;

public class DispatcherServlet extends HttpServlet {
	private final RequestMapping requestMapping;

	public DispatcherServlet(RequestMapping requestMapping) {
		this.requestMapping = requestMapping;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatch(req, resp);
	}

	private void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		Controller handler = requestMapping.getHandler(requestURI);

		String viewUrl = handler.service(request, response);
		resolveView(request, response, viewUrl);
	}

	private void resolveView(HttpServletRequest request, HttpServletResponse response, String viewUrl) throws IOException, ServletException {
		if (viewUrl == null) {
			response.sendRedirect("");
			return;
		}

		if (viewUrl.startsWith("redirect:")) {
			response.sendRedirect(viewUrl.replace("redirect:", ""));
			return;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewUrl);
		requestDispatcher.forward(request, response);
	}
}
