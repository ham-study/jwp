package core.mvc;

import jakarta.servlet.http.HttpServletRequest;

public interface HandlerMapping {
	Object getHandler(HttpServletRequest request);
}
