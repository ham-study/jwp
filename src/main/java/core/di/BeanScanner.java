package core.di;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;

public class BeanScanner {
	private final Reflections reflections;

	public BeanScanner(Object... basePackages) {
		this.reflections = new Reflections(basePackages);
	}

	public Set<Class<?>> scan() {
		return getTypesAnnotationWith(Controller.class, Service.class, Repository.class);
	}

	@SafeVarargs
	private final Set<Class<?>> getTypesAnnotationWith(Class<? extends Annotation>... annotations) {
		Set<Class<?>> preInstantiateBeans = new HashSet<>();

		for (Class<? extends Annotation> annotation : annotations) {
			preInstantiateBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
		}

		return preInstantiateBeans;
	}
}
