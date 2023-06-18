package core.nmvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import core.annotation.Controller;

public class ControllerScanner {
	private Reflections reflections;
	public ControllerScanner(Object[] basePackage) {
		this.reflections = new Reflections(basePackage);
	}

	public Map<Class<?>, Object> getControllers() {
		Set<Class<?>> preInstantiateControllers = reflections.getTypesAnnotatedWith(Controller.class);
		return instantiateControllers(preInstantiateControllers);
	}

	private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInstantiateControllers) {
		Map<Class<?>, Object> controllers = new HashMap<>();
		for (Class<?> controller : preInstantiateControllers) {
			try {
				Object instance = controller.getDeclaredConstructor().newInstance();
				controllers.put(controller, instance);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return controllers;
	}
}
