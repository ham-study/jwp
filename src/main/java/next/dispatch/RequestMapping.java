package next.dispatch;

import java.util.HashMap;
import java.util.Map;

import next.controller.Controller;

public class RequestMapping {
	private final Map<String, Controller> handlers = new HashMap<>();

	public void addHandler(String url, Controller handler) {
		handlers.put(url, handler);
	}

	public Controller getHandler(String url) {
		return handlers.get(url);
	}
}
