package next.web;

import java.io.IOException;

import core.db.DataBase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.model.User;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");

		User user = DataBase.findUserById(userId);

		if (user.login(password)) {
			req.getSession()
				.setAttribute("user", user);
			resp.sendRedirect("/index.jsp");
		} else {
			resp.sendRedirect("/user/login_failed.html");
		}
	}
}
