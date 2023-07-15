package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;

public abstract class AbstractInjector implements Injector {
	private final BeanFactory beanFactory;

	protected AbstractInjector(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public void inject(Class<?> clazz) {
		instantiateClass(clazz);
		Set<?> injectedBeans = getInjectedBeans(clazz);
		for (Object injectedBean : injectedBeans) {
			Class<?> beanClass = getBeanClass(injectedBean);
			inject(injectedBean, instantiateClass(beanClass), beanFactory);
		}
	}

	protected abstract void inject(Object injectedBean, Object bean, BeanFactory beanFactory);

	protected abstract Class<?> getBeanClass(Object injectedBean);

	protected abstract Set<?> getInjectedBeans(Class<?> clazz);

	private Object instantiateClass(Class<?> clazz) {
		Object bean = beanFactory.getBean(clazz);
		if (bean != null) {
			return bean;
		}

		Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
		if (injectedConstructor == null) {
			bean = BeanUtils.instantiate(clazz);
			beanFactory.registerBean(clazz, bean);
			return bean;
		}

		bean = instantiateConstructor(injectedConstructor);
		beanFactory.registerBean(clazz, bean);
		return bean;
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		Class<?>[] pTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		for (Class<?> clazz : pTypes) {
			Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getPreInstanticateBeans());
			if (!beanFactory.getPreInstanticateBeans().contains(concreteClazz)) {
				throw new IllegalStateException(clazz + "는 Bean이 아니다.");
			}

			Object bean = beanFactory.getBean(concreteClazz);
			if (bean == null) {
				bean = instantiateClass(concreteClazz);
			}
			args.add(bean);
		}
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}
}
