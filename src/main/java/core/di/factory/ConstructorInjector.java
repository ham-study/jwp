package core.di.factory;

import java.util.Collections;
import java.util.Set;

public class ConstructorInjector extends AbstractInjector {
	protected ConstructorInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	protected void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
		// nothing
	}

	@Override
	protected Class<?> getBeanClass(Object injectedBean) {
		return null;
	}

	@Override
	protected Set<?> getInjectedBeans(Class<?> clazz) {
		return Collections.emptySet();
	}
}
