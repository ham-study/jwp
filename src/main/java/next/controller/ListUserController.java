package next.controller;

import core.db.DataBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
    private static final ListUserController INSTANCE = new ListUserController();

    private ListUserController() {
    }

    public static ListUserController getInstance() {
        return INSTANCE;
    }

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }

        request.setAttribute("users", DataBase.findAll());

        return "/user/list.jsp";
    }
}
