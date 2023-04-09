package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.model.User;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    private static final CreateUserController INSTANCE = new CreateUserController();

    private CreateUserController() {
    }

    public static CreateUserController getInstance() {
        return INSTANCE;
    }

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().equals("/users/form")) {
            return "/user/form.jsp";
        }

        if (request.getRequestURI().equals("/users/create")) {
            User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
                request.getParameter("email"));
            log.debug("User : {}", user);

            DataBase.addUser(user);

            return "redirect:/";
        }

        throw new UnsupportedOperationException();
    }
}
