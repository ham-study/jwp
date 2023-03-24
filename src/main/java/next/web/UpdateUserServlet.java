package next.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.model.User;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(UpdateUserServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");

		User user = DataBase.findUserById(userId);
		user.update(
			req.getParameter("password"),
			req.getParameter("name"),
			req.getParameter("email")
		);

		resp.sendRedirect("/user/list");
	}
}
