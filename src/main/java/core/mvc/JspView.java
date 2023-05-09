package core.mvc;

import java.util.Map;
import java.util.Objects;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JspView implements View {
	private static final String REDIRECT_PREFIX = "redirect:";

	private String viewName;

	public JspView(String viewName) {
		Objects.requireNonNull(viewName, "viewName");

		this.viewName = viewName;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (viewName.startsWith(REDIRECT_PREFIX)) {
			response.sendRedirect(viewName.substring(viewName.indexOf(REDIRECT_PREFIX.length())));
			return;
		}

		for (String key : model.keySet()) {
			request.setAttribute(key, model.get(key));
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
		requestDispatcher.forward(request, response);
	}
}
