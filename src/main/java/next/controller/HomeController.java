package next.controller;

import core.db.DataBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
	private static final HomeController INSTANCE = new HomeController();

	private HomeController() {

	}

	public static HomeController getInstance() {
		return INSTANCE;
	}

	@Override
	public String service(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("users", DataBase.findAll());
		return "index.jsp";
	}
}
