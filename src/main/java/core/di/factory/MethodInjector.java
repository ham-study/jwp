package core.di.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.beans.factory.BeanCreationException;

public class MethodInjector extends AbstractInjector {
	protected MethodInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	protected void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
		Method method = (Method)injectedBean;
		try {
			method.invoke(injectedBean, bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BeanCreationException("Failed to create bean", e);
		}
	}

	@Override
	protected Class<?> getBeanClass(Object injectedBean) {
		Method method = (Method)injectedBean;

		Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length != 1) {
			throw new IllegalStateException("Parameter should exist 1");
		}

		return parameterTypes[0];
	}

	@Override
	protected Set<?> getInjectedBeans(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedMethods(clazz);
	}
}
