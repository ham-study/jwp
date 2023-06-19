package next.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

@Controller
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private UserDao userDao = UserDao.getInstance();

	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public ModelAndView join(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = new User(request.getParameter("userId"), request.getParameter("password"),
			request.getParameter("name"), request.getParameter("email"));
		log.debug("User : {}", user);
		userDao.insert(user);
		return new ModelAndView(new JspView("redirect:/"));
	}

	@RequestMapping(value = "/users")
	public ModelAndView getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		ModelAndView mav = new ModelAndView(new JspView("/user/list.jsp"));
		mav.addObject("users", userDao.findAll());
		return mav;
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		User user = userDao.findByUserId(userId);

		if (user == null) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}

		if (user.matchPassword(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return new ModelAndView(new JspView("redirect:/"));
		} else {
			throw new IllegalStateException("비밀번호가 틀립니다.");
		}
	}

	@RequestMapping(value = "/users/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return new ModelAndView(new JspView("redirect:/"));
	}

	@RequestMapping(value = "/users/profile")
	public ModelAndView getProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView(new JspView("/user/profile.jsp"));
		mav.addObject("user", userDao.findByUserId(userId));
		return mav;
	}

	@RequestMapping(value = "/users/updateForm")
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = userDao.findByUserId(request.getParameter("userId"));

		if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}
		ModelAndView mav = new ModelAndView(new JspView("/user/updateForm.jsp"));
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest req, HttpServletResponse response) throws Exception {
		User user = userDao.findByUserId(req.getParameter("userId"));

		if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}

		User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
			req.getParameter("email"));
		log.debug("Update User : {}", updateUser);
		user.update(updateUser);
		userDao.update(user);
		return new ModelAndView(new JspView("redirect:/"));
	}
}
