package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        for (Object packageName : basePackage) {
            Reflections reflections = new Reflections(String.valueOf(packageName));
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
            for (Class<?> aClass : classes) {
                Set<Method> methods = ReflectionUtils.getAllMethods(aClass);
                for (Method method : methods) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping == null) {
                        continue;
                    }

                    String url = requestMapping.value();
                    RequestMethod requestMethod = requestMapping.method();

                    HandlerKey handlerKey = new HandlerKey(url, requestMethod);
                    try {
                        HandlerExecution handlerExecution = new HandlerExecution(aClass, method);
                        handlerExecutions.put(handlerKey, handlerExecution);
                    } catch (Exception e) {
                        throw new IllegalStateException("Failed to create handler. " + aClass.getName());
                    }
                }

            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
