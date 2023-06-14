package core.mvc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private AnnotationHandlerMapping am;
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();

        am = new AnnotationHandlerMapping("next.controller");
        am.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ModelAndView mav = handleAnnotationHandler(req, resp);

            if (mav == null) {
                mav = handleRequestMappingHandler(req, resp);
            }

            if (mav == null) {
                throw new IllegalArgumentException("Failed to find handler to process request");
            }

            render(mav, req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView handleAnnotationHandler(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HandlerExecution handlerExecution = am.getHandler(req);
        if (handlerExecution == null) {
            return null;
        }

        return handlerExecution.handle(req, resp);
    }

    private ModelAndView handleRequestMappingHandler(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Controller controller = rm.findController(requestUri);
        if (controller == null) {
            return null;
        }

        return controller.execute(req, resp);
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
