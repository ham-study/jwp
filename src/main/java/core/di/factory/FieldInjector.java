package core.di.factory;

import java.lang.reflect.Field;
import java.util.Set;

import org.springframework.beans.factory.BeanCreationException;

public class FieldInjector extends AbstractInjector {
	protected FieldInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	protected void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
		Field field = (Field)injectedBean;
		try {
			field.setAccessible(true);
			field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
		} catch (IllegalAccessException e) {
			throw new BeanCreationException("Failed to create bean", e);
		}

	}

	@Override
	protected Class<?> getBeanClass(Object injectedBean) {
		Field field = (Field)injectedBean;
		return field.getType();
	}

	@Override
	protected Set<?> getInjectedBeans(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedFields(clazz);
	}
}
