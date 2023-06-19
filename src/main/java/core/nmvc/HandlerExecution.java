package core.nmvc;

import java.lang.reflect.Method;

import core.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HandlerExecution {
    private final Object target;
    private final Method method;
    public HandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object result = method.invoke(target, request, response);
        return (ModelAndView)result;
    }
}
