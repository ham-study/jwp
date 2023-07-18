package core.di.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import core.di.beans.factory.config.BeanDefinition;

public class DefaultBeanDefinition implements BeanDefinition {
	private Class<?> beanClazz;
	private Constructor<?> injectConstructor;
	private Set<Field> injectFields;

	public DefaultBeanDefinition(Class<?> clazz) {
		this.beanClazz = clazz;
		injectConstructor = getInjectConstructor(clazz);
		injectFields = getInjectFields(clazz, injectConstructor);
	}

	private Constructor<?> getInjectConstructor(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedConstructor(clazz);
	}

	private Set<Field> getInjectFields(Class<?> clazz, Constructor<?> constructor) {
		if (constructor != null) {
			return new HashSet<>();
		}

		Set<Field> injectFields = new HashSet<>();
		Set<Class<?>> injectProperties = getInjectProperties(clazz);
		Field[] fields = clazz.getFields();

		for (Field field : fields) {
			if (injectProperties.contains(field.getType())) {
				injectFields.add(field);
			}
		}

		return injectFields;
	}

	private Set<Class<?>> getInjectProperties(Class<?> clazz) {
		Set<Class<?>> injectProperties = new HashSet<>();
		Set<Method> injectedMethods = BeanFactoryUtils.getInjectedMethods(clazz);
		for (Method injectedMethod : injectedMethods) {
			Class<?>[] parameterTypes = injectedMethod.getParameterTypes();
			if (parameterTypes.length != 1) {
				throw new IllegalStateException("parameter should exist 1");
			}
			injectProperties.add(parameterTypes[0]);
		}
		Set<Field> injectedFields = BeanFactoryUtils.getInjectedFields(clazz);

		for (Field injectedField : injectedFields) {
			injectProperties.add(injectedField.getType());
		}

		return injectProperties;
	}

	@Override
	public Constructor<?> getInjectConstructor() {
		return injectConstructor;
	}

	@Override
	public Set<Field> getInjectFields() {
		return this.injectFields;
	}

	@Override
	public Class<?> getBeanClass() {
		return this.beanClazz;
	}

	@Override
	public InjectType getResolvedInjectMode() {
		if (injectConstructor != null) {
			return InjectType.INJECT_CONSTRUCTOR;
		}

		if (!injectFields.isEmpty()) {
			return InjectType.INJECT_FIELD;
		}

		return InjectType.INJECT_NO;
	}
}
