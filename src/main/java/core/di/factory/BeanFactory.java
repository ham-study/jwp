package core.di.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

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

    public Map<Class<?>, Object> getBeansAnnotatedWith(Class<? extends Annotation> annotation) {
        Map<Class<?>, Object> beans = new HashMap<>();
        for (Class<?> preInstantiateBean : preInstantiateBeans) {
            if (preInstantiateBean.getAnnotation(annotation) != null) {
                beans.put(preInstantiateBean, getBean(preInstantiateBean));
            }
        }

        return beans;
    }

    public void initialize() {
        for (Class<?> preInstantiateBean : preInstantiateBeans) {
            if (beans.get(preInstantiateBean) == null) {
                instantiateClass(preInstantiateBean);
            }
        }
    }

    private Object instantiateClass(Class<?> preInstantiateBean) {
        if (beans.containsKey(preInstantiateBean)) {
            return beans.get(preInstantiateBean);
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(preInstantiateBean);
        if (injectedConstructor == null) {
            Object bean = BeanUtils.instantiate(preInstantiateBean);
            beans.put(preInstantiateBean, bean);
            return bean;
        }

        Object bean = instantiateConstructor(injectedConstructor);
        beans.put(preInstantiateBean, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> parameters = new ArrayList<>();

        for (Class<?> parameterType : parameterTypes) {
            Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(parameterType, preInstantiateBeans);

            if (!preInstantiateBeans.contains(concreteClass)) {
                throw new IllegalArgumentException(concreteClass + " is not a bean");
            }

            Object bean = getBean(concreteClass);
            if (bean == null) {
                bean = instantiateClass(concreteClass);
            }

            parameters.add(bean);
        }

        return BeanUtils.instantiateClass(constructor, parameters.toArray());
    }
}
