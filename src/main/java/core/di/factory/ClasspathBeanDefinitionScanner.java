package core.di.factory;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Sets;

import core.annotation.Component;
import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import core.di.factory.config.DefaultBeanDefinition;
import core.di.factory.support.BeanDefinitionRegistry;

public class ClasspathBeanDefinitionScanner {
	private final BeanDefinitionRegistry beanDefinitionRegistry;

	public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	public void doScan(Object... basePackages) {
		Reflections reflections = new Reflections(basePackages);
		Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class, Repository.class, Component.class);

		for (Class<?> clazz : beanClasses) {
			beanDefinitionRegistry.registerBeanDefinition(clazz, new DefaultBeanDefinition(clazz));
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
		Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
		for (Class<? extends Annotation> annotation : annotations) {
			preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
		}
		return preInstantiatedBeans;
	}
}
