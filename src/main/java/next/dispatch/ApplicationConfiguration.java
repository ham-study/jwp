package next.dispatch;

import next.controller.CreateUserController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;

public class ApplicationConfiguration {
	public void addHandlers(RequestMapping requestMapping) {
		requestMapping.addHandler("/users/create", CreateUserController.getInstance());
		requestMapping.addHandler("/users/form", CreateUserController.getInstance());

		requestMapping.addHandler("", HomeController.getInstance());
		requestMapping.addHandler("/", HomeController.getInstance());

		requestMapping.addHandler("/users", ListUserController.getInstance());

		requestMapping.addHandler("/users/login", LoginController.getInstance());
		requestMapping.addHandler("/users/loginForm", LoginController.getInstance());

		requestMapping.addHandler("/users/logout", LogoutController.getInstance());

		requestMapping.addHandler("/users/profile", ProfileController.getInstance());

		requestMapping.addHandler("/users/updateForm", UpdateUserController.getInstance());
		requestMapping.addHandler("/users/update", UpdateUserController.getInstance());
	}
}
