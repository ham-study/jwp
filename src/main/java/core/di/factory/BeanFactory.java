package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanCreationException;

import com.google.common.collect.Maps;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstantiateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> preInstantiateBean : preInstantiateBeans) {
            register(BeanFactoryUtils.findConcreteClass(preInstantiateBean, preInstantiateBeans));
        }
    }

    private void register(Class<?> preInstantiateBean) {
        if (beans.containsKey(preInstantiateBean)) {
            return;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(preInstantiateBean);
        if (injectedConstructor == null) {
            Object instance = BeanUtils.instantiate(preInstantiateBean);
            beans.put(preInstantiateBean, instance);
            return;
        }

        Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(parameterType, preInstantiateBeans);
            if (getBean(concreteClass) == null) {
                register(concreteClass);
            }
            parameters[i] = getBean(concreteClass);
        }

        try {
            Object bean = injectedConstructor.newInstance(parameters);
            beans.put(preInstantiateBean, bean);
        } catch (Exception e) {
            throw new BeanCreationException("Failed to instantiate bean." + e.getMessage());
        }
    }
}
