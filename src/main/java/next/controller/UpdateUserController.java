package next.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import next.model.User;

@WebServlet(value = { "/users/update",  })
public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);
    private static final UpdateUserController INSTANCE = new UpdateUserController();

    private UpdateUserController() {
    }

    public static UpdateUserController getInstance() {
        return INSTANCE;
    }

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (request.getRequestURI().equals("/users/updateForm")) {
            if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
                throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
            }
            request.setAttribute("user", user);
            return "/user/updateForm.jsp";
        }

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
            request.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);

        return "redirect:/";
    }
}
