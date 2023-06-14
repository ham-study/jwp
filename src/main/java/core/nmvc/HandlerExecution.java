package core.nmvc;

import java.lang.reflect.Method;

import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HandlerExecution {
    private final Class<?> type;
    private final Method method;
    public HandlerExecution(Class<?> type, Method method) {
        this.type = type;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object instance = type.getDeclaredConstructor().newInstance();
        Object result = method.invoke(instance, request, response);
        return (ModelAndView)result;
    }
}
